package view.deck;
import interface_adapter.deck.DeckDetailViewModel;
import interface_adapter.deck.OpenDeckController;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DeckDetailView extends JPanel implements PropertyChangeListener {

    private final DeckDetailViewModel vm;
    private final ViewManager viewManager;
    private final OpenDeckController openCtl;

    private final JLabel titleLabel;
    private final JPanel listPanel;
    private final JLabel errorLabel;

    public DeckDetailView(DeckDetailViewModel vm,
                          OpenDeckController openCtl,
                          ViewManager viewManager) {
        this.vm = vm;
        this.openCtl = openCtl;
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
            // TODO 1: switch to UC1 Create Flashcard page (to be implemented later)
            JOptionPane.showMessageDialog(this, "TODO: go to UC1 Create Flashcard");
        });
        playBtn.addActionListener(e -> {
            // TODO 2: switch to UC2 Study Deck page (to be implemented later)
            JOptionPane.showMessageDialog(this, "TODO: go to UC2 Study Deck");
        });
    }

    // TODO 3: Clicking a flashcard in the list → open UC9 (edit/delete that flashcard)

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
