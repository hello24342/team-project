package usecase.study_deck.mark_unknown;

import entity.Flashcard;
import usecase.FlashcardDataAccessInterface;
import usecase.deck.DeckDataAccessInterface;
import java.util.List;

public class MarkUnknownInteractor implements MarkUnknownInputBoundary {
    private final DeckDataAccessInterface deckDataAccess;
    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final MarkUnknownOutputBoundary markUnknownPresenter;

    public MarkUnknownInteractor(DeckDataAccessInterface deckDataAccess,
                                 FlashcardDataAccessInterface flashcardDataAccess,
                                 MarkUnknownOutputBoundary markUnknownPresenter) {
        this.deckDataAccess = deckDataAccess;
        this.flashcardDataAccess = flashcardDataAccess;
        this.markUnknownPresenter = markUnknownPresenter;
    }

    @Override
    public void execute(MarkUnknownInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardId();

        int dontKnowDeckId = deckDataAccess.findOrCreateDontKnowDeckId(userId);

        // Move the flashcard to the "Don't Know" deck
        flashcardDataAccess.markCardAsUnknown(cardIndex, deckId, dontKnowDeckId);
        MarkUnknownOutputData outputData = new MarkUnknownOutputData(dontKnowDeckId, cardIndex);
        markUnknownPresenter.presentSuccessView(outputData);
    }
}
