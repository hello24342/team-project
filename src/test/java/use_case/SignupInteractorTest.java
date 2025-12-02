package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.signup.*;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "paul123@example.com", "password");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void signupSuccessView(SignupOutputData signupOutputData) {
                assertEquals("Paul", signupOutputData.getUsername());
                assertTrue(userRepository.usernameExists("Paul"));
            }

            @Override
            public void signupFailureView(String errorMessage) {
                fail("Use case failure is unexpected. Error: " + errorMessage); // Added error message for debugging
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password1", "paul123@example.com", "password2");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void signupSuccessView(SignupOutputData signupOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void signupFailureView(String errorMessage) {
                assertEquals("Passwords don't match.", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "paul123@example.com", "password");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new UserFactory();
        User user = factory.create(1,"Paul", "paul123@example.com", "pwd");
        userRepository.save(user);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void signupSuccessView(SignupOutputData signupOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void signupFailureView(String errorMessage) {
                assertEquals("User already exists.", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }
}