package view;

import interface_adapter.deck.CreateDeckController;
import interface_adapter.deck.OpenDeckController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.deck.ListDecksController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class LoggedInView extends JPanel implements PropertyChangeListener {
    private final String viewName = "logged in";

    private final JLabel username = new JLabel();
    private final JPanel deckPanel = new JPanel();

    private final JButton addDeckButton = new JButton("+ Add Deck");
    private final JButton homeButton = new JButton("Home");
    private final JButton decksButton = new JButton("Decks");
    private final JButton activityButton = new JButton("Activity");
    private final JButton logOutButton = new JButton("Log Out");

    private final LoggedInViewModel loggedInViewModel;
    private final LogoutController logoutController;
    private final ListDecksController listDecksController;
    private final CreateDeckController createDeckController;
    private final OpenDeckController openDeckController;
    private final ViewManager viewManager;

    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        LogoutController logoutController,
                        ListDecksController listDecksController,
                        CreateDeckController createDeckController,
                        OpenDeckController openDeckController,
                        ViewManager viewManager) {
        this.loggedInViewModel = loggedInViewModel;
        this.logoutController = logoutController;
        this.listDecksController = listDecksController;
        this.createDeckController = createDeckController;
        this.openDeckController = openDeckController;
        this.viewManager = viewManager;
        this.loggedInViewModel.addPropertyChangeListener(this);

        initializeUI();
        setupActionListeners();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());

        final JLabel title = new JLabel("VocabVault Home");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.add(new JLabel("Welcome, "));
        welcomePanel.add(username);
        username.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        deckPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        deckPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(deckPanel, BorderLayout.CENTER);

        JPanel addDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addDeckPanel.add(addDeckButton);
        mainPanel.add(addDeckPanel, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.add(homeButton);
        navigationPanel.add(decksButton);
        navigationPanel.add(activityButton);
        navigationPanel.add(logOutButton);
        this.add(navigationPanel, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Home button clicked");
                refreshDeckDisplay();
            }
        });

        decksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Decks button clicked");
                if (viewManager != null) {
                    viewManager.show("DeckMenu");
                } else {
                    JOptionPane.showMessageDialog(
                            LoggedInView.this,
                            "Decks functionality not implemented yet",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        activityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Activity button clicked");
                if (viewManager != null) {
                    viewManager.show("Activity");
                } else {
                    JOptionPane.showMessageDialog(
                            LoggedInView.this,
                            "Activity/Progress tracker not implemented yet",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Logout button clicked");
                if (logoutController != null) {
                    logoutController.execute();

                    if (viewManager != null) {
                        viewManager.show("Login");
                    }
                } else if (viewManager != null) {
                    viewManager.show("Login");
                }
            }
        });

        addDeckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deckName = JOptionPane.showInputDialog(
                        LoggedInView.this,
                        "Enter deck name:",
                        "Create New Deck",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (deckName != null && !deckName.trim().isEmpty()) {
                    if (createDeckController != null) {
                        createDeckController.onCreate(deckName.trim());
                    } else {
                        JOptionPane.showMessageDialog(
                                LoggedInView.this,
                                "Created deck: " + deckName.trim(),
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
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
    }

    public void refreshDeckDisplay() {
        deckPanel.removeAll();

        LoggedInState loggedInState = loggedInViewModel.getState();
        List<String> userDecks = loggedInState.getUserDecks();

        if (userDecks == null || userDecks.isEmpty()) {
            JLabel noDecksLabel = new JLabel("No decks found yet. Click the '+ Add Deck' button to create your first deck!");
            noDecksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            deckPanel.add(noDecksLabel);
        } else {
            for (int i = 0; i < userDecks.size(); i++) {
                final String deckName = userDecks.get(i);
                final int deckIndex = i;

                JButton deckButton = new JButton(deckName);
                deckButton.setPreferredSize(new Dimension(150, 50));
                deckButton.setFont(new Font("Arial", Font.PLAIN, 12));
                deckButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (openDeckController != null) {
                            openDeckController.open(deckIndex);
                        } else {
                            JOptionPane.showMessageDialog(
                                    LoggedInView.this,
                                    "Opening deck: " + deckName,
                                    "Info",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                });
                deckPanel.add(deckButton);
            }
        }

        deckPanel.revalidate();
        deckPanel.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            LoggedInState loggedInState = (LoggedInState) evt.getNewValue();
            username.setText(loggedInState.getUsername());
            refreshDeckDisplay();
        }
    }

    public String getViewName() {
        return viewName;
    }
}