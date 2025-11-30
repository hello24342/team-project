package usecase.study_deck.mark_known;

import usecase.FlashcardDataAccessInterface;

public class MarkKnownInteractor implements MarkKnownInputBoundary {
    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final MarkKnownOutputBoundary markKnownPresenter;

    public MarkKnownInteractor(FlashcardDataAccessInterface flashcardDataAccess,
                               MarkKnownOutputBoundary markKnownPresenter) {
        this.flashcardDataAccess = flashcardDataAccess;
        this.markKnownPresenter = markKnownPresenter;
    }

    @Override
    public void execute(MarkKnownInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();

        // Mark the card as known
        flashcardDataAccess.markCardAsKnown(userId, deckId, cardIndex);

        MarkKnownOutputData outputData = new MarkKnownOutputData(deckId, cardIndex);
        markKnownPresenter.presentSuccessView(outputData);
    }
}