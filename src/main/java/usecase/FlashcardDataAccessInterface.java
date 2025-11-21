package usecase;

import entity.Flashcard;

import java.util.List;

public interface FlashcardDataAccessInterface {
    int nextFlashcardId();

    void save(Flashcard card);

    Flashcard findById(int cardId);

    List<Flashcard> findByDeck(int deckId);
}
