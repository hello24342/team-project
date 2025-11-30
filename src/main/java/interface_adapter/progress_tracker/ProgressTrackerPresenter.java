package interface_adapter.progress_tracker;
import interface_adapter.ViewManagerModel;
import usecase.progressTracker.progressTrackerOutputBoundary;
import usecase.progressTracker.progressTrackerOutputData;

public class ProgressTrackerPresenter implements progressTrackerOutputBoundary{
    private final ProgressTrackerViewModel progressTrackerViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProgressTrackerPresenter(ProgressTrackerViewModel progressTrackerViewModel,
                                    ViewManagerModel viewManagerModel){
        this.progressTrackerViewModel = progressTrackerViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccess(progressTrackerOutputData outputData) {
        // work on this.
    }
    @Override
    public void prepareFailure(String message) {
        // work on this.
    }
}
