package usecase.progressTracker;

import usecase.deck.create_deck.CreateDeckOutputData;

/**
 * The output boundary for the progress tracker usecase.
 */
public interface progressTrackerOutputBoundary {
    void prepareSuccess(CreateDeckOutputData outputData);
    void prepareFailure(String errorMessage);
}
