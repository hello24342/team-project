package data_access;

import entity.Flashcard;
import usecase.FlashcardDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFlashcardDataAccessObject implements FlashcardDataAccessInterface {
    // temporary DAO for testing will be replaced later
    private final Map<Integer, Flashcard> flashcards = new HashMap<>();
    private int nextId = 1;

    public int nextFlashcardId() {
        return nextId++;
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
        Flashcard card = deck.get(cardIndex);
        card.setKnown(true);
    }

    @Override
    public void markCardAsUnknown(int cardIndex, int fromDeckId, int toDeckId) {
        List<Flashcard> fromDeck = findByDeck(fromDeckId);
        Flashcard cardToMove = fromDeck.get(cardIndex);
        cardToMove.setKnown(false);
        cardToMove.getDeckIds().add(toDeckId);
    }

    @Override
    public void save(Flashcard flashcard) {
        flashcards.put(flashcard.getId(), flashcard);
    }

    @Override
    public void update(int cardId) {
    }

    @Override
    public void delete(int cardId) {

    }

    @Override
    public Flashcard findById(int cardId) {
        return flashcards.get(cardId);
    }
}