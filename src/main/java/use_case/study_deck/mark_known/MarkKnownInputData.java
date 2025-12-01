package use_case.study_deck.mark_known;

public class MarkKnownInputData {
    private final int userId;
    private final int deckId;
    private final int cardIndex;
    private final String username;
    private final int dontKnowDeckId;

    public MarkKnownInputData(int userId, int deckId, int cardIndex, String username, int dontKnowDeckId) {
        this.userId = userId;
        this.deckId = deckId;
        this.cardIndex = cardIndex;
        this.username = username;
        this.dontKnowDeckId = dontKnowDeckId;
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

    public String getUsername() { return username; }

    public int getDontKnowDeckId() { return dontKnowDeckId; }
}