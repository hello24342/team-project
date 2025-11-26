package usecase.SetLearningGoal;

import data_access.LearningGoalDataAccess;

/**
 * The setLearningGoal Interactor.
 */
public class SetLearningGoalInteractor implements SetLearningGoalInputBoundary {
    private final LearningGoalDataAccess setLearningGoalDAO;
    private final SetLearningGoalOutputBoundary presenter;

    public SetLearningGoalInteractor(LearningGoalDataAccess deckDAO,
                                     SetLearningGoalOutputBoundary presenter) {
        this.setLearningGoalDAO = deckDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(SetLearningGoalInputData inputdata){

    }
}
