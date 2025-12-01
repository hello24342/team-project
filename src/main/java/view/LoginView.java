package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Login";
    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton loginButton = new JButton("Login");
    private final JButton signupButton = new JButton("Signup");
    private final LoginViewModel loginViewModel;
    private final LoginController loginController;
    private final SignupController signupController;
    private final ViewManager viewManager;

    public LoginView(LoginViewModel loginViewModel,
                     LoginController loginController,
                     SignupController signupController,
                     ViewManager viewManager) {
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.signupController = signupController;
        this.viewManager = viewManager;
        this.loginViewModel.addPropertyChangeListener(this);

        initializeUI();
        setupDocumentListeners();
        setupActionListeners();
    }

    private void initializeUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("VocabVault Login Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Style error fields
        usernameErrorField.setForeground(Color.RED);
        passwordErrorField.setForeground(Color.RED);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        final JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(signupButton);

        this.add(Box.createVerticalStrut(20));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(Box.createVerticalStrut(10));
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons);
        this.add(Box.createVerticalStrut(20));
    }

    private void setupDocumentListeners() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateUsernameState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateUsernameState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateUsernameState();
            }

            private void updateUsernameState() {
                SwingUtilities.invokeLater(() -> {
                    LoginState currentState = loginViewModel.getState();
                    currentState.setUsername(usernameInputField.getText());
                    loginViewModel.setState(currentState);

                    // DEBUG
                    System.out.println("Username state updated to: " + currentState.getUsername());
                });
            }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordState();
            }

            private void updatePasswordState() {
                SwingUtilities.invokeLater(() -> {
                    LoginState currentState = loginViewModel.getState();
                    currentState.setPassword(new String(passwordInputField.getPassword()));
                    loginViewModel.setState(currentState);

                    // DEBUG - don't print actual password
                    System.out.println("Password state updated (length): " + currentState.getPassword().length());
                });
            }
        });

        // Initialize state with empty values
        LoginState initialState = loginViewModel.getState();
        initialState.setUsername("");
        initialState.setPassword("");
        loginViewModel.setState(initialState);
    }

    private void setupActionListeners() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LoginState currentState = loginViewModel.getState();

                // DEBUG: Show current state
                System.out.println("=== LOGIN BUTTON CLICKED ===");
                System.out.println("State username: '" + currentState.getUsername() + "'");
                System.out.println("State password length: " + currentState.getPassword().length());
                System.out.println("Field username: '" + usernameInputField.getText() + "'");
                System.out.println("Field password: " + passwordInputField.getPassword());

                String username = currentState.getUsername();
                String password = currentState.getPassword();

                if (username == null || username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            "Please enter username",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    usernameInputField.requestFocus();
                    return;
                }

                if (password == null || password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            "Please enter password",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    passwordInputField.requestFocus();
                    return;
                }
                loginController.execute(username.trim(), password.trim());
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewManager != null) {
                    usernameInputField.setText("");
                    passwordInputField.setText("");
                    LoginState clearedState = loginViewModel.getState();
                    clearedState.setUsername("");
                    clearedState.setPassword("");
                    clearedState.setLoginError(null);
                    loginViewModel.setState(clearedState);

                    viewManager.show("Signup");
                } else {
                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            "Signup functionality not implemented yet",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            LoginState state = (LoginState) evt.getNewValue();

            System.out.println("=== LOGIN VIEW PROPERTY CHANGE ===");
            System.out.println("Login error: " + state.getLoginError());
            System.out.println("Username in state: '" + state.getUsername() + "'");

            String error = state.getLoginError();
            if (error != null && !error.isEmpty()) {
                // Show error
                usernameErrorField.setText(error);
                passwordErrorField.setText(error);

                JOptionPane.showMessageDialog(
                        this,
                        error,
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );

                state.setLoginError(null);
                loginViewModel.setState(state);
            } else if (state.getUsername() != null && !state.getUsername().isEmpty()) {
                clearForm();
                SwingUtilities.invokeLater(() -> {
                    viewManager.show("LoggedIn");
                });
            } else {
                usernameErrorField.setText("");
                passwordErrorField.setText("");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void clearForm() {
        usernameInputField.setText("");
        passwordInputField.setText("");
        LoginState clearedState = loginViewModel.getState();
        clearedState.setUsername("");
        clearedState.setPassword("");
        clearedState.setLoginError(null);
        loginViewModel.setState(clearedState);
    }
}