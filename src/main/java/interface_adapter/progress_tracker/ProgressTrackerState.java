package interface_adapter.progress_tracker;

public class ProgressTrackerState {
    private int wordsStudied;
    private int wordsMastered;

    public int getWordsStudied() {return wordsStudied;}
    public int getWordsMastered() {return wordsMastered;}

    public void setWordsStudied(int wordsStudied) {this.wordsStudied = wordsStudied;}
    public void setWordsMastered(int wordsMastered) {this.wordsMastered = wordsMastered;}
}
