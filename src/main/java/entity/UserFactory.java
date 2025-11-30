package entity;

public class UserFactory {
    /**
     * Creates a new User instance with the specified name, email, and password.
     *
     * @param name the username for the user, must not be null or empty
     * @param email the email address for the user, must not be null or empty
     * @param password the password for the user, must not be null or empty
     * @return a new User object initialized with the provided name, email, and password
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public User create(String name, String email, String password) {
        return new User(name, email, password);
    }
}
