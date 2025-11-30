package entity;
import java.time.LocalDate;

public class LearningGoal {
    private final int id;
    private final int userId;
    private int dailyTarget;
    private LocalDate startDate;
    private LocalDate endDate;

    public LearningGoal(int id, int userId, int dailyTarget, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.dailyTarget = dailyTarget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getDailyTarget() {
        return dailyTarget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
