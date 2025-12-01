package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignUpView extends JPanel implements PropertyChangeListener {
    private final String viewName = "sign up";
    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();
    private final JTextField emailInputField = new JTextField(15);
    private final JLabel emailErrorField = new JLabel();
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();
    private final JPasswordField confirmPasswordInputField = new JPasswordField(15);
    private final JLabel confirmPasswordErrorField = new JLabel();

    private final JButton loginButton = new JButton("To Login");
    private final JButton signupButton = new JButton("Signup");

    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final ViewManager viewManager; // ADD THIS

    public SignUpView(SignupViewModel signupViewModel,
                      SignupController signupController,
                      ViewManager viewManager) { // ADD VIEW MANAGER
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        this.viewManager = viewManager;
        this.signupViewModel.addPropertyChangeListener(this);

        initializeUI();
        setupActionListeners();
        setupDocumentListeners();
    }

    private void initializeUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("VocabVault Sign Up Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Style error fields
        usernameErrorField.setForeground(Color.RED);
        emailErrorField.setForeground(Color.RED);
        passwordErrorField.setForeground(Color.RED);
        confirmPasswordErrorField.setForeground(Color.RED);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel emailInfo = new LabelTextPanel(
                new JLabel("Email"), emailInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);
        final LabelTextPanel confirmPasswordInfo = new LabelTextPanel(
                new JLabel("Confirm Password"), confirmPasswordInputField);

        final JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(signupButton);

        // Add components with spacing
        this.add(Box.createVerticalStrut(20));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(Box.createVerticalStrut(10));
        this.add(emailInfo);
        this.add(emailErrorField);
        this.add(Box.createVerticalStrut(10));
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(Box.createVerticalStrut(10));
        this.add(confirmPasswordInfo);
        this.add(confirmPasswordErrorField);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons);
        this.add(Box.createVerticalStrut(20));
    }

    private void setupActionListeners() {
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final SignupState currentState = signupViewModel.getState();

                if (currentState.getUsername().isEmpty() ||
                        currentState.getEmail().isEmpty() ||
                        currentState.getPassword().isEmpty() ||
                        currentState.getConfirmPassword().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            SignUpView.this,
                            "Please fill in all fields",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Check if passwords match
                if (!currentState.getPassword().equals(currentState.getConfirmPassword())) {
                    JOptionPane.showMessageDialog(
                            SignUpView.this,
                            "Passwords do not match",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Call signup controller
                signupController.execute(
                        currentState.getUsername(),
                        currentState.getEmail(),
                        currentState.getPassword(),
                        currentState.getConfirmPassword()
                );
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewManager != null) {
                    viewManager.show("Login");
                } else if (signupController != null) {
                    signupController.switchToLoginView();
                }
            }
        });
    }

    private void setupDocumentListeners() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateState(); }
        });

        emailInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(emailInputField.getText()); // FIXED: setEmail not setUsername
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateState(); }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword())); // FIXED
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateState(); }
        });

        confirmPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setConfirmPassword(new String(confirmPasswordInputField.getPassword())); // FIXED
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            SignupState state = (SignupState) evt.getNewValue();

            // Update error fields
            if (state.getUsernameError() != null && !state.getUsernameError().isEmpty()) {
                usernameErrorField.setText(state.getUsernameError());
            } else {
                usernameErrorField.setText("");
            }

            if (state.getEmailError() != null && !state.getEmailError().isEmpty()) {
                emailErrorField.setText(state.getEmailError());
            } else {
                emailErrorField.setText("");
            }

            if (state.getPasswordError() != null && !state.getPasswordError().isEmpty()) {
                passwordErrorField.setText(state.getPasswordError());
            } else {
                passwordErrorField.setText("");
            }

            if (state.getConfirmPasswordError() != null && !state.getConfirmPasswordError().isEmpty()) {
                confirmPasswordErrorField.setText(state.getConfirmPasswordError());
            } else {
                confirmPasswordErrorField.setText("");
            }

            // Clear password fields on error
            if (state.getPasswordError() != null || state.getConfirmPasswordError() != null) {
                passwordInputField.setText("");
                confirmPasswordInputField.setText("");
                signupViewModel.getState().setPassword("");
                signupViewModel.getState().setConfirmPassword("");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}