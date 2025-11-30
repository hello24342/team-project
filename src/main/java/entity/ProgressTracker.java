package entity;

public class ProgressTracker {
    private int id;
    private int userId;
    private int wordsStudied;
    private int wordsMastered;

    public ProgressTracker(int id, int userId, int wordsStudied, int wordsMastered) {
        this.id = id;
        this.userId = userId;
        this.wordsStudied = wordsStudied;
        this.wordsMastered = wordsMastered;
    }
    public int getId() { return id;}
    public int getUserId() { return userId;}
    public int getWordsStudied() { return wordsStudied;}
    public int getWordsMastered() { return wordsMastered;}

    public void setWordsStudied(int wordsStudied) { this.wordsStudied = wordsStudied; }
    public void setWordsMastered(int wordsMastered) { this.wordsMastered = wordsMastered; }

}
