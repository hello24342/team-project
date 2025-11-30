package use_case.study_deck.previous_card;

public class PreviousCardOutputData {
    private final int deckId;
    private final int cardIndex;
    private final String sourceWord;
    private final String targetWord;

    public PreviousCardOutputData(int deckId, int cardIndex, String sourceWord, String targetWord) {
        this.deckId = deckId;
        this.cardIndex = cardIndex;
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
    }

    public int getDeckId() {
        return deckId;
    }
    public int getCardIndex() {
        return cardIndex;
    }
    public String getSourceWord() {
        return sourceWord;
    }
    public String getTargetWord() {
        return targetWord;
    }
}
