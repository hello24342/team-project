package use_case.deck.create_deck;

import java.util.List;

public class CreateDeckOutputData {
    private final List<DeckSummary> deckSummaries;

    public CreateDeckOutputData(List<DeckSummary> deckSummaries) {
        this.deckSummaries = deckSummaries;
    }

    public List<DeckSummary> getDeckSummaries() {
        return deckSummaries;
    }

    public static class DeckSummary {
        public final int deckId;
        public final String title;

        public DeckSummary(int deckId, String title) {
            this.deckId = deckId;
            this.title = title;
        }
    }
}
