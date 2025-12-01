package view.deck;
import interface_adapter.deck.CreateDeckController;
import interface_adapter.deck.DeckDetailViewModel;
import interface_adapter.deck.OpenDeckController;
import interface_adapter.edit_flashcard.EditFlashcardController;
import interface_adapter.study_deck.StudyDeckController;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DeckDetailView extends JPanel implements PropertyChangeListener {

    private final DeckDetailViewModel vm;
    private final ViewManager viewManager;
    private final OpenDeckController openCtl;
    private final StudyDeckController studyCtl;
    private final CreateDeckController createCtl;
    private final EditFlashcardController editCtl;
    private final int currentUserId;

    private final JLabel titleLabel;
    private final JPanel listPanel;
    private final JLabel errorLabel;

    public DeckDetailView(DeckDetailViewModel vm,
                          OpenDeckController openCtl,
                          StudyDeckController studyCtl,
                          CreateDeckController createCtl,
                          EditFlashcardController editCtl,
                          int currentUserId,
                          ViewManager viewManager) {
        this.vm = vm;
        this.openCtl = openCtl;
        this.studyCtl = studyCtl;
        this.createCtl = createCtl;
        this.editCtl = editCtl;
        this.currentUserId = currentUserId;
        this.viewManager = viewManager;

        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Deck");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        header.add(titleLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Cards list
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        // Footer (buttons)
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("Deck Menu");
        JButton addBtn = new JButton("+ New Flashcards");
        JButton playBtn = new JButton("Play");

        footer.add(backBtn);
        footer.add(addBtn);
        footer.add(playBtn);

        errorLabel = new JLabel("");
        footer.add(errorLabel);
        add(footer, BorderLayout.SOUTH);

        vm.addPropertyChangeListener(this);

        backBtn.addActionListener(e -> viewManager.show("DeckMenu"));

        addBtn.addActionListener(e -> {
            createCtl.onCreate(vm.getDeckTitle());
            viewManager.show("CreateFlashcard");
            // TODO: check if createFlashcard name is right when view is made in appbuilder
        });

        playBtn.addActionListener(e -> {
            studyCtl.loadDeckForStudy(currentUserId, vm.getDeckId());
            viewManager.show("Study");
        });
    }

    // TODO 3: Clicking a flashcard in the list → open UC9 (edit/delete that flashcard)
    private JPanel createCardRow(DeckDetailViewModel.CardVM card) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Cards will be on the left
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Label cards with ID
        JLabel idLabel = new JLabel("#" + card.id);
        idLabel.setForeground(Color.BLACK);
        idLabel.setPreferredSize(new Dimension(50, 20));

        // Setting a card label with the source word and target words
        JLabel cardLabel = new JLabel("  " + card.sourceWord + "  →  " + card.targetWord);
        cardLabel.setFont(cardLabel.getFont().deriveFont(Font.PLAIN, 14f));

        contentPanel.add(idLabel);
        contentPanel.add(cardLabel);

        // Edit button on the right of each flashcard to navigate to EditFlashcardView
        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(80, 30));
        editButton.addActionListener(e -> {
            int deckId = vm.getDeckId();
            String deckTitle = vm.getDeckTitle();

            if (deckId > 0 && deckTitle != null && !deckTitle.isEmpty()) {
                editCtl.prepareViewForEdit(card.id, card.sourceWord, card.targetWord);
                viewManager.show("EditFlashcard");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid deck information",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        row.add(contentPanel, BorderLayout.CENTER);
        row.add(editButton, BorderLayout.EAST);

        return row;
    }

    private void refresh() {
        titleLabel.setText(vm.getDeckTitle());
        listPanel.removeAll();
        for (DeckDetailViewModel.CardVM c : vm.getCards()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.add(new JLabel("#" + c.id));
            row.add(new JLabel("  " + c.sourceWord + "  →  " + c.targetWord));
            listPanel.add(row);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        if (DeckDetailViewModel.DETAIL_PROPERTY.equals(name)) {
            refresh();
        } else if (DeckDetailViewModel.ERROR_PROPERTY.equals(name)) {
            String m = vm.getErrorMessage();
            errorLabel.setText(m == null ? "" : m);
        }
    }
}
