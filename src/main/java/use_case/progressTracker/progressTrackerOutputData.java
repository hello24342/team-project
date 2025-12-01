package use_case.progressTracker;

/**
 * Output Data for the progressTracker usecase
 */
public class progressTrackerOutputData {
    private final int wordsStudied;
    private final int wordsMastered;

    public progressTrackerOutputData(int wordsStudied, int wordsMastered) {
        this.wordsStudied = wordsStudied;
        this.wordsMastered = wordsMastered;
    }
    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}
}
