package usecase.study_deck.previous_card;

import entity.Flashcard;
import usecase.FlashcardDataAccessInterface;

import java.util.List;

public class PreviousCardInteractor implements PreviousCardInputBoundary {

    private final FlashcardDataAccessInterface flashcardDAO;
    private final PreviousCardOutputBoundary presenter;

    public PreviousCardInteractor(FlashcardDataAccessInterface flashcardDAO, PreviousCardOutputBoundary presenter) {
        this.flashcardDAO = flashcardDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(PreviousCardInputData inputData) {
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();

        List<Flashcard> cards = flashcardDAO.findByDeck(deckId);

        int prevIndex = inputData.getCardIndex() - 1;

        if (prevIndex < 0) {
            Flashcard card = cards.get(prevIndex);
            presenter.presentSuccessView(new PreviousCardOutputData(deckId, prevIndex, card.getSourceWord(),
                    card.getTargetWord()));
        }
        else {
            presenter.presentFailureView("No more cards in deck.");
        }
    }
}
