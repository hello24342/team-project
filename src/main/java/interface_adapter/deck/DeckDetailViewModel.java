package interface_adapter.deck;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DeckDetailViewModel {

    public static final String DETAIL_PROPERTY = "deckDetail";
    public static final String ERROR_PROPERTY = "error";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private int deckId = -1;
    private String deckTitle = "";
    private List<CardVM> cards = new ArrayList<>();
    private String errorMessage = null;

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public int getDeckId() { return deckId; }
    public String getDeckTitle() { return deckTitle; }
    public List<CardVM> getCards() { return new ArrayList<>(cards); }
    public String getErrorMessage() { return errorMessage; }

    public void setDetail(int deckId, String deckTitle, List<CardVM> cards) {
        int oldId = this.deckId;
        this.deckId = deckId;
        this.deckTitle = deckTitle;
        this.cards = new ArrayList<>(cards);
        support.firePropertyChange(DETAIL_PROPERTY, oldId, deckId);
    }

    public void setError(String msg) {
        String old = this.errorMessage;
        this.errorMessage = msg;
        support.firePropertyChange(ERROR_PROPERTY, old, msg);
    }

    public static class CardVM {
        public final int id;
        public final String sourceWord;
        public final String targetWord;

        public CardVM(int id, String sourceWord, String targetWord) {
            this.id = id;
            this.sourceWord = sourceWord;
            this.targetWord = targetWord;
        }
    }
}
