package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final String email;
    private final String password;
    private final List<FlashcardDeck> decks;

    public User(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.username = username;
        this.email = email;
        this.password = password;
        this.decks = new ArrayList<>();
    }

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

    public void addDeck(FlashcardDeck deck) {
        if (deck != null) {
            this.decks.add(deck);
        }
    }

    public void removeDeck(FlashcardDeck deck) {
        this.decks.remove(deck);
    }
}