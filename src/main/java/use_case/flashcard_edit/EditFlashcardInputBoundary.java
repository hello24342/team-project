package use_case.flashcard_edit;

public interface EditFlashcardInputBoundary {
    void execute(EditFlashcardInputData inputData);
    default void delete(EditFlashcardInputData inputData) {
    }
}
