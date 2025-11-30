package use_case.flashcard_create;

import data_access.FileFlashcardDataAccessObject;
import entity.Flashcard;
import entity.Language;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final TranslatorGateway translatorGateway;
    private final FileFlashcardDataAccessObject FileFlashcardDataAccessObject;
    // Check to make sure this ^ does what we need it to do
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(TranslatorGateway translatorGateway,
                                     FileFlashcardDataAccessObject flashcardDataAccess,
                                     CreateFlashcardOutputBoundary presenter)
    // this too ----------------------------^
    {
        this.translatorGateway = translatorGateway;
        this.FileFlashcardDataAccessObject = flashcardDataAccess;
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
        int id = FileFlashcardDataAccessObject.nextFlashcardId();

        Flashcard flashcard = new Flashcard(
                id,
                sourceWord,
                targetWord,
                sourceLang,
                targetLang
        );

        FileFlashcardDataAccessObject.save(flashcard);

        CreateFlashcardOutputData response = new CreateFlashcardOutputData(
                id,
                sourceWord,
                targetWord,
                sourceLang,
                targetLang
        );

        presenter.present(response);
    }
}

