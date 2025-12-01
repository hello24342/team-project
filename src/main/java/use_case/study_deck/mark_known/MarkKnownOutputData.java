package use_case.study_deck.mark_known;

public class MarkKnownOutputData {
    private final int deckId;
    private final int cardIndex;
    private final int deckKnownCount;
    private final int deckTotalCards;
    private final int userTotalKnownFlashcards;

    public MarkKnownOutputData(int deckId, int cardIndex, int deckKnownCount,
                               int deckTotalCards, int userTotalKnownFlashcards) {
        this.deckId = deckId;
        this.cardIndex = cardIndex;
        this.deckKnownCount = deckKnownCount;
        this.deckTotalCards = deckTotalCards;
        this.userTotalKnownFlashcards = userTotalKnownFlashcards;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}