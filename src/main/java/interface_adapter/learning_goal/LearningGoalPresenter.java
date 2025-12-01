package interface_adapter.learning_goal;

import interface_adapter.ViewModel;
import use_case.SetLearningGoal.SetLearningGoalOutputBoundary;
import use_case.SetLearningGoal.SetLearningGoalOutputData;
import view.SetLearningGoalView;

public class LearningGoalPresenter implements SetLearningGoalOutputBoundary {
    private final SetLearningGoalView view;
    private final ViewModel viewModel;

    public LearningGoalPresenter(SetLearningGoalView view, ViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccess(SetLearningGoalOutputData outputData) {

    }

    @Override
    public void prepareFailure(String errorMessage) {

    }
}
