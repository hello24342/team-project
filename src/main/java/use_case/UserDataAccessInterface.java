package use_case;

import entity.User;

public interface UserDataAccessInterface {
    boolean usernameExists(String username);
    void save(User user);
    User getUser(String username);
    void setCurrentUsername(String username);
    String getCurrentUsername();
    boolean emailExists(String email);
    int getNextUserId();
}
