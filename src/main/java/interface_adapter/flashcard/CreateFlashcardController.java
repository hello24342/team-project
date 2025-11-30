package interface_adapter.flashcard;

import entity.Language;
import use_case.flashcard_create.CreateFlashcardInputBoundary;
import use_case.flashcard_create.CreateFlashcardInputData;

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
