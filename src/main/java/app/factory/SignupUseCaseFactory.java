package app.factory;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.UserDataAccessInterface;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupOutputBoundary;
import entity.UserFactory;
import view.ViewManager;

public class SignupUseCaseFactory {

    public static class SignupBundle {
        public final SignupViewModel signupViewModel;
        public final SignupController signupController;

        public SignupBundle(SignupViewModel signupViewModel, SignupController signupController) {
            this.signupViewModel = signupViewModel;
            this.signupController = signupController;
        }
    }

    public static SignupBundle build(UserDataAccessInterface userDataAccess,
                                     ViewManager viewManager) {
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        ViewManagerModel viewManagerModel = viewManager.getViewManagerModel();
        UserFactory userFactory = new UserFactory();

        // Presenter
        SignupOutputBoundary signupPresenter = new SignupPresenter(
                viewManagerModel, loginViewModel, signupViewModel);

        // Interactor
        SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccess, signupPresenter, userFactory);

        // Controller
        SignupController signupController = new SignupController(signupInteractor);

        return new SignupBundle(signupViewModel, signupController);
    }
}