package use_case.study_deck.mark_known;

public class MarkKnownOutputData {
    private final int deckId;
    private final int cardIndex;

    public MarkKnownOutputData(int deckId, int cardIndex) {
        this.deckId = deckId;
        this.cardIndex = cardIndex;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}