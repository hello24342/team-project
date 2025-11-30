package app.factory;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LoginInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import view.ViewManager;

public class LoginUseCaseFactory {

    public static class LoginBundle {
        public final LoginViewModel loginViewModel;
        public final LoginController loginController;

        public LoginBundle(LoginViewModel loginViewModel, LoginController loginController) {
            this.loginViewModel = loginViewModel;
            this.loginController = loginController;
        }
    }

    public static LoginBundle build(LoginUserDataAccessInterface userDataAccess,
                                    ViewManager viewManager) {
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();

        ViewManagerModel viewManagerModel = viewManager.getViewManagerModel();

        // Presenter
        LoginOutputBoundary loginPresenter = new LoginPresenter(
                viewManagerModel, loggedInViewModel, loginViewModel);

        // Interactor
        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccess, loginPresenter);

        // Controller
        LoginController loginController = new LoginController(loginInteractor);

        return new LoginBundle(loginViewModel, loginController);
    }
}