package data_access;

import entity.FlashcardDeck;
import usecase.deck.DeckDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDeckDataAccessObject implements DeckDataAccessInterface {
    private final Map<Integer, List<FlashcardDeck>> decksByUser;
    private final Map<Integer, Integer> dontKnowDeckIdByUser;
    private int nextDeckId;

    public InMemoryDeckDataAccessObject() {
        this.decksByUser = new HashMap<>();
        this.dontKnowDeckIdByUser = new HashMap<>();
        this.nextDeckId = 1;
    }


    //Check if the deck title exists for the user
    @Override
    public boolean existsByTitleForUser(int userId, String title) {
        List<FlashcardDeck> decks = decksByUser.get(userId);
        if (decks != null) {
            for (FlashcardDeck deck : decks) {
                if (deck.getTitle().equals(title)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public int nextDeckId() {
        int id = nextDeckId;
        nextDeckId++;
        return id;
    }

    //save the new deck
    @Override
    public void save(FlashcardDeck deck) {
        int userId = deck.getUserId();
        List<FlashcardDeck> list = decksByUser.get(userId);
        if (list == null) {
            list = new ArrayList<>();
            decksByUser.put(userId, list);
        }
        list.add(deck);

    }

    //Retrieve all decks for a user
    @Override
    public List<FlashcardDeck> findByUser(int userId) {
        List<FlashcardDeck> decks = decksByUser.get(userId);
        if (decks == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(decks);
    }

    @Override
    public FlashcardDeck findById(int deckId) {
        for (List<FlashcardDeck> list : decksByUser.values()) {
            for (FlashcardDeck deck : list) {
                if (deck.getId() == deckId) {
                    return deck;
                }
            }
        }
        return null;
    }

    //Find the id of or create the "Don't know" deck for a user
    @Override
    public int findOrCreateDontKnowDeckId(int userId) {
        Integer cached = dontKnowDeckIdByUser.get(userId);
        if (cached != null) {
            return cached;
        }
        int newId = nextDeckId();
        FlashcardDeck dk = new FlashcardDeck(newId, "Don't know deck", userId);
        save(dk);
        dontKnowDeckIdByUser.put(userId, newId);
        return newId;
    }
}
