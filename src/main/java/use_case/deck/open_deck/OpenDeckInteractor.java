package use_case.deck.open_deck;

import entity.FlashcardDeck;
import entity.Flashcard;
import use_case.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class OpenDeckInteractor implements OpenDeckInputBoundary {

    private final DeckDataAccessInterface deckDAO;
    private final FlashcardDataAccessInterface cardDAO;
    private final OpenDeckOutputBoundary presenter;

    public OpenDeckInteractor(DeckDataAccessInterface deckDAO,
                              FlashcardDataAccessInterface cardDAO,
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
