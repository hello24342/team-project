package interface_adapter.study_deck;

public class StudyDeckState {
    private int deckId;
    private int userId;
    private int cardIndex;
    private String cardText;
    private boolean isShowingFront;
    private boolean startWithSource;
    private boolean isShowingFrontNow;
    private String deckName;

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

    public boolean isShowingFront() {
        return isShowingFront;
    }

    public boolean startsWithSource() {
        return startWithSource;
    }

    public boolean isShowingFrontNow() {
        return isShowingFrontNow;
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

    public void setShowingFrontNow(boolean b) {
        this.isShowingFrontNow = b;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
