package usecase.study_deck.flip_card;

public class FlipCardOutputData {
    private final String textToShow;
    private final boolean isShowingFront;

    public FlipCardOutputData(String text, boolean isShowingFront) {
        this.textToShow = text;
        this.isShowingFront = isShowingFront;
    }

    public String getTextToShow() {
        return textToShow;
    }

    public boolean isShowingFrontNow() {
        return isShowingFront;
    }
}
