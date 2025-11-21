package usecase.study_deck.load_deck_for_study;
import java.util.List;

public class LoadDeckForStudyOutputData {
    private final int deckId;
    private final String deckTitle;
    private final int cardId;
    private final int cardCount;
    private final int currentCardIndex;
    private final String sourceWord;

    public LoadDeckForStudyOutputData(int deckId, String deckTitle, int cardId, int cardCount, int currentCardIndex,
                                      String sourceWord) {
        this.deckId = deckId;
        this.deckTitle = deckTitle;
        this.cardId = cardId;
        this.cardCount = cardCount;
        this.currentCardIndex = currentCardIndex;
        this.sourceWord = sourceWord;
    }

    public int getDeckId() {
        return deckId;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public int getCardId() {
        return cardId;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public String getSourceWord() {
        return sourceWord;
    }
}
