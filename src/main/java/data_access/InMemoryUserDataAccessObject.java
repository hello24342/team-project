package data_access;

import entity.User;
import use_case.UserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccessObject implements UserDataAccessInterface
{
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final Map<String, User> usersByEmail = new HashMap<>();
    private String currentUsername;

    @Override
    public boolean usernameExists(String username) {
        return usersByUsername.containsKey(username);
    }

    @Override
    public boolean emailExists(String email) {
        return usersByEmail.containsKey(email);
    }

    @Override
    public int getNextUserId() { return usersByUsername.size() + 1; }

    @Override
    public User getUser(String username) {
        return usersByUsername.get(username);
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void save(User user) {
        usersByUsername.put(user.getUsername(), user);
        usersByEmail.put(user.getEmail(), user);
    }
}
