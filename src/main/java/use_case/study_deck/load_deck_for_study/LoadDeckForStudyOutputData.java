package use_case.study_deck.load_deck_for_study;

public class LoadDeckForStudyOutputData {
    private final int userId;
    private final int deckId;
    private final String deckTitle;
    private final int cardId;
    private final int cardCount;
    private final int currentCardIndex;
    private final String sourceWord;
    private final String targetWord;
    private final String username;

    public LoadDeckForStudyOutputData(int userId, int deckId, String deckTitle, int cardId, int cardCount, int currentCardIndex,
                                      String sourceWord, String targetWord, String username) {
        this.userId = userId;
        this.deckId = deckId;
        this.deckTitle = deckTitle;
        this.cardId = cardId;
        this.cardCount = cardCount;
        this.currentCardIndex = currentCardIndex;
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        this.username = username;
    }

    public int getUserId() {
        return userId;
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

    public String getUsername() { return username; }
}
