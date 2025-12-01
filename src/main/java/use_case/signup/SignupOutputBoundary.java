package use_case.signup;

public interface SignupOutputBoundary {
    void signupSuccessView(SignupOutputData signupOutputData);
    void signupFailureView(String errorMessage);
    void switchToLoginView();
}
