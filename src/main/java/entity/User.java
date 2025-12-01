package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int userId;
    private final String username;
    private final String email;
    private final String password;
    private final List<FlashcardDeck> decks;
    private int totalKnownFlashcards;
    private List<Integer> userDeckIds;
    private int totalFlashcards;

    public User(int userId, String username, String email, String password) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.totalKnownFlashcards = 0;
        this.totalFlashcards = 0;
        this.userDeckIds = new ArrayList<>();
        this.decks = new ArrayList<>();
    }

    public int getUserId() { return userId; }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<FlashcardDeck> getDecks() {
        return new ArrayList<>(decks);
    }

    public List<Integer> getDeckIds() {
        return userDeckIds;
    };

    public void setDeckIds(List<Integer> userDeckIds) { this.userDeckIds = userDeckIds; };

    public int getTotalFlashcards() {
        return totalFlashcards;
    }

    public void setTotalFlashcards(int totalFlashcards) {
        this.totalFlashcards = Math.max(0, totalFlashcards);
    }

    public void incrementTotalFlashcards(int count) {
        this.totalFlashcards += count;
    }

    public void decrementTotalFlashcards(int count) {
        this.totalFlashcards = Math.max(0, this.totalFlashcards - count);
    }

    /**
     * Add a deck to the user's list of decks.
     *
     * @param deck the deck to add
     */
    public void addDeck(FlashcardDeck deck) {
        if (deck != null) {
            this.decks.add(deck);
        }
    }

    /**
     * Remove a deck from the user's list of decks.
     *
     * @param deck the deck to remove
     */
    public void removeDeck(FlashcardDeck deck) {
        this.decks.remove(deck);
    }

    public int getTotalKnownFlashcards() {
        return totalKnownFlashcards;
    }

    public void setTotalKnownFlashcards(int totalKnownFlashcards) {
        this.totalKnownFlashcards = totalKnownFlashcards;
    }

    public void incrementKnownFlashcards() {
        this.totalKnownFlashcards++;
    }

    public void decrementKnownFlashcards() {
        this.totalKnownFlashcards = Math.max(0, this.totalKnownFlashcards - 1);
    }
}