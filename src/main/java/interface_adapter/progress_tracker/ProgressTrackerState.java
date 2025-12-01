package interface_adapter.progress_tracker;

public class ProgressTrackerState {
    private int id;
    private int userId;
    private int wordsStudied;
    private int wordsMastered;
    private int dailyTarget;

    public int getId() {return id;}
    public int getUserId() {return userId;}
    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}
    public int getDailyTarget() {return dailyTarget;}

    public void setId(int id) {this.id = id;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setWordsStudied(int wordsStudied) {this.wordsStudied = wordsStudied;}
    public void setWordsMastered(int wordsMastered) {this.wordsMastered = wordsMastered;}
    public void setDailyTarget(int dailyTarget) {this.dailyTarget = dailyTarget;}
}
