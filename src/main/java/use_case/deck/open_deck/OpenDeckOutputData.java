package use_case.deck.open_deck;

import java.util.List;

public class OpenDeckOutputData {
    private final int deckId;
    private final String deckTitle;
    private final List<CardSummary> cards;

    public OpenDeckOutputData(int deckId, String deckTitle, List<CardSummary> cards) {
        this.deckId = deckId;
        this.deckTitle = deckTitle;
        this.cards = cards;
    }

    public int getDeckId() { return deckId; }
    public String getDeckTitle() { return deckTitle; }
    public List<CardSummary> getCards() { return cards; }

    public static class CardSummary {
        public final int id;
        public final String sourceWord;
        public final String targetWord;

        public CardSummary(int id, String sourceWord, String targetWord) {
            this.id = id;
            this.sourceWord = sourceWord;
            this.targetWord = targetWord;
        }
    }
}