package use_case.progressTracker;

/**
 * The progress tracker Interactor.
 */
public class progressTrackerInteractor implements progressTrackerInputBoundary {
    private final progressTrackerDataAccessInterface progressTrackerDataAccess;
    private final progressTrackerOutputBoundary presenter;

    public progressTrackerInteractor(progressTrackerDataAccessInterface progressTrackerDataAccess,
                                     progressTrackerOutputBoundary presenter) {
        this.progressTrackerDataAccess = progressTrackerDataAccess;
        this.presenter = presenter;
    }

    public void execute(progressTrackerInputData progressTrackerInputData) {
        final int wordsStudied  = progressTrackerDataAccess.getTotalFlashcards();
        final int wordsMastered = progressTrackerDataAccess.getTotalKnownFlashcards();
        final int dailyTarget = progressTrackerDataAccess.getDailyTarget();

        final progressTrackerOutputData outputData = new progressTrackerOutputData(wordsStudied,
                wordsMastered,
                dailyTarget);
        presenter.presentSuccess(outputData);
    }
}
