package interface_adapter.study_deck;

import interface_adapter.ViewModel;

public class StudyDeckViewModel extends ViewModel<StudyDeckState> {
    public static final String ERROR_PROPERTY = "error";

    private String errorMessage;

    public StudyDeckViewModel() {
        super("study deck");
        setState(new StudyDeckState());
    }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String msg) {
        this.errorMessage = msg;
        firePropertyChange(ERROR_PROPERTY);
    }
}
