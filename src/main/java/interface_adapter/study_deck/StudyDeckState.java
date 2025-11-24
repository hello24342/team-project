package interface_adapter.study_deck;

public class StudyDeckState {
    private String cardText;
    private boolean showingFront;
    private String deckName;

    public String getCardText() {
        return cardText;
    }

    public boolean isShowingFront() {
        return showingFront;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public void setShowingFront(boolean b) {
        this.showingFront = b;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
