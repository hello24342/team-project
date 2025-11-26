package usecase.deck.open_deck;

import data_access.DeckDataAccess;
import data_access.FlashcardDataAccessObject;
import entity.FlashcardDeck;
import entity.Flashcard;
import java.util.ArrayList;
import java.util.List;

public class OpenDeckInteractor implements OpenDeckInputBoundary {

    private final DeckDataAccess deckDAO;
    private final FlashcardDataAccessObject cardDAO;
    private final OpenDeckOutputBoundary presenter;

    public OpenDeckInteractor(DeckDataAccess deckDAO,
                              FlashcardDataAccessObject cardDAO,
                              OpenDeckOutputBoundary presenter) {
        this.deckDAO = deckDAO;
        this.cardDAO = cardDAO;
        this.presenter = presenter;
    }

    @Override
    public void open(OpenDeckInputData input) {
        // Retrieve the deck
        int deckId = input.getDeckId();
        FlashcardDeck deck = deckDAO.findById(deckId);
        if (deck == null) {
            presenter.presentNotFound("Deck not found: " + deckId);
            return;
        }

       // Retrieve the cards in the deck
        List<Flashcard> cards = cardDAO.findByDeck(deckId);
        List<OpenDeckOutputData.CardSummary> summaries = new ArrayList<>();
        for (Flashcard c : cards) {
            summaries.add(new OpenDeckOutputData.CardSummary(
                    c.getId(), c.getSourceWord(), c.getTargetWord()
            ));
        }
        presenter.present(new OpenDeckOutputData(deck.getId(), deck.getTitle(), summaries));
    }
}
