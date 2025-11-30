package usecase.SetLearningGoal;

import data_access.LearningGoalDataAccess;

/**
 * The setLearningGoal Interactor.
 */
public class SetLearningGoalInteractor implements SetLearningGoalInputBoundary {
    private final LearningGoalDataAccess setLearningGoalDAO;
    private final SetLearningGoalOutputBoundary presenter;

    public SetLearningGoalInteractor(LearningGoalDataAccess learningGoalDataAccess,
                                     SetLearningGoalOutputBoundary presenter) {
        this.setLearningGoalDAO = learningGoalDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SetLearningGoalInputData inputdata){
        int dailyTarget = inputdata.getDailyTarget();
        presenter.prepareSuccess(new SetLearningGoalOutputData(dailyTarget));
        //write out what the learning goal is at the bottom of the progress screen
        }


    }
}
