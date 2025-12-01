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
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();
        int dontKnowDeckId = inputData.getDontKnowDeckId();

        System.out.println("\n=== MARK KNOWN INTERACTOR ===");
        System.out.println("User ID from input: " + userId);
        System.out.println("Deck ID: " + deckId);
        System.out.println("Card Index: " + cardIndex);
        System.out.println("Don't Know Deck ID: " + dontKnowDeckId);

        // Get username from userDataAccessObject instead of inputData
        String username = userDataAccessObject.getCurrentUsername();

        System.out.println("Username from userDAO: '" + username + "'");

        // Validate username
        if (username == null || username.isEmpty()) {
            System.err.println("ERROR: No user is currently logged in!");
            markKnownPresenter.presentFailureView("Cannot mark card: user not logged in. Please login first.");
            return;
        }

        // Also get the correct userId from the DAO
        Integer actualUserId = userDataAccessObject.getCurrentUserId();
        if (actualUserId == null || actualUserId <= 0) {
            System.err.println("ERROR: Invalid user ID from DAO: " + actualUserId);
            markKnownPresenter.presentFailureView("Invalid user session. Please login again.");
            return;
        }

        System.out.println("Actual User ID from DAO: " + actualUserId);

        // Validate user exists
        if (!userDataAccessObject.usernameExists(username)) {
            System.err.println("ERROR: Username '" + username + "' not found in database!");
            markKnownPresenter.presentFailureView("User '" + username + "' not found.");
            return;
        }

        // Use the actual userId from DAO instead of input
        flashcardDataAccess.markCardAsKnown(actualUserId, deckId, cardIndex, dontKnowDeckId);
        userDataAccessObject.incrementKnownFlashcards(username);

        // Get user - should not be null now
        User user = userDataAccessObject.getUser(username);

        if (user == null) {
            System.err.println("CRITICAL ERROR: getUser('" + username + "') returned null!");
            markKnownPresenter.presentFailureView("Failed to retrieve user data.");
            return;
        }

        int totalKnown = user.getTotalKnownFlashcards();
        int deckKnownCount = flashcardDataAccess.getKnownCardsCount(actualUserId, deckId);
        int deckTotalCards = flashcardDataAccess.getDeckSize(actualUserId, deckId);

        System.out.println("Total known flashcards: " + totalKnown);
        System.out.println("Deck known count: " + deckKnownCount + "/" + deckTotalCards);

        MarkKnownOutputData outputData = new MarkKnownOutputData(
                deckId, cardIndex, deckKnownCount, deckTotalCards,
                totalKnown
        );

        markKnownPresenter.presentSuccessView(outputData);
    }
}