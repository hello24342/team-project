package use_case.progressTracker;
import entity.ProgressTracker;

public interface progressTrackerDataAccessInterface {
    int getKnownCardsCount(int userID, int deckID);


}
