package use_case.study_deck.mark_known;

import data_access.FileUserDataAccessObject;
import entity.User;
import use_case.flashcard.FlashcardDataAccessInterface;

public class MarkKnownInteractor implements MarkKnownInputBoundary {
    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final FileUserDataAccessObject userDataAccessObject;
    private final MarkKnownOutputBoundary markKnownPresenter;

    public MarkKnownInteractor(FlashcardDataAccessInterface flashcardDataAccess,
                               FileUserDataAccessObject userDataAccessObject,
                               MarkKnownOutputBoundary markKnownPresenter) {
        this.flashcardDataAccess = flashcardDataAccess;
        this.userDataAccessObject = userDataAccessObject;
        this.markKnownPresenter = markKnownPresenter;
    }

    @Override
    public void execute(MarkKnownInputData inputData) {
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();
        int dontKnowDeckId = inputData.getDontKnowDeckId();
        String username = userDataAccessObject.getCurrentUsername();
        int actualUserId = userDataAccessObject.getCurrentUserId();

        flashcardDataAccess.markCardAsKnown(actualUserId, deckId, cardIndex, dontKnowDeckId);
        userDataAccessObject.incrementKnownFlashcards(username);

        User user = userDataAccessObject.getUser(username);

        int totalKnown = user.getTotalKnownFlashcards();
        int deckKnownCount = flashcardDataAccess.getKnownCardsCount(actualUserId, deckId);
        int deckTotalCards = flashcardDataAccess.getDeckSize(actualUserId, deckId);

        MarkKnownOutputData outputData = new MarkKnownOutputData(
                deckId, cardIndex, deckKnownCount, deckTotalCards,
                totalKnown
        );

        markKnownPresenter.presentSuccessView(outputData);
    }
}