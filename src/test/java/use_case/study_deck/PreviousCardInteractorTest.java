package use_case.study_deck;

import data_access.InMemoryFlashcardDataAccessObject;
import entity.Flashcard;
import entity.Language;
import org.junit.jupiter.api.Test;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.study_deck.previous_card.PreviousCardInputData;
import use_case.study_deck.previous_card.PreviousCardInteractor;
import use_case.study_deck.previous_card.PreviousCardOutputBoundary;
import use_case.study_deck.previous_card.PreviousCardOutputData;

import static org.junit.jupiter.api.Assertions.*;

class PreviousCardInteractorTest {
    @Test
    void testNoMoreCardsFailure() {
        PreviousCardInputData nextCardInputData = new PreviousCardInputData(1, 0);
        FlashcardDataAccessInterface flashcardDAO = new InMemoryFlashcardDataAccessObject();

        Flashcard card = new Flashcard(9, "Hello", "Bonjour", Language.ENGLISH, Language.FRENCH);
        card.addDeck(1);
        flashcardDAO.save(card);

        PreviousCardOutputBoundary presenter = new PreviousCardOutputBoundary() {
            @Override
            public void presentSuccessView(PreviousCardOutputData outputData) {
                fail("Unexpected success");
            }
            @Override
            public void presentFailureView(String error) {
                assertEquals("Reached first card in deck.", error);
            }
        };

        PreviousCardInteractor interactor = new PreviousCardInteractor(flashcardDAO, presenter);
        interactor.execute(nextCardInputData);
    }

    @Test
    void testPreviousCardSuccess() {
        PreviousCardInputData nextCardInputData = new PreviousCardInputData(10, 1);
        FlashcardDataAccessInterface flashcardDAO = new InMemoryFlashcardDataAccessObject();

        Flashcard firstCard = new Flashcard(1, "Hello", "Bonjour", Language.ENGLISH, Language.FRENCH);
        firstCard.addDeck(10);
        flashcardDAO.save(firstCard);
        Flashcard secondCard = new Flashcard(2, "Thank you", "Merci", Language.ENGLISH, Language.FRENCH);
        secondCard.addDeck(10);
        flashcardDAO.save(secondCard);

        PreviousCardOutputBoundary presenter = new PreviousCardOutputBoundary() {
            @Override
            public void presentSuccessView(PreviousCardOutputData data) {
                assertEquals(10, data.getDeckId());
                assertEquals(0, data.getCardIndex());
                assertEquals("Hello", data.getSourceWord());
                assertEquals("Bonjour", data.getTargetWord());
            }

            @Override
            public void presentFailureView(String error) {
                fail("Unexpected failure");
            }
        };

        PreviousCardInteractor interactor = new PreviousCardInteractor(flashcardDAO, presenter);
        interactor.execute(nextCardInputData);
    }
}
