package usecase.study_deck;

import data_access.InMemoryFlashcardDataAccessObject;
import entity.Flashcard;
import entity.Language;
import org.junit.jupiter.api.Test;
import usecase.FlashcardDataAccessInterface;
import usecase.study_deck.flip_card.FlipCardInputData;
import usecase.study_deck.flip_card.FlipCardInteractor;
import usecase.study_deck.flip_card.FlipCardOutputBoundary;
import usecase.study_deck.flip_card.FlipCardOutputData;

import static org.junit.jupiter.api.Assertions.*;

class FlipCardInteractorTest {

    @Test
    void TestStartsWithSource_FlipToBack() {
        FlipCardInputData flipCardInputData = new FlipCardInputData(1, 0, true, true);
        FlashcardDataAccessInterface flashcardDataAccessInterface = new InMemoryFlashcardDataAccessObject();
        Flashcard card = new Flashcard(1, "Hello", "Bonjour", Language.ENGLISH,
                Language.FRENCH);
        card.addDeck(1);
        flashcardDataAccessInterface.save(card);

        FlipCardOutputBoundary presenter = new FlipCardOutputBoundary() {
            @Override
            public void presentSuccessView(FlipCardOutputData data) {
                assertEquals("Bonjour", data.getTextToShow());
                assertFalse(data.isShowingFrontNow());
            }
        };

        FlipCardInteractor interactor = new FlipCardInteractor(flashcardDataAccessInterface, presenter);
        interactor.execute(flipCardInputData);
    }

    @Test
    void TestDoesNotStartWithSource_FlipToFront() {
        FlipCardInputData flipCardInputData = new FlipCardInputData(1, 0, false, false);
        FlashcardDataAccessInterface flashcardDataAccessInterface = new InMemoryFlashcardDataAccessObject();
        Flashcard card = new Flashcard(1, "Hello", "Bonjour", Language.ENGLISH,
                Language.FRENCH);
        card.addDeck(1);
        flashcardDataAccessInterface.save(card);

        FlipCardOutputBoundary presenter = new FlipCardOutputBoundary() {
            @Override
            public void presentSuccessView(FlipCardOutputData data) {
                assertEquals("Bonjour", data.getTextToShow());
                assertTrue(data.isShowingFrontNow());
            }
        };

        FlipCardInteractor interactor = new FlipCardInteractor(flashcardDataAccessInterface, presenter);
        interactor.execute(flipCardInputData);
    }
}
