package use_case.deck.list_deck;

public class ListDecksInputData {
    private final int userId;

    public ListDecksInputData(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }
}