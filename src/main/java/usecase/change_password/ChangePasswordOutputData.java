package usecase.change_password;

public class ChangePasswordOutputData {

    private final String password;

    public ChangePasswordOutputData(String password) {
        this.password = password;
    }
    public String getPassword() { return password; }

}
