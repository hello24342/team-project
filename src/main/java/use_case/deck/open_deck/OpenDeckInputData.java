package use_case.deck.open_deck;

public class OpenDeckInputData {
    private final int deckId;

    public OpenDeckInputData(int deckId) {
        this.deckId = deckId;
    }
    public int getDeckId() { return deckId; }
}
