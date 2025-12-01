package use_case.logout;

import use_case.login.LoginUserDataAccessInterface;

public class LogoutInteractor implements LogoutInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LoginUserDataAccessInterface userDataAccess,
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
