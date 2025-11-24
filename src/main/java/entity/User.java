package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private final List<FlashcardDeck> decks;

    /*
    Creates a new user with the given name and password,
    throws IllegalArgumentException if username or password is empty
     */
    public User(String name, String password) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.name = name;
        this.password = password;
        this.decks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() { return password; }

    public List<FlashcardDeck> getUserDecks() {
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
