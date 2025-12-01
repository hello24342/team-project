package interface_adapter.edit_flashcard;

import interface_adapter.ViewModel;

public class EditFlashcardViewModel extends ViewModel<EditFlashcardState> {

    public EditFlashcardViewModel(String viewName) {
        super(viewName);
        //super("edit flashcard");
        setState(new EditFlashcardState());
    }
}
