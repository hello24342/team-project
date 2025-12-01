package use_case.study_deck.mark_unknown;

public class MarkUnknownOutputData {
    private final int deckId;
    private final int cardId;

    public MarkUnknownOutputData(int deckId, int cardId) {
        this.deckId = deckId;
        this.cardId = cardId;
    }

    public int getDeckId() { return deckId; }
    public int getCardId() { return cardId; }
}