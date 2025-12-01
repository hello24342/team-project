package use_case;

import entity.User;

public interface UserDataAccessInterface {
    boolean usernameExists(String username);
    void save(User user);
    int getCurrentUserId();
    User getUser(String username);
    void setCurrentUsername(String username);
    String getCurrentUsername();
    boolean emailExists(String email);
    int getNextUserId();
    String getUsernameFromId(int userId);
    void addDeckToUser(String username, int deckId);
    void setCurrentUserId(int userId);
}
