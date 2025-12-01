package interface_adapter.create_flashcard;

import entity.Language;
import use_case.flashcard.create.CreateFlashcardInputBoundary;
import use_case.flashcard.create.CreateFlashcardInputData;

public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createFlashcard(int deckId,
                                String sourceWord,
                                Language sourceLang,
                                Language targetLang) {

        CreateFlashcardInputData input =
                new CreateFlashcardInputData(deckId,
                        sourceWord,
                        sourceLang,
                        targetLang);
        interactor.createFlashcard(input);
    }
}

