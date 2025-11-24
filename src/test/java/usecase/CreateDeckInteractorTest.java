package usecase;
import data_access.DeckDataAccess;
import entity.FlashcardDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.deck.create_deck.CreateDeckInputData;
import usecase.deck.create_deck.CreateDeckInteractor;
import usecase.deck.create_deck.CreateDeckOutputBoundary;
import usecase.deck.create_deck.CreateDeckOutputData;

import java.util.List;

public class CreateDeckInteractorTest {

    // ----- Presenter Mock -----
    private static class CreateDeckPresenterMock implements CreateDeckOutputBoundary {

        boolean failCalled = false;
        boolean successCalled = false;
        String failMessage = null;
        CreateDeckOutputData successData = null;

        @Override
        public void prepareFail(String message) {
            failCalled = true;
            failMessage = message;
        }

        @Override
        public void prepareSuccess(CreateDeckOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }
    }

    // ----- Test 1: title is null -----
    @Test
    void testCreateDeck_nullTitle_fails() {
        DeckDataAccess dao = new DeckDataAccess();
        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(123, null);

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("Deck title cannot be empty.", presenter.failMessage);
    }

    // ----- Test 2: title only blank space -----
    @Test
    void testCreateDeck_blankTitle_fails() {
        DeckDataAccess dao = new DeckDataAccess();
        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(123, "   ");

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("Deck title cannot be empty.", presenter.failMessage);
    }

    // ----- Test 3: title already existed -----
    @Test
    void testCreateDeck_duplicateTitle_fails() {
        DeckDataAccess dao = new DeckDataAccess();
        int userId = 123;
        String title = "My Deck";

        // save a deck of the same name，let existsByTitleForUser return true
        dao.save(new FlashcardDeck(1, title, userId));

        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(userId, title);

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("You already have a deck with this title.", presenter.failMessage);
    }

    // ----- Test 4: Deck successfully created + sort logic -----
    @Test
    void testCreateDeck_success_createsAndSortsDecks() {
        DeckDataAccess dao = new DeckDataAccess();
        int userId = 123;

        // add a deck first, let it be alphabetically sorted after "Don't know deck"
        dao.save(new FlashcardDeck(100, "Alpha Deck", userId));

        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, presenter);

        // Create a new deck with leading/trailing spaces to test trimming
        CreateDeckInputData input = new CreateDeckInputData(userId, "  New Deck  ");

        interactor.createDeck(input);

        Assertions.assertFalse(presenter.failCalled);
        Assertions.assertTrue(presenter.successCalled);
        Assertions.assertNotNull(presenter.successData);

        List<CreateDeckOutputData.DeckSummary> list =
                presenter.successData.getDeckSummaries();

        // suppose to have 3 decks now:
        // 1. Don't know deck
        // 2. Alpha Deck
        // 3. New Deck
        Assertions.assertEquals(3, list.size());

        // check the order：Don't know deck must be in the first position
        Assertions.assertEquals("Don't know deck", list.get(0).title);

        // check the titles of the other two decks to see if "Alpha Deck" comes before "New Deck"
        Assertions.assertEquals("Alpha Deck", list.get(1).title);
        Assertions.assertEquals("New Deck", list.get(2).title);
    }
}
