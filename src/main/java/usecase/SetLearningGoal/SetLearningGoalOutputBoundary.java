package usecase.SetLearningGoal;

import java.time.LocalDate;

/**
 * The output boundary for the setLearningGoal usecase.
 */
public interface SetLearningGoalOutputBoundary {
   void presentSuccess(SetLearningGoalOutputData outputData);
}
