package interface_adapter.study_deck;

import interface_adapter.ViewModel;
import view.StudyDeckView;

public class StudyDeckViewModel extends ViewModel<StudyDeckState> {
    public StudyDeckViewModel() {
        super("study deck");
        setState(new StudyDeckState());
    }
}
