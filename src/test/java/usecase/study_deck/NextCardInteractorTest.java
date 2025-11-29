package usecase.study_deck;

import data_access.FlashcardDataAccessObject;
import entity.Flashcard;
import entity.Language;
import org.junit.jupiter.api.Test;
import usecase.FlashcardDataAccessInterface;
import usecase.study_deck.next_card.NextCardInputData;
import usecase.study_deck.next_card.NextCardInteractor;
import usecase.study_deck.next_card.NextCardOutputBoundary;
import usecase.study_deck.next_card.NextCardOutputData;

import static org.junit.jupiter.api.Assertions.*;

class NextCardInteractorTest {
    @Test
    void testNoMoreCardsFailure() {
        NextCardInputData nextCardInputData = new NextCardInputData(1, 0);
        FlashcardDataAccessInterface flashcardDAO = new FlashcardDataAccessObject();

        Flashcard card = new Flashcard(9, "Hello", "Bonjour", Language.ENGLISH, Language.FRENCH_FRANCE);
        card.addDeck(1);
        flashcardDAO.save(card);

        NextCardOutputBoundary presenter = new NextCardOutputBoundary() {
            @Override
            public void presentSuccessView(NextCardOutputData outputData) {
                fail("Unexpected success");
            }
            @Override
            public void presentFailureView(String error) {
                assertEquals("No more cards in deck.", error);
            }
        };

        NextCardInteractor interactor = new NextCardInteractor(flashcardDAO, presenter);
        interactor.execute(nextCardInputData);
    }

    @Test
    void testNextCardSuccess() {
        NextCardInputData nextCardInputData = new NextCardInputData(10, 0);
        FlashcardDataAccessInterface flashcardDAO = new FlashcardDataAccessObject();

        Flashcard firstCard = new Flashcard(1, "Hello", "Bonjour", Language.ENGLISH, Language.FRENCH_FRANCE);
        firstCard.addDeck(10);
        flashcardDAO.save(firstCard);
        Flashcard secondCard = new Flashcard(2, "Thank you", "Merci", Language.ENGLISH, Language.FRENCH_FRANCE);
        secondCard.addDeck(10);
        flashcardDAO.save(secondCard);

        NextCardOutputBoundary presenter = new NextCardOutputBoundary() {
            @Override
            public void presentSuccessView(NextCardOutputData data) {
                assertEquals(10, data.getDeckId());
                assertEquals(1, data.getCardIndex());
                assertEquals("Thank you", data.getSourceWord());
                assertEquals("Merci", data.getTargetWord());
            }

            @Override
            public void presentFailureView(String error) {
                fail("Unexpected failure");
            }
        };

        NextCardInteractor interactor = new NextCardInteractor(flashcardDAO, presenter);
        interactor.execute(nextCardInputData);
    }
}
