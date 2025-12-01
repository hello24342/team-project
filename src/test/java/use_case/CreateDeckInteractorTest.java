package use_case;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryDeckDataAccessObject;
import entity.FlashcardDeck;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.deck.create_deck.CreateDeckInputData;
import use_case.deck.create_deck.CreateDeckInteractor;
import use_case.deck.create_deck.CreateDeckOutputBoundary;
import use_case.deck.create_deck.CreateDeckOutputData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreateDeckInteractorTest {

    private static final String TEST_USERS_CSV = "test_users.csv";

    @BeforeAll
    static void setupTestFile() throws IOException {
        // prepare a userId=123 in the csv file for testing
        try (FileWriter writer = new FileWriter(TEST_USERS_CSV)) {
            writer.write("#userId,username,email,password,totalKnownFlashcards,totalFlashcards,deckIds\n");
            writer.write("123,testuser,test@email.com,password123,0,0,[]\n");
            writer.write("456,user2,user2@email.com,pass456,15,50,[1,2,5]\n");
        }
    }

    @AfterAll
    static void cleanup() {
        new File(TEST_USERS_CSV).delete();
    }

    // ---- Presenter mock ----
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

    // ---- Test 0: user not found ----
    @Test
    void testCreateDeck_userNotFound_fails() throws IOException {
        InMemoryDeckDataAccessObject deckDAO = new InMemoryDeckDataAccessObject();
        UserDataAccessInterface userDAO = new FileUserDataAccessObject(TEST_USERS_CSV);
        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();

        CreateDeckInteractor interactor =
                new CreateDeckInteractor(deckDAO, userDAO, presenter);

        // userId=999 is not in csv，getUsernameFromId will return null
        CreateDeckInputData input = new CreateDeckInputData(999, "Some Deck");

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("User not found.", presenter.failMessage);
    }

    // ----- Test 1: title is null -----
    @Test
    void testCreateDeck_nullTitle_fails() throws IOException {
        InMemoryDeckDataAccessObject dao = new InMemoryDeckDataAccessObject();
        FileUserDataAccessObject userdao = new FileUserDataAccessObject(TEST_USERS_CSV);
        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, userdao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(123, null);

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("Deck title cannot be empty.", presenter.failMessage);
    }

    // ----- Test 2: title only blank space -----
    @Test
    void testCreateDeck_blankTitle_fails() throws IOException {
        InMemoryDeckDataAccessObject dao = new InMemoryDeckDataAccessObject();
        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        FileUserDataAccessObject userdao = new FileUserDataAccessObject(TEST_USERS_CSV);
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, userdao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(123, "   ");

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("Deck title cannot be empty.", presenter.failMessage);
    }

    // ----- Test 3: title already existed -----
    @Test
    void testCreateDeck_duplicateTitle_fails() throws IOException {
        InMemoryDeckDataAccessObject dao = new InMemoryDeckDataAccessObject();
        int userId = 123;
        String title = "My Deck";

        // save a deck of the same name，let existsByTitleForUser return true
        dao.save(new FlashcardDeck(1, title, userId));

        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        FileUserDataAccessObject userdao = new FileUserDataAccessObject(TEST_USERS_CSV);
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, userdao, presenter);

        CreateDeckInputData input = new CreateDeckInputData(userId, title);

        interactor.createDeck(input);

        Assertions.assertTrue(presenter.failCalled);
        Assertions.assertFalse(presenter.successCalled);
        Assertions.assertEquals("You already have a deck with this title.", presenter.failMessage);
    }

    // ----- Test 4: Deck successfully created + sort logic -----
    @Test
    void testCreateDeck_success_createsAndSortsDecks() throws IOException {
        InMemoryDeckDataAccessObject dao = new InMemoryDeckDataAccessObject();
        int userId = 123;

        // add a deck first, let it be alphabetically sorted after "Don't know deck"
        dao.save(new FlashcardDeck(100, "Alpha Deck", userId));

        CreateDeckPresenterMock presenter = new CreateDeckPresenterMock();
        FileUserDataAccessObject userdao = new FileUserDataAccessObject(TEST_USERS_CSV);
        CreateDeckInteractor interactor = new CreateDeckInteractor(dao, userdao, presenter);

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
