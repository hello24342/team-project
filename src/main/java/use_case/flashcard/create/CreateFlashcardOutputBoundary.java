package use_case.flashcard.create;

public interface CreateFlashcardOutputBoundary {
    void present(CreateFlashcardOutputData response);
    void presentError(String errorMessage);
}
