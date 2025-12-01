package use_case.SetLearningGoal;

import java.time.LocalDate;

/**
 * The Input Data for the setLearningGoal Use Case.
 */
public class SetLearningGoalInputData {
    private int userId;
    private int dailyTarget;
    private LocalDate startDate;
    private LocalDate endDate;

    public SetLearningGoalInputData(int userId, int dailyTarget) {
        this.userId = userId;
        this.dailyTarget = dailyTarget;
    }

    public int getUserId() { return userId; }
    public int getDailyTarget() { return dailyTarget; }
}
