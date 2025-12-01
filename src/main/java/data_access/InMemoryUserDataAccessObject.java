package data_access;

import entity.User;
import use_case.UserDataAccessInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserDataAccessObject implements UserDataAccessInterface {
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
    public int getNextUserId() {
        return usersByUsername.size() + 1;
    }

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

    public String getUsernameFromId(int userId) {
        for (User user : usersByUsername.values()) {
            if (user.getUserId() == userId) {
                return user.getUsername();
            }
        }
        return null; // or throw an exception if user not found
    }

    ;

    @Override
    public void addDeckToUser(String username, int deckId) {
        User user = usersByUsername.get(username);
        if (user == null) {
            return;
        }

        List<Integer> deckIds = user.getDeckIds();

        if (!deckIds.contains(deckId)) {
            deckIds.add(deckId);
            user.setDeckIds(deckIds);
        }
    }
}
