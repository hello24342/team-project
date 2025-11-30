package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    boolean usernameExists(String username);
    void save(User user);
    User getUser(String username);
    void setCurrentUsername(String username);
    String getCurrentUsername();
}
