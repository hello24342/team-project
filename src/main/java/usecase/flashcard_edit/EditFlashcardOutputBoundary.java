package usecase.flashcard_edit;

public interface EditFlashcardOutputBoundary {
    void prepareSuccessView(EditFlashcardOutputData outputData);
    void prepareFailView(String errorMessage);
}
