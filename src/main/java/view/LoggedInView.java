package view;

import interface_adapter.deck.CreateDeckController;
import interface_adapter.deck.OpenDeckController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.deck.ListDecksController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;


public class LoggedInView extends JPanel implements PropertyChangeListener, ActionListener {
    private final String viewName = "logged in";

    private final JLabel username = new JLabel();
    private final JPanel deckPanel = new JPanel();

    private final JButton addDeckButton = new JButton("+ Add Deck");

    private final JButton homeButton = new JButton("Home");
    private final JButton decksButton = new JButton("Decks");
    private final JButton activityButton = new JButton("Activity");
    private final JButton logOutButton = new JButton("Log Out");

    private LoggedInViewModel loggedInViewModel;
    private LogoutController logoutController;
    private ListDecksController listDecksController;
    private CreateDeckController createDeckController;
    private OpenDeckController openDeckController; //make this

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.listDecksController = listDecksController;
        this.logoutController = logoutController;
        this.createDeckController = createDeckController;
        this.openDeckController = openDeckController;
        this.loggedInViewModel.addPropertyChangeListener(this);

        initializeUI();

        setupActionListeners();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());

        // title panel
        final JLabel title = new JLabel("VocabVault Home");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title, BorderLayout.NORTH);

        // main content panel (welcome message, add deck button, and one clickable deck)
        JPanel mainPanel = new JPanel(new BorderLayout());

        // welcome message
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.add(new JLabel("Welcome, "));
        welcomePanel.add(username);
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        // deck display panel
        deckPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        deckPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(deckPanel, BorderLayout.CENTER);

        // add deck button
        JPanel addDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addDeckPanel.add(addDeckButton);
        mainPanel.add(addDeckPanel, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);

        // nav bar
        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.add(homeButton);
        navigationPanel.add(decksButton);
        navigationPanel.add(activityButton);
        navigationPanel.add(logOutButton);
        this.add(navigationPanel, BorderLayout.SOUTH);
    }

    public void setupActionListeners() {
        addDeckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listDecksController.onEnterDeckMenu();
            }
        });

        decksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listDecksController.onEnterDeckMenu();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logoutController.execute();
            }
        });

        /*
        activityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLearningGoalController.
            }
        })
        */

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeckDisplay();
            }
        });
    }

    public void refreshDeckDisplay() {
        deckPanel.removeAll();

        LoggedInState loggedInState = loggedInViewModel.getState();
        java.util.List<String> userDecks = loggedInState.getUserDecks();

        if (userDecks == null || userDecks.isEmpty()) {
            JLabel noDecksLabel = new JLabel("No decks found yet. Click the '+ Add Deck' button to create your first deck!");
            deckPanel.add(noDecksLabel);
        } else {
            JLabel deckLabel = new JLabel("Your Deck: " + userDecks.get(0));

            deckPanel.add(deckLabel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState loggedInState = (LoggedInState) evt.getNewValue();
        username.setText(loggedInState.getUsername());
        refreshDeckDisplay();
    }

    public String getViewName() {
        return viewName;
    }
}
