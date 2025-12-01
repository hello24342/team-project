package use_case.progressTracker;

import use_case.progressTracker.progressTrackerOutputData;

/**
 * The output boundary for the progress tracker usecase.
 */
public interface progressTrackerOutputBoundary {
    void presentSuccess(progressTrackerOutputData outputData);
}
