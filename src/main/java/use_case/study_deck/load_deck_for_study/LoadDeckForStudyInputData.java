package use_case.study_deck.load_deck_for_study;

public class LoadDeckForStudyInputData {
    private final int userId;
    private final int deckId;
    private final String username;

    public LoadDeckForStudyInputData(int userId, int deckId, String username) {
        this.userId = userId;
        this.deckId = deckId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public int getDeckId() {
        return deckId;
    }

    public String getUsername() { return username; }
}
