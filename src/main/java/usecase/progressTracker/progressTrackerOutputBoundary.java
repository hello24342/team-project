package usecase.progressTracker;

/**
 * The output boundary for the progress tracker usecase.
 */
public interface progressTrackerOutputBoundary {
    void prepareSuccess(progressTrackerOutputData outputData);
    void prepareFailure(String errorMessage);
}
