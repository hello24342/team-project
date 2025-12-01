package app.factory;

import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.UserDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.SignUpView;
import view.ViewManager;

public class LoginUseCaseFactory {

    public static class LoginBundle {
        public final LoginViewModel loginViewModel;
        public final LoginController loginController;
        public SignupController signupController;
        public ViewManager viewManager;

        public LoginBundle(LoginViewModel loginViewModel, LoginController loginController, SignupController signupController, ViewManager viewManager) {
            this.loginViewModel = loginViewModel;
            this.loginController = loginController;
            this.signupController = signupController;
            this.viewManager = viewManager;
        }
    }

    public static LoginBundle build(UserDataAccessInterface userDataAccess,
                                    ViewManager viewManager) {
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        UserFactory userFactory = new UserFactory();

        // Presenter
        LoginOutputBoundary loginPresenter = new LoginPresenter(
                viewManager, loggedInViewModel, loginViewModel);
        SignupOutputBoundary signupPresenter = new SignupPresenter(
                viewManager, loginViewModel, signupViewModel
        );

        // Interactor
        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccess, loginPresenter);
        SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccess, signupPresenter, userFactory);

        // Controller
        LoginController loginController = new LoginController(loginInteractor);
        SignupController signupController = new SignupController(signupInteractor);

        return new LoginBundle(loginViewModel, loginController, signupController, viewManager);
    }
}