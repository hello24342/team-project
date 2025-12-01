package use_case.deck.create_deck;

import data_access.FileUserDataAccessObject;
import entity.FlashcardDeck;
import use_case.UserDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class CreateDeckInteractor implements CreateDeckInputBoundary {
    private final DeckDataAccessInterface deckDAO;
    private final UserDataAccessInterface userDAO;
    private final CreateDeckOutputBoundary presenter;

    public CreateDeckInteractor(DeckDataAccessInterface deckDAO, UserDataAccessInterface userDAO,
                                CreateDeckOutputBoundary presenter) {
        this.deckDAO = deckDAO;
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void createDeck(CreateDeckInputData input) {
        String title = input.getTitle();
        int userId = input.getUserId();

        // Get username from user ID
        String username = userDAO.getUsernameFromId(userId);
        if (username == null) {
            presenter.prepareFail("User not found.");
            return;
        }

        // Validate title
        if (title == null || title.trim().isEmpty()) {
            presenter.prepareFail("Deck title cannot be empty.");
            return;
        }
        if (deckDAO.existsByTitleForUser(userId, title)) {
            presenter.prepareFail("You already have a deck with this title.");
            return;
        }

        // Make sure the "Don't know" deck exists
        int dkId = deckDAO.findOrCreateDontKnowDeckId(userId);

        // Create and save the new deck
        int newId = deckDAO.nextDeckId();
        FlashcardDeck deck = new FlashcardDeck(newId, title.trim(), userId);
        deckDAO.save(deck);
        userDAO.addDeckToUser(username, deck.getId());

        // Retrieve and sort all decks for the user
        List<FlashcardDeck> decks = deckDAO.findByUser(userId);
        decks.sort((a, b) -> {
            int aKey = (a.getId() == dkId) ? 0 : 1;
            int bKey = (b.getId() == dkId) ? 0 : 1;
            if (aKey != bKey) return Integer.compare(aKey, bKey);
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        });

        // Convert the entity FlashcardDeck into output data format the DeckSummary
        List<CreateDeckOutputData.DeckSummary> list = new ArrayList<>();
        for (FlashcardDeck d : decks) {
            list.add(new CreateDeckOutputData.DeckSummary(d.getId(), d.getTitle()));
        }
        presenter.prepareSuccess(new CreateDeckOutputData(list));
    }
}