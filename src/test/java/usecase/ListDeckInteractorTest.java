package usecase;

import data_access.DeckDataAccess;
import entity.FlashcardDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.deck.list_deck.ListDecksInputData;
import usecase.deck.list_deck.ListDecksInteractor;
import usecase.deck.list_deck.ListDecksOutputBoundary;
import usecase.deck.list_deck.ListDecksOutputData;

import java.util.List;

public class ListDeckInteractorTest {

    // ----- Presenter Mock -----
    private static class ListDecksPresenterMock implements ListDecksOutputBoundary {

        boolean presentCalled = false;
        ListDecksOutputData capturedData = null;

        @Override
        public void present(ListDecksOutputData outputData) {
            presentCalled = true;
            capturedData = outputData;
        }
    }

    // ===== Test 1: the user does not have any deck =====
    @Test
    void testExecute_createsDontKnowDeckWhenNoDecks() {
        DeckDataAccess dao = new DeckDataAccess();
        ListDecksPresenterMock presenter = new ListDecksPresenterMock();
        ListDecksInteractor interactor =
                new ListDecksInteractor(dao, presenter);

        int userId = 123;
        ListDecksInputData input = new ListDecksInputData(userId);

        interactor.execute(input);

        Assertions.assertTrue(presenter.presentCalled);
        Assertions.assertNotNull(presenter.capturedData);

        List<ListDecksOutputData.DeckSummary> summaries =
                presenter.capturedData.getDecks();

        // should only have one don't know deck
        Assertions.assertEquals(1, summaries.size());
        ListDecksOutputData.DeckSummary only = summaries.get(0);

        Assertions.assertEquals("Don't know deck", only.getTitle());
    }

    // ===== Test 2: the user already have serveral decks =====
    @Test
    void testExecute_sortsDontKnowFirstAndOthersAlphabetically() {
        DeckDataAccess dao = new DeckDataAccess();
        int userId = 456;

        // Pre-create 2 decks for the user
        dao.save(new FlashcardDeck(100, "beta deck", userId));
        dao.save(new FlashcardDeck(101, "Alpha Deck", userId));

        ListDecksPresenterMock presenter = new ListDecksPresenterMock();
        ListDecksInteractor interactor =
                new ListDecksInteractor(dao, presenter);

        ListDecksInputData input = new ListDecksInputData(userId);

        interactor.execute(input);

        Assertions.assertTrue(presenter.presentCalled);
        Assertions.assertNotNull(presenter.capturedData);

        List<ListDecksOutputData.DeckSummary> summaries =
                presenter.capturedData.getDecks();

        // should be 3 deck
        Assertions.assertEquals(3, summaries.size());

        ListDecksOutputData.DeckSummary first = summaries.get(0);
        ListDecksOutputData.DeckSummary second = summaries.get(1);
        ListDecksOutputData.DeckSummary third = summaries.get(2);

        // Don't know deck should be the first (examine the title)
        Assertions.assertEquals("Don't know deck", first.getTitle());

        // the other two should be sorted alphabetically
        Assertions.assertEquals("Alpha Deck", second.getTitle());
        Assertions.assertEquals("beta deck", third.getTitle());
    }
}

