package use_case.progressTracker;
import entity.User;
import entity.LearningGoal;

public interface progressTrackerDataAccessInterface {
    int getTotalKnownFlashcards();
    int getTotalFlashcards();
    int getDailyTarget();

}
