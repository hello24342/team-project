package use_case.progressTracker;

import use_case.deck.create_deck.CreateDeckOutputData;

/**
 * The output boundary for the progress tracker usecase.
 */
public interface progressTrackerOutputBoundary {
    void prepareSuccess(CreateDeckOutputData outputData);
    void prepareFailure(String errorMessage);
}
