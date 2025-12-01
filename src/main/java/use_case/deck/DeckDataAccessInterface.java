package use_case.deck;

import entity.FlashcardDeck;

import java.util.List;

public interface DeckDataAccessInterface {
        boolean existsByTitleForUser(int userId, String title);

        int nextDeckId();

        void save(FlashcardDeck deck);

        List<FlashcardDeck> findByUser(int userId);

        FlashcardDeck findById(int deckId);

        int findOrCreateDontKnowDeckId(int userId);

    }
