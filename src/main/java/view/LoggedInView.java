package view;

import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;

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
    private LoggedInViewModel loggedInViewModel;
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    private final JLabel username;


    private final JButton homeButton;
    private final JButton decksButton;
    private final JButton changePasswordButton;
    private final JButton logOutButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("VocabVault Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
