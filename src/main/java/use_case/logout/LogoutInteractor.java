package use_case.logout;

import use_case.UserDataAccessInterface;

public class LogoutInteractor implements LogoutInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(UserDataAccessInterface userDataAccess,
                            LogoutOutputBoundary logoutPresenter) {
        this.userDataAccess = userDataAccess;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute() {
        String username = userDataAccess.getCurrentUsername();
        userDataAccess.setCurrentUsername(null);
        LogoutOutputData logoutOutputData = new LogoutOutputData(username);
        logoutPresenter.logoutSuccessView(logoutOutputData);
    }
}
