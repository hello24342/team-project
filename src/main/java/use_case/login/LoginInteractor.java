package use_case.login;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class LoginInteractor implements LoginInputBoundary {

    public LoginUserDataAccessInterface loginUserDataAccessObject;
    public LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface loginUserDataAccessInterface,
                           LoginOutputBoundary loginPresenter) {
        this.loginUserDataAccessObject = loginUserDataAccessInterface;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!loginUserDataAccessObject.usernameExists(username)) {
            loginPresenter.loginFailureView(username + ": This account does not exist!");
        }
        else {
            final String pwd = loginUserDataAccessObject.getUser(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.loginFailureView("Incorrect password for \"" + username + "\".");
            }
            else {
                final User user = loginUserDataAccessObject.getUser(loginInputData.getUsername());
                loginUserDataAccessObject.setCurrentUsername(username);

                List<String> userDeckNames = getUserDeckNames(user);

                final LoginOutputData loginOutputData = new LoginOutputData(user.getUsername(), userDeckNames);
                loginPresenter.loginSuccessView(loginOutputData);
            }
        }
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
