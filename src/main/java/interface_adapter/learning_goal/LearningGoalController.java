package interface_adapter.learning_goal;

import use_case.SetLearningGoal.SetLearningGoalInputBoundary;
import use_case.SetLearningGoal.SetLearningGoalInputData;

public class LearningGoalController {
    private final SetLearningGoalInputBoundary setLearningGoalInteractor;

    public LearningGoalController(SetLearningGoalInputBoundary setLearningGoalInteractor) {
        this.setLearningGoalInteractor = setLearningGoalInteractor;
    }

    public void execute(int userId, int dailyTarget ) {
        final SetLearningGoalInputData setLearningGoalInputData = new SetLearningGoalInputData(userId, dailyTarget);
        setLearningGoalInteractor.execute(setLearningGoalInputData);
    }

}
