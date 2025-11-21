package usecase.study_deck.flip_card;

public class FlipCardInputData {
    private final int deckId;
    private final int cardIndex;
    private final boolean isShowingFront;
    private final boolean startWithSource;

    public FlipCardInputData(int deckId, int cardIndex, boolean isShowingFront, boolean startWithSource) {
        this.deckId = deckId;
        this.cardIndex = cardIndex;
        this.isShowingFront = isShowingFront;
        this.startWithSource = startWithSource;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public boolean isShowingFront() {
        return isShowingFront;
    }

    public boolean startsWithSource() {
        return startWithSource;
    }
}
