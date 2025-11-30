package usecase.SetLearningGoal;

import java.time.LocalDate;

/**
 * The Input Data for the setLearningGoal Use Case.
 */
public class SetLearningGoalInputData {
    private int userId;
    private int dailyTarget;
    private LocalDate startDate;
    private LocalDate endDate;

    public SetLearningGoalInputData(int userId, int dailyTarget, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.dailyTarget = dailyTarget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getUserId() { return userId; }
    public int getDailyTarget() { return dailyTarget; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}
