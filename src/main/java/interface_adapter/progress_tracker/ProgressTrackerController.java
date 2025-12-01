package interface_adapter.progress_tracker;
 import usecase.progressTracker.progressTrackerInputBoundary;
 import usecase.progressTracker.progressTrackerInputData;

public class ProgressTrackerController {
    private final progressTrackerInputBoundary progressTrackerInteractor;

    public ProgressTrackerController(progressTrackerInputBoundary progressTrackerInteractor) {
        this.progressTrackerInteractor = progressTrackerInteractor;
    }

    public void execute(int id, int userId, int wordsStudied, int wordsMastered) {
        final progressTrackerInputData progressTrackerInputData = new progressTrackerInputData(id,
                userId, wordsStudied, wordsMastered);
        progressTrackerInteractor.execute(progressTrackerInputData);
    }
}
