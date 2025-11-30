package usecase.progressTracker;

import data_access.ProgressTrackerDataAccess;
import interface_adapter.progress_tracker.ProgressTrackerPresenter;

/**
 * The progress tracker Interactor.
 */
public class progressTrackerInteractor implements progressTrackerInputBoundary {
    private final ProgressTrackerDataAccess progressTrackerDataAccess;
    private final progressTrackerOutputBoundary presenter;

    public progressTrackerInteractor(ProgressTrackerDataAccess progressTrackerDataAccess,
                                     progressTrackerOutputBoundary presenter) {
        this.progressTrackerDataAccess = progressTrackerDataAccess;
        this.presenter = presenter;
    }

    public void execute(progressTrackerInputData progressTrackerInputData) {
        final int wordsStudied  = progressTrackerInputData.getWordsStudied();
        final int wordsMastered = progressTrackerInputData.getWordsMastered();

        final progressTrackerOutputData outputData = new progressTrackerOutputData(wordsStudied, wordsMastered);
        presenter.prepareSuccess(outputData);
    }
}
