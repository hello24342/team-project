package data_access;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.User;
import use_case.UserDataAccessInterface;

public class FileUserDataAccessObject implements UserDataAccessInterface {
    private final String filePath;
    // Key: username
    private final Map<String, User> usersByUsername = new HashMap<>();
    // Key: email
    private final Map<String, User> usersByEmail = new HashMap<>();
    private final Map<Integer, User> usersById = new HashMap<>();
    private int nextUserId = 2;
    private String currentUsername;
    private Integer currentUserId;

    public FileUserDataAccessObject(String filePath) throws IOException {
        this.filePath = filePath;
        loadUsersFromFile();
        updateNextUserId();
    }

    private void updateNextUserId() {
        int maxId = 1; // Start from 1
        for (User user : usersByUsername.values()) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        nextUserId = maxId + 1;
    }

    public void addDeckToUser(String username, int deckId) {
        User user = usersByUsername.get(username);
        if (user != null && !user.getDeckIds().contains(deckId)) {
            user.getDeckIds().add(deckId);
            save(user);
        }
    }

    @Override
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        User user = usersById.get(userId);
        if (user != null) {
            this.currentUsername = user.getUsername();
        }
    }

    public void removeDeckFromUser(String username, int deckId) {
        User user = usersByUsername.get(username);
        if (user != null) {
            user.getDeckIds().remove((Integer) deckId);
            save(user);
        }
    }

    private void loadUsersFromFile() throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("#userId,username,email,password,totalKnownFlashcards,totalFlashcards,deckIds");
                writer.newLine();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    int userId = Integer.parseInt(parts[0].trim());
                    String username = parts[1].trim();
                    String email = parts[2].trim();
                    String password = parts[3].trim();

                    int totalKnownFlashcards = 0;
                    try {
                        totalKnownFlashcards = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid totalKnownFlashcards for user " + username + ": " + parts[4]);
                    }

                    int totalFlashcards = 0;
                    try {
                        totalFlashcards = Integer.parseInt(parts[5].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid totalFlashcards for user " + username + ": " + parts[5]);
                    }

                    List<Integer> userDeckIds = parseDeckIds(parts[6].trim());

                    User user = new User(userId, username, email, password);
                    user.setTotalKnownFlashcards(totalKnownFlashcards);
                    user.setTotalFlashcards(totalFlashcards);
                    user.setDeckIds(userDeckIds);

                    usersByUsername.put(username, user);
                    usersByEmail.put(email, user);
                    usersById.put(userId, user);
                }
            }
        }
    }

    private List<Integer> parseDeckIds(String deckIdsString) {
        List<Integer> deckIds = new ArrayList<>();
        if (deckIdsString == null || deckIdsString.trim().isEmpty()) {
            return deckIds;
        }

        deckIdsString = deckIdsString.replace("\"", "").replace("[", "").replace("]", "").trim();
        if (deckIdsString.isEmpty()) {
            return deckIds;
        }

        String[] idStrings = deckIdsString.split(",");
        for (String idStr : idStrings) {
            try {
                int deckId = Integer.parseInt(idStr.trim());
                deckIds.add(deckId);
            } catch (NumberFormatException e) {
                System.err.println("Invalid deck ID found: " + idStr);
            }
        }
        return deckIds;
    }

    private void saveUsersToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("#userId,username,email,password,totalKnownFlashcards,totalFlashcards,deckIds");
            writer.newLine();

            for (User user : usersByUsername.values()) {
                String line = String.format("%d,%s,%s,%s,%d,%d,%s",
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getTotalKnownFlashcards(),
                        user.getTotalFlashcards(),
                        user.getDeckIds());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementKnownFlashcards(String username) {
        User user = usersByUsername.get(username);
        if (user != null) {
            user.incrementKnownFlashcards();
            save(user);
        }
    }

    public String getUsernameFromId(int userId) {
        User user = usersById.get(userId);
        return user != null ? user.getUsername() : null;
    }

    public User getUserById(int userId) {
        return usersById.get(userId);
    }

    public void setTotalFlashcards(String username, int totalFlashcards) {
        User user = usersByUsername.get(username);
        if (user != null) {
            user.setTotalFlashcards(totalFlashcards);
            save(user);
        }
    }

    public int getTotalFlashcards(String username) {
        User user = usersByUsername.get(username);
        return user != null ? user.getTotalFlashcards() : 0;
    }

    public void incrementTotalFlashcards(String username, int count) {
        User user = usersByUsername.get(username);
        if (user != null) {
            user.incrementTotalFlashcards(count);
            save(user);
        }
    }

    public void decrementTotalFlashcards(String username, int count) {
        User user = usersByUsername.get(username);
        if (user != null) {
            user.decrementTotalFlashcards(count);
            save(user);
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
        User user = usersByUsername.get(username);
        if (user != null) {
            this.currentUserId = user.getUserId();
        }
    }

    @Override
    public void save(User user) {
        usersByUsername.put(user.getUsername(), user);
        usersByEmail.put(user.getEmail(), user);
        usersById.put(user.getUserId(), user);
        try {
            saveUsersToFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user data", e);
        }
    }

    @Override
    public int getCurrentUserId() {
        System.out.println("DEBUG getCurrentUserId(): currentUsername = '" + currentUsername + "'");

        if (currentUsername == null || currentUsername.trim().isEmpty()) {
            System.out.println("WARNING: No user logged in yet. currentUsername is null/empty.");
            return -1;
        }

        User user = usersByUsername.get(currentUsername);
        if (user == null) {
            System.err.println("ERROR: User '" + currentUsername + "' not found in database!");
            System.err.println("Available usernames: " + usersByUsername.keySet());
            return -1;
        }

        System.out.println("DEBUG: Found user ID: " + user.getUserId());
        return user.getUserId();
    }

    @Override
    public boolean emailExists(String email) {
        return usersByEmail.containsKey(email);
    }

    @Override
    public int getNextUserId() {
        int nextId = nextUserId;
        nextUserId++; // Increment for the next call
        return nextId;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }
}