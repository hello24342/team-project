package interface_adapter.deck;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DeckMenuViewModel {

    public static final String DECK_LIST_PROPERTY = "deckList";
    public static final String ERROR_PROPERTY = "error";

    private final PropertyChangeSupport support;
    private List<DeckTileVM> tiles;
    private String errorMessage;

    public DeckMenuViewModel() {
        this.support = new PropertyChangeSupport(this);
        this.tiles = new ArrayList<>();
        this.errorMessage = null;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public List<DeckTileVM> getTiles() {
        return new ArrayList<>(tiles);
    }

    public void setTiles(List<DeckTileVM> newTiles) {
        List<DeckTileVM> oldTiles = this.tiles;
        this.tiles = new ArrayList<>(newTiles);
        support.firePropertyChange(DECK_LIST_PROPERTY, oldTiles, newTiles);
    }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String msg) {
        String old = this.errorMessage;
        this.errorMessage = msg;
        support.firePropertyChange(ERROR_PROPERTY, old, msg);
    }

    public static class DeckTileVM {
        public final int deckId;
        public final String title;

        public DeckTileVM(int deckId, String title) {
            this.deckId = deckId;
            this.title = title;
        }
    }

}
