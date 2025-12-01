package use_case.flashcard.edit;

public interface EditFlashcardOutputBoundary {
    void prepareSuccessView(EditFlashcardOutputData outputData);
    void prepareFailView(String errorMessage);
}
