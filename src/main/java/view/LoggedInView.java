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
    private OpenDeckController openDeckController;

    // Updated constructor to properly initialize all dependencies
    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        LogoutController logoutController,
                        ListDecksController listDecksController,
                        CreateDeckController createDeckController,
                        OpenDeckController openDeckController) {
        this.loggedInViewModel = loggedInViewModel;
        this.logoutController = logoutController;
        this.listDecksController = listDecksController;
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
        // Add Deck button listener
        addDeckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deckName = JOptionPane.showInputDialog(
                        LoggedInView.this,
                        "Enter deck name:",
                        "Create New Deck",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (deckName != null && !deckName.trim().isEmpty()) {
                    createDeckController.onCreate(deckName.trim());
                } else if (deckName != null) {
                    JOptionPane.showMessageDialog(
                            LoggedInView.this,
                            "Deck name cannot be empty!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Decks button listener
        decksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listDecksController.onEnterDeckMenu();
            }
        });

        // Logout button listener
        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logoutController.execute();
            }
        });

        // Home button listener
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeckDisplay();
            }
        });

        //TODO: Implement activity button when progress tracker controller is available
        /*
        activityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLearningGoalController.
            }
        })
        */
    }

    public void refreshDeckDisplay() {
        deckPanel.removeAll();

        LoggedInState loggedInState = loggedInViewModel.getState();
        java.util.List<String> userDecks = loggedInState.getUserDecks();

        if (userDecks == null || userDecks.isEmpty()) {
            JLabel noDecksLabel = new JLabel("No decks found yet. Click the '+ Add Deck' button to create your first deck!");
            deckPanel.add(noDecksLabel);
        } else {
            // Display the first deck as a clickable button
            String firstDeckName = userDecks.get(0);
            JButton deckButton = new JButton(firstDeckName);
            deckButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // openDeckController.execute(firstDeckName);
                }
            });
            deckPanel.add(deckButton);

            // Optional: Show message if there are more decks
            if (userDecks.size() > 1) {
                JLabel moreDecksLabel = new JLabel("... and " + (userDecks.size() - 1) + " more decks");
                deckPanel.add(moreDecksLabel);
            }
        }

        // Refresh the panel
        deckPanel.revalidate();
        deckPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle other actions if needed
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