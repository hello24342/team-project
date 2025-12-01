package view;

import entity.Language;
import interface_adapter.edit_flashcard.EditFlashcardController;
import interface_adapter.edit_flashcard.EditFlashcardState;
import interface_adapter.edit_flashcard.EditFlashcardViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditFlashcardView extends JPanel implements PropertyChangeListener {

    private final transient EditFlashcardController controller;
    private final EditFlashcardViewModel viewModel;
    private final ViewManager viewManager;

    private final JTextField sourceWordField;
    private final JTextField targetWordField;
    private final JComboBox<Language> sourceLangDropdown;
    private final JComboBox<Language> targetLangDropdown;
    private final JLabel statusLabel;

    public EditFlashcardView(EditFlashcardController controller,
                             EditFlashcardViewModel viewModel,
                             ViewManager viewManager) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManager = viewManager;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("Edit Flashcard");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        add(titleLabel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));

        formPanel.add(new JLabel("Source word:"));
        sourceWordField = new JTextField();
        formPanel.add(sourceWordField);

        formPanel.add(new JLabel("Target word:"));
        targetWordField = new JTextField();
        formPanel.add(targetWordField);

        formPanel.add(new JLabel("Source language:"));
        sourceLangDropdown = new JComboBox<>(Language.values());
        formPanel.add(sourceLangDropdown);

        formPanel.add(new JLabel("Target language:"));
        targetLangDropdown = new JComboBox<>(Language.values());
        formPanel.add(targetLangDropdown);

        add(formPanel, BorderLayout.CENTER);

        // Bottom: status + buttons
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JButton backButton = new JButton("Back to Deck");
        JButton deleteButton = new JButton("Delete");
        JButton saveButton = new JButton("Save");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        backButton.addActionListener(e -> viewManager.show("DeckDetail"));

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this flashcard?",
                    "Confirm delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                controller.executeDelete();
            }
        });

        saveButton.addActionListener(e -> {
            String newSourceWord = sourceWordField.getText().trim();
            String newTargetWord = targetWordField.getText().trim();

            Language srcLang = (Language) sourceLangDropdown.getSelectedItem();
            Language tgtLang = (Language) targetLangDropdown.getSelectedItem();

            String srcLangName = (srcLang != null) ? srcLang.name() : null;
            String tgtLangName = (tgtLang != null) ? tgtLang.name() : null;

            controller.executeEdit(newSourceWord, newTargetWord, srcLangName, tgtLangName);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }

        EditFlashcardState state = viewModel.getState();
        if (state == null) {
            return;
        }

        // Pre-fill fields with current state (step 2 of the use case: show current data)
        if (state.getSourceWord() != null) {
            sourceWordField.setText(state.getSourceWord());
        }
        if (state.getTargetWord() != null) {
            targetWordField.setText(state.getTargetWord());
        }

        if (state.getSourceLang() != null) {
            try {
                Language src = Language.valueOf(state.getSourceLang());
                sourceLangDropdown.setSelectedItem(src);
            } catch (IllegalArgumentException ignored) {}
        }

        if (state.getTargetLang() != null) {
            try {
                Language tgt = Language.valueOf(state.getTargetLang());
                targetLangDropdown.setSelectedItem(tgt);
            } catch (IllegalArgumentException ignored) {}
        }

        // Status / messages
        if (state.getError() != null) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(state.getError());
        } else if (state.getMessage() != null) {
            statusLabel.setForeground(new JLabel().getForeground());
            statusLabel.setText(state.getMessage());

            // Optional: after delete, go back to deck detail
            if (state.getMessage().toLowerCase().contains("deleted")) {
                viewManager.show("DeckDetail");
            }
        } else {
            statusLabel.setText(" ");
        }
    }
}
