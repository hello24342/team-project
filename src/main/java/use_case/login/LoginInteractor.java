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

        final User user = userDataAccessObject.getUser(username);
        if (user == null) {
            loginPresenter.loginFailureView(username + ": Account does not exist.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            loginPresenter.loginFailureView("Incorrect password for \"" + username + "\".");
            return;
        }

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