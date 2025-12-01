package interface_adapter.login;

public class LoginState {

    private String username = "";
    private String loginError;
    private String password = "";
    private boolean loginSuccess = false;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getLoginError() {
        return loginError;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

}
