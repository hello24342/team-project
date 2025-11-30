package interface_adapter.learning_goal;

import interface_adapter.login.LoginState;

public class LearningGoalViewModel {
    public LearningGoalViewModel() {
        super("set learning goal");
        setState(new LearningGoalState());
    }
}
