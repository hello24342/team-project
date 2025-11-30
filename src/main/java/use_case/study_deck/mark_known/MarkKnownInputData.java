package use_case.study_deck.mark_known;

public class MarkKnownInputData {
    private final int userId;
    private final int deckId;
    private final int cardIndex;

    public MarkKnownInputData(int userId, int deckId, int cardIndex) {
        this.userId = userId;
        this.deckId = deckId;
        this.cardIndex = cardIndex;
    }

    public int getUserId() {
        return userId;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}