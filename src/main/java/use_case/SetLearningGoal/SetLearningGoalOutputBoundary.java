package use_case.SetLearningGoal;

/**
 * The output boundary for the setLearningGoal usecase.
 */
public interface SetLearningGoalOutputBoundary {
   void prepareSuccess(SetLearningGoalOutputData outputData);
   void prepareFailure(String errorMessage);
}
