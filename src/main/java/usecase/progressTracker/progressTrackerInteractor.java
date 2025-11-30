package usecase.progressTracker;

import data_access.ProgressTrackerDataAccess;

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

    }
}
