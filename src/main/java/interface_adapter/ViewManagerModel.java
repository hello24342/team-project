package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {
    private String activeViewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getActiveView() {
        return activeViewName;
    }

    public void setActiveView(String activeViewName) {
        String oldValue = this.activeViewName;
        this.activeViewName = activeViewName;
        support.firePropertyChange("view", oldValue, activeViewName);
    }

    public void setState(String viewName) {
        setActiveView(viewName);
    }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.activeViewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}