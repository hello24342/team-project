package interface_adapter.flashcard;

import entity.Language;
import usecase.flashcard_create.CreateFlashcardInputBoundary;
import usecase.flashcard_create.CreateFlashcardInputData;

public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void create(String sourceWord,
                       Language sourceLang,
                       Language targetLang) {

        CreateFlashcardInputData request =
                new CreateFlashcardInputData(sourceWord, sourceLang, targetLang);

        interactor.createFlashcard(request);
    }
}
