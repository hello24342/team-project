package data_access;

import entity.Flashcard;
import entity.Language;
import use_case.flashcard.FlashcardDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFlashcardDataAccessObject implements FlashcardDataAccessInterface {

    private int nextId = 1;
    private final Map<Integer, Flashcard> flashcards = new HashMap<>();
    private final Map<Integer, Integer> knownCountCache = new HashMap<>(); // deckId -> knownCount

    private final Map<Integer, Integer> deckSizeCache = new HashMap<>(); // deckId -> total cards

    public InMemoryFlashcardDataAccessObject() {
        // No file initialization needed
    }

    // Initialize with some test data if needed
    public InMemoryFlashcardDataAccessObject(boolean addTestData) {
        if (addTestData) {
            addTestFlashcards();
        }
    }

    private void addTestFlashcards() {
        // Add some test flashcards
        Flashcard card1 = new Flashcard(nextId++, "hello", "hola", Language.ENGLISH, Language.SPANISH);
        card1.getDeckIds().add(1);
        card1.setKnown(true);
        save(card1);

        Flashcard card2 = new Flashcard(nextId++, "goodbye", "adiós", Language.ENGLISH, Language.SPANISH);
        card2.getDeckIds().add(1);
        save(card2);

        Flashcard card3 = new Flashcard(nextId++, "thank you", "gracias", Language.ENGLISH, Language.SPANISH);
        card3.getDeckIds().add(2);
        card3.setKnown(true);
        save(card3);
    }

    // interface methods
    @Override
    public int nextFlashcardId() {
        return nextId++;
    }

    @Override
    public void save(Flashcard card) {
        flashcards.put(card.getId(), card);

        // Update deck size cache
        for (int deckId : card.getDeckIds()) {
            deckSizeCache.put(deckId, deckSizeCache.getOrDefault(deckId, 0) + 1);

            // Update known count cache if card is known
            if (card.isKnown()) {
                knownCountCache.put(deckId, knownCountCache.getOrDefault(deckId, 0) + 1);
            }
        }
    }

    @Override
    public void update(int cardId) {
        // For in-memory, save handles updates too
        Flashcard card = flashcards.get(cardId);
        if (card != null) {
            save(card); // This will update caches
        }
    }

    @Override
    public void delete(int cardId) {
        Flashcard card = flashcards.remove(cardId);
        if (card != null) {
            // Update deck size cache
            for (int deckId : card.getDeckIds()) {
                deckSizeCache.put(deckId, Math.max(0, deckSizeCache.getOrDefault(deckId, 0) - 1));

                // Update known count cache if card was known
                if (card.isKnown()) {
                    knownCountCache.put(deckId, Math.max(0, knownCountCache.getOrDefault(deckId, 0) - 1));
                }
            }
        }
    }

    @Override
    public Flashcard findById(int cardId) {
        return flashcards.get(cardId);
    }

    @Override
    public List<Flashcard> findByDeck(int deckId) {
        List<Flashcard> result = new ArrayList<>();
        for (Flashcard card : flashcards.values()) {
            if (card.getDeckIds().contains(deckId)) {
                result.add(card);
            }
        }
        return result;
    }

    @Override
    public void markCardAsKnown(int userId, int deckId, int cardIndex) {
        List<Flashcard> deck = findByDeck(deckId);
        if (cardIndex >= 0 && cardIndex < deck.size()) {
            Flashcard card = deck.get(cardIndex);
            if (!card.isKnown()) {
                card.setKnown(true);
                knownCountCache.put(deckId, knownCountCache.getOrDefault(deckId, 0) + 1);
            }
        }
    }

    @Override
    public void markCardAsUnknown(int cardIndex, int fromDeckId, int toDeckId) {
        List<Flashcard> fromDeck = findByDeck(fromDeckId);

        if (cardIndex >= 0 && cardIndex < fromDeck.size()) {
            Flashcard cardToMove = fromDeck.get(cardIndex);

            // If the card was known, update cache for the from deck
            if (cardToMove.isKnown()) {
                knownCountCache.put(fromDeckId,
                        Math.max(0, knownCountCache.getOrDefault(fromDeckId, 0) - 1));
            }

            // Remove from old deck and add to new deck
            cardToMove.getDeckIds().remove((Integer) fromDeckId);
            cardToMove.getDeckIds().add(toDeckId);

            // Update deck size cache
            deckSizeCache.put(fromDeckId, Math.max(0, deckSizeCache.getOrDefault(fromDeckId, 0) - 1));
            deckSizeCache.put(toDeckId, deckSizeCache.getOrDefault(toDeckId, 0) + 1);

            // If the card is known, update cache for the to deck
            if (cardToMove.isKnown()) {
                knownCountCache.put(toDeckId, knownCountCache.getOrDefault(toDeckId, 0) + 1);
            }
        }
    }

    @Override
    public int getKnownCardsCount(int userId, int deckId) {
        return knownCountCache.getOrDefault(deckId, 0);
    }

    @Override
    public int getDeckSize(int userId, int deckId) {
        return deckSizeCache.getOrDefault(deckId, 0);
    }

    // Additional helper methods for testing

    public int getTotalFlashcards() {
        return flashcards.size();
    }

    public int getTotalKnownFlashcards() {
        int total = 0;
        for (Flashcard card : flashcards.values()) {
            if (card.isKnown()) {
                total++;
            }
        }
        return total;
    }

    public void clearAll() {
        flashcards.clear();
        knownCountCache.clear();
        deckSizeCache.clear();
        nextId = 1;
    }
}