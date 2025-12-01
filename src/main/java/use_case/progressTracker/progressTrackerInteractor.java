package use_case.progressTracker;

/**
 * The progress tracker Interactor.
 */
public class progressTrackerInteractor implements progressTrackerInputBoundary {
    private final progressTrackerOutputBoundary presenter;

    public progressTrackerInteractor(progressTrackerOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(progressTrackerInputData progressTrackerInputData) {
        final int wordsStudied  = progressTrackerInputData.getWordsStudied();
        final int wordsMastered = progressTrackerInputData.getWordsMastered();

        final progressTrackerOutputData outputData = new progressTrackerOutputData(wordsStudied, wordsMastered);
        presenter.presentSuccess(outputData);
    }
}
