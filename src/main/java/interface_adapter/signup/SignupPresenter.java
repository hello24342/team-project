package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
import view.ViewManager;

public class SignupPresenter implements SignupOutputBoundary{
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManager viewManager;

    public SignupPresenter(ViewManager viewManager,
                           LoginViewModel loginViewModel,
                           SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManager = viewManager;
    }

    @Override
    public void signupSuccessView(SignupOutputData signupOutputData) {
        //update the signupViewModel state, switch to log in view on success
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(signupOutputData.getUsername());
        loginViewModel.firePropertyChange();
        viewManager.show("Login");
    }

    @Override
    public void signupFailureView(String errorMessage) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(errorMessage);
        signupViewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManager.show("Login");
    }
}
