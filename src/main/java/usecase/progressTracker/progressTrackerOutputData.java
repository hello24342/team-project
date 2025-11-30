package usecase.progressTracker;

/**
 * Output Data for the progressTracker usecase
 */
public class progressTrackerOutputData {
    private int wordsStudied;
    private int wordsMastered;

    public progressTrackerOutputData(int wordsStudied, int wordsMastered) {
        this.wordsStudied = wordsStudied;
        this.wordsMastered = wordsMastered;
    }
    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}
    public int getProgress(int wordsMastered, int wordsStudied) {
        return wordsMastered/wordsStudied;
    }
}
