package interface_adapter.login;


import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import view.ViewManager;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManager viewManager;

    public LoginPresenter(ViewManager viewManager,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManager = viewManager;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void loginSuccessView(LoginOutputData response) {
        System.out.println("=== LOGIN PRESENTER: SUCCESS ===");
        System.out.println("Username: " + response.getUsername());

        // Update LoggedInState
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        loggedInState.setUserDecks(response.getUserDeckNames());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();

        // Switch to logged in view
        viewManager.show("LoggedIn");
    }

    @Override
    public void loginFailureView(String error) {
        System.out.println("=== LOGIN PRESENTER: FAILURE ===");
        System.out.println("Error: " + error);

        // Update LoginState with error
        LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChange();
    }
}
