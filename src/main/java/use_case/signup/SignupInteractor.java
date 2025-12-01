package use_case.signup;

import entity.User;
import entity.UserFactory;
import use_case.UserDataAccessInterface;

public class SignupInteractor implements SignupInputBoundary {

    private final UserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary signupPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(UserDataAccessInterface userDataAccessObject,
                            SignupOutputBoundary signupPresenter,
                            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.signupPresenter = signupPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String password = signupInputData.getPassword();
        final String email = signupInputData.getEmail();
        final String confirmPassword = signupInputData.getConfirmPassword();

        if (userDataAccessObject.usernameExists(username)) {
            signupPresenter.signupFailureView("Username already exists. Login instead!");
        }
        else if (!password.equals(confirmPassword)) {
            signupPresenter.signupFailureView("Passwords don't match.");
        }
        else if (email.isEmpty()) {
            signupPresenter.signupFailureView("Email cannot be empty.");
        }
        else if (userDataAccessObject.emailExists(email)) {
            signupPresenter.signupFailureView("Email already exists. Login instead!");
        }
        else if (password.isEmpty()) {
            signupPresenter.signupFailureView("New password cannot be empty");
        }
        else if (username.isEmpty()) {
            signupPresenter.signupFailureView("Username cannot be empty");
        }
        else {
            int userId = userDataAccessObject.getNextUserId();
            final User user = userFactory.create(userId, signupInputData.getUsername(),
                    signupInputData.getEmail(),
                    signupInputData.getPassword());
            user.setTotalKnownFlashcards(0);
            user.setTotalFlashcards(0);
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername());
            signupPresenter.signupSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }
}
