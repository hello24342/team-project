package interface_adapter.logged_in;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class LoggedInViewModel extends ViewModel {
    private LoggedInState state = new LoggedInState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LoggedInViewModel() {
        super("logged in");
    }

    public void setState(LoggedInState state) {
        this.state = state;
    }

    public LoggedInState getState() {
        return state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Helper methods for deck management
    public void addDeck(String deckName) {
        state.addUserDeck(deckName);
        firePropertyChanged();
    }

    public void setDecks(List<String> decks) {
        state.setUserDecks(decks);
        firePropertyChanged();
    }
}