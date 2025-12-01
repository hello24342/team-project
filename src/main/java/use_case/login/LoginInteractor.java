package use_case.login;

import entity.FlashcardDeck;
import entity.User;
import use_case.UserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class LoginInteractor implements LoginInputBoundary {

    public UserDataAccessInterface userDataAccessObject;
    public LoginOutputBoundary loginPresenter;

    public LoginInteractor(UserDataAccessInterface userDataAccessObject,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        System.out.println("=== LOGIN INTERACTOR ===");
        System.out.println("Input username: '" + username + "'");
        System.out.println("Input password: '" + password + "'");

        // Check if user exists
        boolean userExists = userDataAccessObject.usernameExists(username);
        System.out.println("User exists: " + userExists);

        if (!userExists) {
            System.out.println("LOGIN FAIL: User doesn't exist");
            loginPresenter.loginFailureView("User doesn't exist");
            return;
        }

        final String storedPassword = userDataAccessObject.getUser(username).getPassword();
        if (!password.equals(storedPassword)) {
            System.out.println("LOGIN FAIL: Incorrect password");
            loginPresenter.loginFailureView("Incorrect password for \"" + username + "\".");
            return;
        }

        System.out.println("LOGIN SUCCESS: " + username);
        final User user = userDataAccessObject.getUser(username);
        userDataAccessObject.setCurrentUsername(username);
        userDataAccessObject.setCurrentUserId(user.getUserId());

        List<String> userDeckNames = getUserDeckNames(user);

        final LoginOutputData loginOutputData = new LoginOutputData(user.getUsername(), userDeckNames);
        loginPresenter.loginSuccessView(loginOutputData);
    }

    public List<String> getUserDeckNames(User user) {
        List<String> userDeckNames = new ArrayList<>();

        if (user.getDecks() != null) {
            for (FlashcardDeck deck : user.getDecks()) {
                userDeckNames.add(deck.getTitle());
            }
        }
        return userDeckNames;
    }
}