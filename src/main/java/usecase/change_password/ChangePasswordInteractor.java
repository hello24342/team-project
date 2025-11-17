package usecase.change_password;

import entity.User;
import entity.UserFactory;

public class ChangePasswordInteractor implements ChangePasswordInputBoundary {

    private final ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface;
    private final ChangePasswordOutputBoundary changePasswordPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordPresenter, UserFactory userFactory) {
        this.changePasswordDataAccessInterface = changePasswordDataAccessInterface;
        this.changePasswordPresenter = changePasswordPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        if (changePasswordInputData.getPassword().isEmpty()) {
            changePasswordPresenter.prepareFailView("New password cannot be empty!");
        } else {
            final User user = userFactory.create(
                    changePasswordInputData.getUsername(),
                    changePasswordInputData.getPassword());
            changePasswordDataAccessInterface.changePassword(user);

            final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(user.getName());
            changePasswordPresenter.prepareSuccessView(changePasswordOutputData);
        }
    }
}
