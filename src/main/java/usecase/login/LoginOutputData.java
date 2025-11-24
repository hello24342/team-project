package usecase.login;

import java.util.ArrayList;
import java.util.List;

public class LoginOutputData {
    private final String username;
    private final List<String> userDeckNames;

    public LoginOutputData(String username, List<String> userDeckNames) {

        this.username = username;
        this.userDeckNames = userDeckNames != null ? new ArrayList<>(userDeckNames) : new ArrayList<>();

    }

    public String getUsername() {
        return username;
    }

    public List<String> getUserDeckNames() { return userDeckNames; }
}
