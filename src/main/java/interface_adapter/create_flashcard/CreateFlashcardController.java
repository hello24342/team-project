package interface_adapter.create_flashcard;

import use_case.flashcard.create.CreateFlashcardInputBoundary;
import use_case.flashcard.create.CreateFlashcardInputData;

public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createFlashcard(CreateFlashcardInputData request) {
        // Simply delegate to the interactor and return the result
        interactor.createFlashcard(request);
    }
}

