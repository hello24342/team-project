package interface_adapter.edit_flashcard;

import interface_adapter.ViewModel;

public class EditFlashcardViewModel extends ViewModel<EditFlashcardState> {

    public EditFlashcardViewModel() {
        super("EditFlashcard");
        setState(new EditFlashcardState());
    }
}
