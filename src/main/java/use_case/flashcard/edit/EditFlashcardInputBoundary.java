package use_case.flashcard.edit;

public interface EditFlashcardInputBoundary {
    void execute(EditFlashcardInputData inputData);
    default void delete(EditFlashcardInputData inputData) {
    }
}
