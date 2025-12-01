package interface_adapter.learning_goal;

public class LearningGoalState {
    private int userId;
    private int learningGoal;

    public int getUserId() {return userId;}
    public int getLearningGoal() {return learningGoal;}

    public void setLearningGoal(int learningGoal) {this.learningGoal = learningGoal;}
    public void setUserId(int userId) {this.userId = userId;}
}
