package use_case.progressTracker;

/**
 * The Input Data for the progressTracker Use Case.
 */
public class progressTrackerInputData {
    private int id;
    private int userId;
    private int wordsStudied;
    private int wordsMastered;

    public progressTrackerInputData(int id, int userId, int wordsStudied, int wordsMastered) {
        this.id = id;
        this.userId = userId;
        this.wordsStudied = wordsStudied;
        this.wordsMastered = wordsMastered;
    }

    public int getId() {return id;}
    public int getUserId() {return userId;}
    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}
}
