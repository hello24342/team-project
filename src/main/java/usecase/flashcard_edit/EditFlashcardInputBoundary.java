package usecase.flashcard_edit;

public interface EditFlashcardInputBoundary {
    void execute(EditFlashcardInputData inputData);
    default void delete(EditFlashcardInputData inputData) {
    }
}
