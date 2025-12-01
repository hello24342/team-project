package interface_adapter.signup;

public class SignupState {

    private String username;
    private String usernameError;
    private String password;
    private String passwordError;
    private String email;
    private String emailError;
    private String confirmPassword;
    private String confirmPasswordError;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getConfirmPassword() { return confirmPassword; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public void setUsernameError(String errorMessage) { this.usernameError = errorMessage; }
    public void setEmailError(String errorMessage) { this.emailError = errorMessage; }
    public void setConfirmPasswordError(String errorMessage) { this.confirmPasswordError = errorMessage; }
    public void setPasswordError(String passwordError) { this.passwordError = passwordError; }

    public String getUsernameError() { return usernameError; }
    public String getPasswordError() { return passwordError; }
    public String getEmailError() { return emailError; }
    public String getConfirmPasswordError() { return confirmPasswordError; }

}
