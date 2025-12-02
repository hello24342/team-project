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
        if (!signupInputData.getPassword().equals(signupInputData.getConfirmPassword())) {
            signupPresenter.signupFailureView("Passwords don't match.");
            return;
        }

        if (userDataAccessObject.usernameExists(signupInputData.getUsername())) {
            signupPresenter.signupFailureView("User already exists.");
            return;
        }

        if (userDataAccessObject.emailExists(signupInputData.getEmail())) {
            signupPresenter.signupFailureView("Email already registered.");
            return;
        }

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

    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }
}