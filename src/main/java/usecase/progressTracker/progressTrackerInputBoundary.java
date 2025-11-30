package usecase.progressTracker;

import usecase.progressTracker.progressTrackerInputData;

/**
 * Input Boundary for actions which are related to tracking progress.
 */
public interface progressTrackerInputBoundary {
    void execute(progressTrackerInputData progressTrackerInputData);
}
