package use_case.signup;

public interface SignupInputBoundary {
    void execute(SignupInputData signupInputData);
    // alternate flow when user clicks on login button
    void switchToLoginView();
}