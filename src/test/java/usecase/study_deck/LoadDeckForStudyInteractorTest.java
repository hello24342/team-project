package usecase.study_deck;

import data_access.InMemoryDeckDataAccessObject;
import data_access.InMemoryFlashcardDataAccessObject;
import entity.Flashcard;
import entity.FlashcardDeck;
import entity.Language;
import org.junit.jupiter.api.Test;
import usecase.FlashcardDataAccessInterface;
import usecase.deck.DeckDataAccessInterface;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputData;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInteractor;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyOutputBoundary;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LoadDeckForStudyInteractorTest {

    @Test
    void testNullDeckFailure() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(1, 1);
        FlashcardDataAccessInterface flashcardDataAccessInterface = new InMemoryFlashcardDataAccessObject();
        DeckDataAccessInterface deckDataAccessInterface = new InMemoryDeckDataAccessObject();

        LoadDeckForStudyOutputBoundary presenter = new LoadDeckForStudyOutputBoundary() {
            @Override
            public void presentSuccessView(LoadDeckForStudyOutputData data) {
                fail("Unexpected success");
            }

            @Override
            public void presentFailureView(String error) {
                assertEquals("Deck not found.", error);
            }
        };

        LoadDeckForStudyInteractor interactor = new LoadDeckForStudyInteractor(presenter, deckDataAccessInterface,
                flashcardDataAccessInterface);
        interactor.execute(inputData);
    }

    @Test
    void testLoadDeckSuccess() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(1, 1);
        FlashcardDataAccessInterface flashcardDataAccessInterface = new InMemoryFlashcardDataAccessObject();
        DeckDataAccessInterface deckDataAccessInterface = new InMemoryDeckDataAccessObject();

        FlashcardDeck deck = new FlashcardDeck(1, "Conversational French Words", 1);
        deckDataAccessInterface.save(deck);

        Flashcard card = new Flashcard(9, "Hello", "Bonjour", Language.ENGLISH, Language.FRENCH);
        card.addDeck(deck.getId());
        flashcardDataAccessInterface.save(card);

        LoadDeckForStudyOutputBoundary presenter = new LoadDeckForStudyOutputBoundary() {
            @Override
            public void presentSuccessView(LoadDeckForStudyOutputData data) {
                assertEquals(1, data.getDeckId());
                assertEquals("Conversational French Words", data.getDeckTitle());
                assertEquals(9, data.getCardId());
                assertEquals("Hello", data.getSourceWord());
                assertEquals("Bonjour", data.getTargetWord());
                assertEquals(1, data.getCardCount());
                assertEquals(0, data.getCurrentCardIndex());
            }

            @Override
            public void presentFailureView(String error) {
                fail("Unexpected failure");
            }
        };

        LoadDeckForStudyInteractor interactor = new LoadDeckForStudyInteractor(presenter, deckDataAccessInterface,
                flashcardDataAccessInterface);
        interactor.execute(inputData);
    }
}
