package usecase.logout;

public class LogoutOutputData {
    private final String username;

    public LogoutOutputData(String username){ this.username = username; }

    public String getUsername() { return username; }
}
