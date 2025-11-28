package view.deck;

import interface_adapter.deck.CreateDeckController;
import interface_adapter.deck.DeckMenuViewModel;
import interface_adapter.deck.ListDecksController;
import interface_adapter.deck.OpenDeckController;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DeckMenuView extends JPanel {

    private final DeckMenuViewModel vm;
    private final CreateDeckController createCtl;
    private final ListDecksController listCtl;

    private final ViewManager viewManager;
    private final OpenDeckController openCtl;

    private final JPanel grid;
    private final JLabel errorLabel;
    private final JButton newDeckBtn;
    private final JButton homeBtn;

    public DeckMenuView(DeckMenuViewModel vm,
                        CreateDeckController createCtl,
                        ListDecksController listCtl,
                        OpenDeckController openCtl,
                        ViewManager viewManager) {
        this.vm = vm;
        this.createCtl = createCtl;
        this.listCtl = listCtl;
        this.openCtl = openCtl;
        this.viewManager = viewManager;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Decks");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 3, 12, 12));
        add(new JScrollPane(grid), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());

        // Left：Home button
        homeBtn = new JButton("Home");
        bottom.add(homeBtn, BorderLayout.WEST);

       // right：New Deck + error
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        newDeckBtn = new JButton("+ New Deck");
        right.add(newDeckBtn);

        errorLabel = new JLabel("");
        right.add(errorLabel);

        bottom.add(right, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        // Register this View as a listener of the ViewModel
        vm.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(DeckMenuViewModel.DECK_LIST_PROPERTY)) {
                refreshGrid();
            } else if (evt.getPropertyName().equals(DeckMenuViewModel.ERROR_PROPERTY)) {
                String msg = vm.getErrorMessage();
                errorLabel.setText(msg == null ? "" : msg);
            }
        });

        //register button listener
        newDeckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickNewDeck();
            }
        });

        homeBtn.addActionListener(e -> {
            // TODO: make sure 'loggedin' is the correct view name of homepage view
            viewManager.show("Loggedin");
        });


        // automatic load decks when entering the view
        this.listCtl.onEnterDeckMenu();
    }

    // Handle the "New Deck" button click
    private void onClickNewDeck() {
        // pop up a dialog to get the deck title
        String title = JOptionPane.showInputDialog(this,
                "Deck title:", "Create Deck",
                JOptionPane.PLAIN_MESSAGE);
        if (title != null) {
            createCtl.onCreate(title);
        }
    }

    // Refresh the grid of deck tiles adding buttons for each deck
    private void refreshGrid() {
        grid.removeAll();
        java.util.List<DeckMenuViewModel.DeckTileVM> tiles = vm.getTiles();
        for (DeckMenuViewModel.DeckTileVM t : tiles) {
            JButton b = new JButton(t.title);
            // register button listener
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onOpenDeck(t.deckId);
                }
            });

            grid.add(b);
        }
        grid.revalidate();
        grid.repaint();
    }

    private void onOpenDeck(int deckId) {
        openCtl.open(deckId);
        viewManager.show("DeckDetail");
    }

    // Listen to ViewModel property changes
    // @Override
    // public void propertyChange(PropertyChangeEvent evt) {
    //    String name = evt.getPropertyName();
    //    if (DeckMenuViewModel.DECK_LIST_PROPERTY.equals(name)) {
    //        refreshGrid();
    //    } else if (DeckMenuViewModel.ERROR_PROPERTY.equals(name)) {
    //       String msg = vm.getErrorMessage();
    //        errorLabel.setText(msg == null ? "" : msg);
    //    }
    }

