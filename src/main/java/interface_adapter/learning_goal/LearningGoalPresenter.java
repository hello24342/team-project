package interface_adapter.learning_goal;

import interface_adapter.ViewManagerModel;
import interface_adapter.progress_tracker.ProgressTrackerState;
import interface_adapter.progress_tracker.ProgressTrackerViewModel;
import use_case.SetLearningGoal.SetLearningGoalOutputBoundary;
import use_case.SetLearningGoal.SetLearningGoalOutputData;

public class LearningGoalPresenter implements SetLearningGoalOutputBoundary {
    private final LearningGoalViewModel learningGoalViewModel;
    private final ProgressTrackerViewModel progressTrackerViewModel;
    private final ViewManagerModel viewManagerModel;


    public LearningGoalPresenter(LearningGoalViewModel learningGoalViewModel,
                                 ProgressTrackerViewModel progressTrackerViewModel,
                                 ViewManagerModel viewManager) {
        this.learningGoalViewModel = learningGoalViewModel;
        this.progressTrackerViewModel = progressTrackerViewModel;
        this.viewManagerModel = viewManager;
    }

    @Override
    public void presentSuccess(SetLearningGoalOutputData outputData) {
        final LearningGoalState learningGoalState = learningGoalViewModel.getState();
        learningGoalState.setLearningGoal(outputData.getDailyTarget());
        this.learningGoalViewModel.firePropertyChange();

        learningGoalViewModel.setState(new LearningGoalState());

        // switch to progress bar view
        viewManagerModel.setState(progressTrackerViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
