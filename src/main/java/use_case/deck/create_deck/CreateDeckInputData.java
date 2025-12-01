package use_case.deck.create_deck;

public class CreateDeckInputData {
    private final int userId;
    private final String title;

    public CreateDeckInputData(int userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    public int getUserId() { return userId; }

    public String getTitle() { return title; }
}
