package use_case;

import data_access.InMemoryUserDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.login.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new UserFactory();
        User user = factory.create(1,"Paul", "paul123@example.com", "password");
        userRepository.save(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void loginSuccessView(LoginOutputData loginOutputData) {
                assertEquals("Paul", user.getUsername());
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void loginFailureView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new UserFactory();
        User user = factory.create(1, "Paul", "paul123@example.com", "password");
        userRepository.save(user);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void loginSuccessView(LoginOutputData loginOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void loginFailureView(String errorMessage) {
                assertEquals("Incorrect password for \"Paul\".", errorMessage);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        UserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void loginSuccessView(LoginOutputData loginOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void loginFailureView(String errorMessage) {
                assertEquals("Paul: Account does not exist.", errorMessage);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}