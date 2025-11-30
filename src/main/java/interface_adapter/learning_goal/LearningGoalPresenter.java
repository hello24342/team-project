package interface_adapter.learning_goal;

import interface_adapter.ViewModel;
import usecase.SetLearningGoal.SetLearningGoalOutputBoundary;
import view.SetLearningGoalView;

public class LearningGoalPresenter implements SetLearningGoalOutputBoundary {
    private final SetLearningGoalView view;
    private final ViewModel viewModel;

    public LearningGoalPresenter(SetLearningGoalView view, ViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    public void
}
