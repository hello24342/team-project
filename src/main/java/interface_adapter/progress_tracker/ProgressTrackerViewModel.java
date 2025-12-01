package interface_adapter.progress_tracker;

import entity.ProgressTracker;
import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class ProgressTrackerViewModel extends ViewModel<ProgressTrackerState> {
    public ProgressTrackerViewModel() {
        super("log in");
        setState(new ProgressTrackerState());
    }
}
