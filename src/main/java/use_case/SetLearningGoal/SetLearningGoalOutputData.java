package use_case.SetLearningGoal;

/**
 * Output Data for the setLearningGoal usecase
 */
public class SetLearningGoalOutputData {
    private int dailyTarget;
    public SetLearningGoalOutputData(int dailyTarget) {this.dailyTarget = dailyTarget;}
    public int getDailyTarget() {return dailyTarget;}
}
