package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

public class LoginController {

    final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute(String username, String password) {
        System.out.println("=== LOGIN CONTROLLER ===");
        System.out.println("Username: " + username);
        System.out.println("Password length: " + (password != null ? password.length() : "null"));

        LoginInputData loginInputData = new LoginInputData(username, password);
        loginUseCaseInteractor.execute(loginInputData);
    }
}