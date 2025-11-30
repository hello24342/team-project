package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

public class SignupController {
    private final SignupInputBoundary signupUseCaseInteractor;

    public SignupController(SignupInputBoundary signupUseCaseInteractor) {
        this.signupUseCaseInteractor = signupUseCaseInteractor;
    }

    public void execute(String username, String email, String password, String confirmPassword) {
        final SignupInputData signupInputData = new SignupInputData(username, password, email, confirmPassword);
        signupUseCaseInteractor.execute(signupInputData);
    }

    public void switchToLoginView() {
        signupUseCaseInteractor.switchToLoginView();
    }
}
