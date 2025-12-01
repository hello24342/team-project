package use_case.study_deck.load_deck_for_study;

public class LoadDeckForStudyInputData {
    private final int userId;
    private final int deckId;

    public LoadDeckForStudyInputData(int userId, int deckId) {
        this.userId = userId;
        this.deckId = deckId;
    }

    public int getUserId() {
        return userId;
    }

    public int getDeckId() {
        return deckId;
    }
}
