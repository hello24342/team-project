package usecase.study_deck.mark_unknown;

public class MarkUnknownInputData {
    private final int userId;
    private final int deckId;
    private final int cardId;

    public MarkUnknownInputData(int userId, int deckId, int cardId) {
        this.userId = userId;
        this.deckId = deckId;
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardId() {
        return cardId;
    }
}