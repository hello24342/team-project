package interface_adapter.create_flashcard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateFlashcardViewModel {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private String message = "";
    private int deckId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String newMessage) {
        String oldMessage = this.message;
        this.message = newMessage;
        pcs.firePropertyChange("message", oldMessage, newMessage);
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    // The View will register itself as a listener
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }
}

