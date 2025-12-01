package use_case.signup;

import entity.User;

public interface SignupUserDataAccessInterface {
    boolean usernameExists(String username);
    void save(User user);
    boolean emailExists(String email);
    int getNextUserId();
}
