package use_case.deck.list_deck;

import entity.FlashcardDeck;
import use_case.deck.DeckDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class ListDecksInteractor implements  ListDecksInputBoundary {
    private final DeckDataAccessInterface deckDAO;
    private final ListDecksOutputBoundary presenter;

    public ListDecksInteractor(DeckDataAccessInterface deckDAO,
                               ListDecksOutputBoundary presenter) {
        this.deckDAO = deckDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ListDecksInputData inputData) {
        int userId = inputData.getUserId();

        // Ensure "Don't know deck" exists
        int dkId = deckDAO.findOrCreateDontKnowDeckId(userId);

        List<FlashcardDeck> decks = deckDAO.findByUser(userId);
        // Sort: "Don't know deck" first, others alphabetically
        decks.sort((a, b) -> {
            int aKey = (a.getId() == dkId) ? 0 : 1;
            int bKey = (b.getId() == dkId) ? 0 : 1;
            if (aKey != bKey) return Integer.compare(aKey, bKey);
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        });
        // Convert entities to DTOs
        List<ListDecksOutputData.DeckSummary> summaries = new ArrayList<>();
        for (FlashcardDeck d : decks) {
            summaries.add(new ListDecksOutputData.DeckSummary(d.getId(), d.getTitle()));
        }
        // Send to presenter
        presenter.present(new ListDecksOutputData(summaries));
    }
}
