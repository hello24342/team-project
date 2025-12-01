package interface_adapter.study_deck;

public class StudyDeckState {
    private int deckId;
    private int userId;
    private int cardIndex;
    private String cardText;
    public String username;
    private boolean isShowingFront;
    private boolean startWithSource;
    private String deckName;
    private String errorMessage;

    public int getDeckId() {
        return deckId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public String getCardText() {
        return cardText;
    }

    public String getUsername() { return username;
    }

    public boolean isShowingFront() {
        return isShowingFront;
    }

    public boolean startsWithSource() {
        return startWithSource;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public void setShowingFront(boolean b) {
        this.isShowingFront = b;
    }

    public void setStartWithSource(boolean b) {
        this.startWithSource = b;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
