package use_case.study_deck.next_card;

public class NextCardInputData {
    private final int deckId;
    private final int cardIndex;

    public NextCardInputData(int deckId, int cardIndex) {
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
