package view;

import interface_adapter.deck.CreateDeckController;
import interface_adapter.deck.DeckMenuViewModel;
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
    private final JPanel mainContentPanel = new JPanel(); // For better layout

    private final JButton addDeckButton = new JButton("+ Add Deck");
    private final JButton homeButton = new JButton("Home");
    private final JButton decksButton = new JButton("Decks");
    private final JButton activityButton = new JButton("Activity");
    private final JButton logOutButton = new JButton("Log Out");

    private final LoggedInViewModel loggedInViewModel;
    private final DeckMenuViewModel deckMenuViewModel;
    private final LogoutController logoutController;
    private final ListDecksController listDecksController;
    private final CreateDeckController createDeckController;
    private final OpenDeckController openDeckController;
    private final ViewManager viewManager;

    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        DeckMenuViewModel deckMenuViewModel,
                        LogoutController logoutController,
                        ListDecksController listDecksController,
                        CreateDeckController createDeckController,
                        OpenDeckController openDeckController,
                        ViewManager viewManager) {
        this.loggedInViewModel = loggedInViewModel;
        this.deckMenuViewModel = deckMenuViewModel;
        this.logoutController = logoutController;
        this.listDecksController = listDecksController;
        this.createDeckController = createDeckController;
        this.openDeckController = openDeckController;
        this.viewManager = viewManager;

        System.out.println("=== LOGGEDINVIEW INIT ===");
        System.out.println("LoggedInViewModel: " + (loggedInViewModel != null));
        System.out.println("Username JLabel: " + (username != null));

        // Check initial state
        LoggedInState initialState = loggedInViewModel.getState();
        System.out.println("Initial username in state: '" + initialState.getUsername() + "'");

        // Set initial text
        if (initialState.getUsername() != null && !initialState.getUsername().isEmpty()) {
            username.setText(initialState.getUsername());
            System.out.println("Set initial username to: " + initialState.getUsername());
        }

        if (deckMenuViewModel != null) {
            deckMenuViewModel.addPropertyChangeListener(evt -> {
                if (DeckMenuViewModel.DECK_LIST_PROPERTY.equals(evt.getPropertyName())) {
                    System.out.println("Deck list updated in DeckMenuViewModel");
                    refreshDeckDisplay();
                }
            });
        }

        this.loggedInViewModel.addPropertyChangeListener(this);
        System.out.println("Added property change listener");

        initializeUI();
        setupActionListeners();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());

        // Title panel
        final JLabel title = new JLabel("VocabVault Home");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(title, BorderLayout.NORTH);

        // Main content panel with scroll
        mainContentPanel.setLayout(new BorderLayout());

        // Welcome panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        welcomePanel.add(new JLabel("Welcome, "));
        username.setFont(new Font("Arial", Font.BOLD, 16));
        welcomePanel.add(username);
        mainContentPanel.add(welcomePanel, BorderLayout.NORTH);

        // Deck display panel with grid layout
        deckPanel.setLayout(new GridLayout(2, 3, 15, 15)); // 2 rows, 3 columns, with gaps
        deckPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        deckPanel.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(deckPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add deck button panel
        JPanel addDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addDeckPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        addDeckButton.setFont(new Font("Arial", Font.BOLD, 14));
        addDeckButton.setPreferredSize(new Dimension(200, 40));
        addDeckPanel.add(addDeckButton);
        mainContentPanel.add(addDeckPanel, BorderLayout.SOUTH);

        this.add(mainContentPanel, BorderLayout.CENTER);

        // Navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout());
        homeButton.setPreferredSize(new Dimension(100, 30));
        decksButton.setPreferredSize(new Dimension(100, 30));
        activityButton.setPreferredSize(new Dimension(100, 30));
        logOutButton.setPreferredSize(new Dimension(100, 30));
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
                listDecksController.onEnterDeckMenu();
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
                }
                if (viewManager != null) {
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
                        System.out.println("Deck created: " + deckName.trim());
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

        if (deckMenuViewModel != null) {
            java.util.List<DeckMenuViewModel.DeckTileVM> tiles = deckMenuViewModel.getTiles();

            if (tiles == null || tiles.isEmpty()) {
                showEmptyState();
            } else {
                int columns = 3;
                int rows = (int) Math.ceil(tiles.size() / (double) columns);
                deckPanel.setLayout(new GridLayout(rows, columns, 12, 12));

                for (DeckMenuViewModel.DeckTileVM tile : tiles) {
                    JButton deckButton = new JButton(tile.title);
                    deckButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    deckButton.addActionListener(e -> {
                        if (openDeckController != null) {
                            openDeckController.open(tile.deckId);
                        }
                        if (viewManager != null) {
                            viewManager.show("DeckDetail");
                        }
                    });

                    deckPanel.add(deckButton);
                }
            }
        } else {
            showEmptyState();
        }

        deckPanel.revalidate();
        deckPanel.repaint();
    }

    private void showEmptyState() {
        JPanel emptyStatePanel = new JPanel(new BorderLayout());
        JLabel noDecksLabel = new JLabel("No decks found yet");
        noDecksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        noDecksLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel instructionLabel = new JLabel("Click '+ Add Deck' to create your first deck!");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emptyStatePanel.add(noDecksLabel, BorderLayout.CENTER);
        emptyStatePanel.add(instructionLabel, BorderLayout.SOUTH);
        emptyStatePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        deckPanel.setLayout(new BorderLayout());
        deckPanel.add(emptyStatePanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            LoggedInState loggedInState = (LoggedInState) evt.getNewValue();
            username.setText(loggedInState.getUsername());
            refreshDeckDisplay();
        }
        if (username != null) {
            System.out.println("User logged in: " + username + ", loading decks...");

            // Load decks for this user
            if (listDecksController != null) {
                listDecksController.onEnterDeckMenu();
            } else {
                System.err.println("ERROR: listDecksController is null!");
            }
        }
        refreshDeckDisplay();
    }

    public String getViewName() {
        return viewName;
    }
}