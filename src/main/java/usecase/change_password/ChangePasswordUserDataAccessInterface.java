package usecase.change_password;

import entity.User;

public interface ChangePasswordUserDataAccessInterface {
    void changePassword(User user);
}
