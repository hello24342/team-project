package app.factory;

import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.deck.DeckMenuViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import use_case.UserDataAccessInterface;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutOutputBoundary;
import view.ViewManager;

public class LogoutUseCaseFactory {

    public static class LogoutBundle {
        public final LoggedInViewModel loggedInViewModel;
        public final LogoutController logoutController;
        public DeckMenuViewModel deckMenuViewModel;

        public LogoutBundle(LoggedInViewModel loggedInViewModel, LogoutController logoutController, DeckMenuViewModel deckMenuViewModel) {
            this.loggedInViewModel = loggedInViewModel;
            this.logoutController = logoutController;
            this.deckMenuViewModel = deckMenuViewModel;
        }
    }

    public static LogoutBundle build(ViewManager viewManager,
                                     UserDataAccessInterface userDataAccess) {
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        DeckMenuViewModel deckMenuViewModel = new DeckMenuViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        ViewManagerModel viewManagerModel = viewManager.getViewManagerModel();

        // Presenter
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(viewManagerModel, loggedInViewModel, loginViewModel);

        // Interactor
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(
                userDataAccess, logoutPresenter);

        // Controller
        LogoutController logoutController = new LogoutController(logoutInteractor);

        return new LogoutBundle(loggedInViewModel, logoutController, deckMenuViewModel);
    }
}