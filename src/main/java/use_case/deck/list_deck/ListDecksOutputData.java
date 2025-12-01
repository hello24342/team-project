package use_case.deck.list_deck;

import java.util.List;

public class ListDecksOutputData {
    private final java.util.List<DeckSummary> decks;

    public ListDecksOutputData(List<DeckSummary> decks) { this.decks = decks; }
    public java.util.List<DeckSummary> getDecks() { return decks; }

    public static class DeckSummary {
        private final int id;
        private final String title;

        public DeckSummary(int id, String title) { this.id = id; this.title = title; }
        public int getId() { return id; }
        public String getTitle() { return title; }
    }
}
