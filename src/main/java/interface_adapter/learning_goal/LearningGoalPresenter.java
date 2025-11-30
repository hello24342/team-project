package interface_adapter.learning_goal;

import interface_adapter.ViewManagerModel;
import usecase.SetLearningGoal.SetLearningGoalOutputBoundary;
import usecase.SetLearningGoal.SetLearningGoalOutputData;

public class LearningGoalPresenter implements SetLearningGoalOutputBoundary {
    private final LearningGoalViewModel learningGoalViewModel;
    private final ViewManagerModel viewManagerModel;


    public LearningGoalPresenter(LearningGoalViewModel viewModel, ViewManagerModel viewManager) {
        this.learningGoalViewModel = viewModel;
        this.viewManagerModel = viewManager;
    }

    @Override
    public void prepareSuccess(SetLearningGoalOutputData outputData) {
        // work on this
    }
    @Override
    public void prepareFailure(String message) {
        // work on this
    }
}
