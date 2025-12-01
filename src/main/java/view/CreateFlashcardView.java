package view;

import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import entity.Language;
import use_case.flashcard.create.CreateFlashcardInputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateFlashcardView extends JPanel implements PropertyChangeListener {

    private final transient CreateFlashcardController controller;
    private final CreateFlashcardViewModel viewModel;

    private final JTextField sourceWordField;
    private final JComboBox<Language> sourceLangDropdown;
    private final JComboBox<Language> targetLangDropdown;
    private final JTextArea outputArea;

    private final ViewManager viewManager;
    private final JButton deckMenuBtn;


    public CreateFlashcardView(CreateFlashcardController controller,
                               CreateFlashcardViewModel viewModel,
                               ViewManager viewManager) {

        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManager = viewManager;

        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Source word:"));
        sourceWordField = new JTextField();
        inputPanel.add(sourceWordField);

        inputPanel.add(new JLabel("Source language:"));
        sourceLangDropdown = new JComboBox<>(Language.values());
        inputPanel.add(sourceLangDropdown);

        inputPanel.add(new JLabel("Target language:"));
        targetLangDropdown = new JComboBox<>(Language.values());
        inputPanel.add(targetLangDropdown);

        // Deck Menu Button
        JPanel bottom = new JPanel(new BorderLayout());
        deckMenuBtn = new JButton("Deck Menu");
        bottom.add(deckMenuBtn, BorderLayout.WEST);
        this.add(bottom, BorderLayout.SOUTH);
        deckMenuBtn.addActionListener(e -> {
            viewManager.show("DeckMenu");
        });

        JButton createButton = new JButton("Create Flashcard");
        inputPanel.add(createButton);



        this.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        this.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Action listener
        createButton.addActionListener(e -> createFlashcard());
    }

    private void createFlashcard() {
        String word = sourceWordField.getText();
        Language sourceLang = (Language) sourceLangDropdown.getSelectedItem();
        Language targetLang = (Language) targetLangDropdown.getSelectedItem();
        int deckId = viewModel.getDeckId();

        controller.createFlashcard(deckId, word, sourceLang, targetLang);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("message".equals(evt.getPropertyName())) {
            outputArea.setText(viewModel.getMessage());
        }
    }
}
