package usecase.flashcard_create;

import data_access.FlashcardDataAccessObject;
import entity.Flashcard;
import entity.Language;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final TranslatorGateway translatorGateway;
    private final FlashcardDataAccessObject flashcardDataAccess;
    // Check to make sure this ^ does what we need it to do
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(TranslatorGateway translatorGateway,
                                     FlashcardDataAccessObject flashcardDataAccess,
                                     CreateFlashcardOutputBoundary presenter)
    // this too ----------------------------^
    {
        this.translatorGateway = translatorGateway;
        this.flashcardDataAccess = flashcardDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void createFlashcard(CreateFlashcardInputData inputData) {

        String sourceWord = inputData.getSourceWord();
        Language sourceLang = inputData.getSourceLang();
        Language targetLang = inputData.getTargetLang();

        // *** Call translator API through gateway ***
        String targetWord = translatorGateway.translate(sourceWord, sourceLang, targetLang);

        // Generate ID using your data access layer
        int id = flashcardDataAccess.generateId();

        Flashcard flashcard = new Flashcard(
                id,
                sourceWord,
                targetWord,
                sourceLang,
                targetLang
        );

        flashcardDataAccess.save(flashcard);

        CreateFlashcardInputData response = new CreateFlashcardInputData(
                id,
                sourceWord,
                targetWord,
                sourceLang,
                targetLang
        );

        presenter.present(response);
    }
}

