package interface_adapter.learning_goal;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class LearningGoalViewModel extends ViewModel<LearningGoalState> {
    public LearningGoalViewModel() {
        super("set learning goal");
        setState(new LearningGoalState());
    }
}
