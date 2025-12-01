package use_case;

import entity.Flashcard;
import java.util.List;

public interface FlashcardDataAccessInterface {

    int nextFlashcardId();

    void save(Flashcard card);

    void update(int cardId);

    void delete(int cardId);

    Flashcard findById(int cardId);

    List<Flashcard> findByDeck(int deckId);

    void markCardAsKnown(int userId, int deckId, int cardIndex);

    void markCardAsUnknown(int cardIndex, int fromDeckId, int toDeckId);

    int getKnownCardsCount(int userId, int deckId);

    int getDeckSize(int userId, int deckId);
}
