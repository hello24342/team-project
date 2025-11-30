package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

public class ViewModel<T> {
    private T state;

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public LoginViewModel getLoginViewModel() {
        return null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public String getViewName() {
        return this.viewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, this.state);
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

}
