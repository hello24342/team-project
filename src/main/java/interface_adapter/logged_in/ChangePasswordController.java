package interface_adapter.logged_in;

import usecase.change_password.ChangePasswordInputBoundary;
import usecase.change_password.ChangePasswordInputData;

public class ChangePasswordController {
    private final ChangePasswordInputBoundary userChangePasswordUseCaseInteractor;

    public ChangePasswordController(ChangePasswordInputBoundary userChangePasswordUseCaseInteractor) {
        this.userChangePasswordUseCaseInteractor = userChangePasswordUseCaseInteractor;
    }

    public void execute(String password, String username) {
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(username, password);
        userChangePasswordUseCaseInteractor.execute(changePasswordInputData);
    }
}
