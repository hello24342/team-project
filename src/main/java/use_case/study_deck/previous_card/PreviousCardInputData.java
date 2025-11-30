package use_case.study_deck.previous_card;

public class PreviousCardInputData {
    private final int deckId;
    private final int cardIndex;

    public PreviousCardInputData(int deckId, int cardIndex) {
        this.deckId = deckId;
        this.cardIndex = cardIndex;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public int getDeckId() {
        return deckId;
    }
}
