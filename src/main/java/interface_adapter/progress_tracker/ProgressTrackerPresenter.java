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
        final ProgressTrackerState loggedInState = progressTrackerViewModel.getState();
        loggedInState.setWordsStudied(outputData.getWordsStudied());
        loggedInState.setWordsMastered(outputData.getWordsMastered());
        this.progressTrackerViewModel.firePropertyChange();

        progressTrackerViewModel.setState(new ProgressTrackerState());
    }
    @Override
    public void prepareFailure(String message) {
        // work on this.
    }
}
