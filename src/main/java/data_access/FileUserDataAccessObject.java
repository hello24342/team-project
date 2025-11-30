package data_access;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

public class FileUserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final String filePath;
    // Key: username
    private final Map<String, User> usersByUsername = new HashMap<>();
    // Key: email
    private final Map<String, User> usersByEmail = new HashMap<>();
    private String currentUsername;

    public FileUserDataAccessObject(String filePath) throws IOException {
        this.filePath = filePath;
        loadUsersFromFile();
    }

    private void loadUsersFromFile() throws IOException {
        File file = new File(filePath);

        // Create file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String email = parts[1].trim();
                    String password = parts[2].trim();

                    User user = new User(username, email, password);
                    usersByUsername.put(username, user);
                    usersByEmail.put(email, user);
                }
            }
        }
    }

    private void saveUsersToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("Data: username,email,password");
            writer.newLine();

            for (User user : usersByUsername.values()) {
                String line = String.format("%s,%s,%s",
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public boolean usernameExists(String username) {
        return usersByUsername.containsKey(username);
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
    public void save(User user) {
        usersByUsername.put(user.getUsername(), user);
        usersByEmail.put(user.getEmail(), user);
        try {
            saveUsersToFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user data", e);
        }
    }

    @Override
    public boolean emailExists(String email) {
        return usersByEmail.containsKey(email);
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Clear the current user.
     */
    public void clearCurrentUser() {
        this.currentUsername = null;
    }

    /**
     * Validate a user's credentials.
     * @param username username
     * @param password password
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateUser(String username, String password) {
        User user = usersByUsername.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public int getUserCount() {
        return usersByUsername.size();
    }
}
