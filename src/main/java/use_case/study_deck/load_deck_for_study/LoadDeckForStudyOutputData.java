package use_case.study_deck.load_deck_for_study;

public class LoadDeckForStudyOutputData {
    private final int deckId;
    private final String deckTitle;
    private final int cardId;
    private final int cardCount;
    private final int currentCardIndex;
    private final String sourceWord;
    private final String targetWord;

    public LoadDeckForStudyOutputData(int deckId, String deckTitle, int cardId, int cardCount, int currentCardIndex,
                                      String sourceWord, String targetWord) {
        this.deckId = deckId;
        this.deckTitle = deckTitle;
        this.cardId = cardId;
        this.cardCount = cardCount;
        this.currentCardIndex = currentCardIndex;
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
    }

    public int getDeckId() {
        return deckId;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public int getCardId() {
        return cardId;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public String getSourceWord() {
        return sourceWord;
    }

    public String getTargetWord() {
        return targetWord;
    }
}
