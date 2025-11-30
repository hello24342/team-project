package use_case.login;

public interface LoginOutputBoundary {
    void loginSuccessView(LoginOutputData loginOutputData);
    void loginFailureView(String errorMessage);
}
