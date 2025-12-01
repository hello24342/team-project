package use_case.study_deck.mark_known;

import data_access.FileUserDataAccessObject;
import entity.User;
import use_case.flashcard.FlashcardDataAccessInterface;

public class MarkKnownInteractor implements MarkKnownInputBoundary {
    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final FileUserDataAccessObject userDataAccessObject;
    private final MarkKnownOutputBoundary markKnownPresenter;

    public MarkKnownInteractor(FlashcardDataAccessInterface flashcardDataAccess, FileUserDataAccessObject userDataAccessObject,
                               MarkKnownOutputBoundary markKnownPresenter) {
        this.flashcardDataAccess = flashcardDataAccess;
        this.userDataAccessObject = userDataAccessObject;
        this.markKnownPresenter = markKnownPresenter;
    }

    @Override
    public void execute(MarkKnownInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();
        String username = inputData.getUsername();

        // Mark the card as known
        flashcardDataAccess.markCardAsKnown(userId, deckId, cardIndex);
        userDataAccessObject.incrementKnownFlashcards(username);

        User user = userDataAccessObject.getUser(username);
        int totalKnown = user.getTotalKnownFlashcards();
        int deckKnownCount = flashcardDataAccess.getKnownCardsCount(userId, deckId);
        int deckTotalCards = flashcardDataAccess.getDeckSize(userId, deckId);

        MarkKnownOutputData outputData = new MarkKnownOutputData(
                deckId, cardIndex, deckKnownCount, deckTotalCards,
                totalKnown
        );

        markKnownPresenter.presentSuccessView(outputData);
    }
}