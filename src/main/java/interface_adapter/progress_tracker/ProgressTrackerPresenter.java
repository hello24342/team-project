package interface_adapter.progress_tracker;
import interface_adapter.ViewManagerModel;
import interface_adapter.learning_goal.LearningGoalViewModel;
import use_case.progressTracker.progressTrackerOutputBoundary;
import use_case.progressTracker.progressTrackerOutputData;

public class ProgressTrackerPresenter implements progressTrackerOutputBoundary{
    private final ProgressTrackerViewModel progressTrackerViewModel;
    private final LearningGoalViewModel learningGoalViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProgressTrackerPresenter(ProgressTrackerViewModel progressTrackerViewModel,
                                    LearningGoalViewModel learningGoalViewModel,
                                    ViewManagerModel viewManagerModel){
        this.progressTrackerViewModel = progressTrackerViewModel;
        this.learningGoalViewModel = learningGoalViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentSuccess(progressTrackerOutputData outputData) {
        final ProgressTrackerState progressTrackerState = progressTrackerViewModel.getState();
        progressTrackerState.setWordsStudied(outputData.getWordsStudied());
        progressTrackerState.setWordsMastered(outputData.getWordsMastered());
        this.progressTrackerViewModel.firePropertyChange();

        progressTrackerViewModel.setState(new ProgressTrackerState());

        viewManagerModel.setState(learningGoalViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
