package use_case.flashcard.create;

import entity.Flashcard;
import entity.Language;
import use_case.flashcard.FlashcardDataAccessInterface;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final TranslatorGateway translator;
    private final FlashcardDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(TranslatorGateway translator,
                                     FlashcardDataAccessInterface flashcardDataAccess,
                                     CreateFlashcardOutputBoundary presenter)
    {
        this.translator = translator;
        this.dataAccess = flashcardDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void createFlashcard(CreateFlashcardInputData inputData) {

        String sourceWord = inputData.getSourceWord();
        Language sourceLang = inputData.getSourceLang();
        Language targetLang = inputData.getTargetLang();
        int deckId = inputData.getDeckId();

        // API translation call
        String targetWord = translator.translate(sourceWord, sourceLang, targetLang);

        int id = dataAccess.nextFlashcardId();

        Flashcard card = new Flashcard(
                id,
                sourceWord,
                targetWord,
                sourceLang,
                targetLang
        );

        card.addDeck(deckId);

        dataAccess.save(card);

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

