package usecase;

import entity.Flashcard;
import java.util.List;

public interface FlashcardDataAccessInterface {

    int nextFlashcardId();

    void save(int userId, Flashcard card);

    void update(int cardId);

    void delete(int cardId);

    Flashcard findById(int cardId);

    List<Flashcard> findByDeck(int deckId);
}
