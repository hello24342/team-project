package use_case.progressTracker;

/**
 * Output Data for the progressTracker usecase
 */
public class progressTrackerOutputData {
    private final int wordsStudied;
    private final int wordsMastered;
    private final int dailyTarget;

    public progressTrackerOutputData(int wordsStudied, int wordsMastered, int dailyTarget) {
        this.wordsStudied = wordsStudied;
        this.wordsMastered = wordsMastered;
        this.dailyTarget = wordsMastered;
    }
    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}
    public int getDailyTarget() {return dailyTarget;}
}
