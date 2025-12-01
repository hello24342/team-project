package use_case.SetLearningGoal;

/**
 * The setLearningGoal Interactor.
 */
public class SetLearningGoalInteractor implements SetLearningGoalInputBoundary {
    private final SetLearningGoalOutputBoundary presenter;

    public SetLearningGoalInteractor(SetLearningGoalOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SetLearningGoalInputData inputdata){
        int dailyTarget = inputdata.getDailyTarget();
        presenter.presentSuccess(new SetLearningGoalOutputData(dailyTarget));
        }


    }
