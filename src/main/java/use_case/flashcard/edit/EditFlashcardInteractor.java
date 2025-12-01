package use_case.flashcard.edit;

import entity.Flashcard;
import entity.Language;
import use_case.flashcard.FlashcardDataAccessInterface;

public class EditFlashcardInteractor implements EditFlashcardInputBoundary {

    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final EditFlashcardOutputBoundary presenter;

    public EditFlashcardInteractor(FlashcardDataAccessInterface flashcardDataAccess,
                                   EditFlashcardOutputBoundary presenter) {
        this.flashcardDataAccess = flashcardDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditFlashcardInputData inputData) {
        int cardId = inputData.getFlashcardId();

        // 1. Load the existing flashcard
        Flashcard card = flashcardDataAccess.findById(cardId);
        if (card == null) {
            presenter.prepareFailView("Flashcard " + cardId + " not found.");
            return;
        }

        // 2. Alternative flow: delete
        if (inputData.isDelete()) {
            flashcardDataAccess.delete(cardId);
            EditFlashcardOutputData outputData =
                    new EditFlashcardOutputData(cardId, "Flashcard deleted.");
            presenter.prepareSuccessView(outputData);
            return;
        }

        // 3. Main flow: edit (null means "leave unchanged")
        if (inputData.getNewSourceWord() != null) {
            card.setSourceWord(inputData.getNewSourceWord());
        }
        if (inputData.getNewTargetWord() != null) {
            card.setTargetWord(inputData.getNewTargetWord());
        }

        if (inputData.getNewSourceLang() != null) {
            try {
                Language newSourceLang = Language.valueOf(inputData.getNewSourceLang());
                card.setSourceLang(newSourceLang);
            } catch (IllegalArgumentException e) {
                presenter.prepareFailView("Invalid source language: " + inputData.getNewSourceLang());
                return;
            }
        }

        if (inputData.getNewTargetLang() != null) {
            try {
                Language newTargetLang = Language.valueOf(inputData.getNewTargetLang());
                card.setTargetLang(newTargetLang);
            } catch (IllegalArgumentException e) {
                presenter.prepareFailView("Invalid target language: " + inputData.getNewTargetLang());
                return;
            }
        }

        // 4. Persist the updated flashcard
        flashcardDataAccess.update(cardId);

        EditFlashcardOutputData outputData =
                new EditFlashcardOutputData(cardId, "Flashcard updated.");
        presenter.prepareSuccessView(outputData);
    }
}
