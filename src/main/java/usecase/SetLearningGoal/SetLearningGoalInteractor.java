package usecase.SetLearningGoal;

import data_access.LearningGoalDataAccess;

/**
 * The setLearningGoal Interactor.
 */
public class SetLearningGoalInteractor implements SetLearningGoalInputBoundary {
    private final LearningGoalDataAccess setLearningGoalDAO;
    private final SetLearningGoalOutputBoundary presenter;

    // do i have to have this?
    public SetLearningGoalInteractor(LearningGoalDataAccess DAO,
                                     SetLearningGoalOutputBoundary presenter) {
        this.setLearningGoalDAO = DAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(SetLearningGoalInputData inputdata){
        int userId = inputdata.getUserId();
        int dailyTarget = inputdata.getDailyTarget();
        if (dailyTarget == -1){
            return;
            //have a button at the bottom of the progress screen that says set goal.
        }
        else {
            presenter.prepareSuccess(new SetLearningGoalOutputData(dailyTarget));
            //write out what the learning goal is at the bottom of the progress screen
        }


    }
}
