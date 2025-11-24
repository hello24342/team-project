package usecase.study_deck.flip_card;

public class FlipCardOutputData {
    private final int cardId;
    private final String textToShow;
    private final boolean isShowingFrontNow;

    public FlipCardOutputData(int cardId, String text, boolean isFrontShowingNow) {
        this.cardId = cardId;
        this.textToShow = text;
        this.isShowingFrontNow = isFrontShowingNow;
    }

    public int getCardId() {
        return cardId;
    }

    public String getTextToShow() {
        return textToShow;
    }

    public boolean isShowingFrontNow() {
        return isShowingFrontNow;
    }
}
