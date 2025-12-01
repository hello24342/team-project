package interface_adapter.logged_in;

import java.util.ArrayList;
import java.util.List;

public class LoggedInState {
    private String username = "";
    private String password = "";
    private List<String> userDecks = new ArrayList<>();
    private String passwordError;

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
        userDecks = new ArrayList<>(copy.userDecks);
    }

    public LoggedInState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public List<String> getUserDecks() {
        return userDecks;
    };

    public void setUserDecks(List<String> userDecks) {
        this.userDecks = userDecks;
    }

    public void addUserDeck(String userDeck) {
        userDecks.add(userDeck);
    }

    public void removeUserDeck(String userDeck) {
        userDecks.remove(userDeck);
    }

    public void clearUserDecks() {
        userDecks.clear();
    }

    public boolean hasDecks() {
        return !this.userDecks.isEmpty();
    }
}
