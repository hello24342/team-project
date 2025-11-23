package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
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


public class LoggedInView extends JPanel implements PropertyChangeListener, ActionListener {
    private final String viewName = "logged in";

    private final JLabel username = new JLabel();

    private final JButton addDeckButton = new JButton("+ Add Deck");

    private final JButton homeButton = new JButton("Home");
    private final JButton decksButton = new JButton("Decks");
    private final JButton activityButton = new JButton("Activity");
    private final JButton logOutButton = new JButton("Log Out");

    private LoggedInViewModel loggedInViewModel;
    private LogoutController logoutController;
    private ListDecksController listDecksController;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.listDecksController = listDecksController;
        this.loggedInViewModel.addPropertyChangeListener(this);

        this.homeButton.addActionListener(this);
        this.logOutButton.addActionListener(this);
        this.decksButton.addActionListener(this);
        this.activityButton.addActionListener(this);

        final JLabel title = new JLabel("VocabVault Home");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.add(homeButton);
        buttons.add(decksButton);
        buttons.add(logOutButton);

        decksButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(decksButton)) {
                            final LoggedInState loggedInState = loggedInViewModel.getState();
                        }
                        listDecksController.onEnterDeckMenu();
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }
}
