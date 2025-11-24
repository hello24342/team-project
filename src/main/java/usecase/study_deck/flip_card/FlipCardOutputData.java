package usecase.study_deck.flip_card;

public class FlipCardOutputData {
    private final int cardId;
    private final String text;
    private final boolean isShowingFrontNow;

    public FlipCardOutputData(int cardId, String text, boolean isFrontShowingNow) {
        this.cardId = cardId;
        this.text = text;
        this.isShowingFrontNow = isFrontShowingNow;
    }

    public int getCardId() {
        return cardId;
    }

    public String getTextToShow() {
        return text;
    }

    public boolean isShowingFrontNow() {
        return isShowingFrontNow;
    }
}
