package use_case.study_deck.next_card;

import entity.Flashcard;
import use_case.flashcard.FlashcardDataAccessInterface;

import java.util.List;

public class NextCardInteractor implements NextCardInputBoundary {
    private final FlashcardDataAccessInterface flashcardDAO;
    private final NextCardOutputBoundary presenter;

    public NextCardInteractor(FlashcardDataAccessInterface flashcardDAO, NextCardOutputBoundary presenter) {
        this.flashcardDAO = flashcardDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(NextCardInputData inputData) {
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();

        List<Flashcard> cards = flashcardDAO.findByDeck(deckId);

        int nextIndex = cardIndex + 1;

        if (nextIndex < cards.size()) {
            Flashcard card = cards.get(nextIndex);
            presenter.presentSuccessView(new NextCardOutputData(deckId, nextIndex, card.getSourceWord(),
                    card.getTargetWord()));
        }
        else {
            presenter.presentFailureView("No more cards in deck.");
        }
    }
}
