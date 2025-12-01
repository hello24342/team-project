package use_case.flashcard.edit;

import entity.Flashcard;
import entity.Language;
import use_case.flashcard.FlashcardDataAccessInterface;

public class EditFlashcardInteractor implements EditFlashcardInputBoundary {

    private final FlashcardDataAccessInterface flashcardDAO;
    private final EditFlashcardOutputBoundary presenter;

    public EditFlashcardInteractor(FlashcardDataAccessInterface flashcardDAO,
                                   EditFlashcardOutputBoundary presenter) {
        this.flashcardDAO = flashcardDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditFlashcardInputData inputData) {

        int id = inputData.getFlashcardId();
        Flashcard card = flashcardDAO.findById(id);

        if (card == null) {
            presenter.prepareFailView("Flashcard not found: " + id);
            return;
        }

        if (inputData.isDelete()) {
            flashcardDAO.delete(id);
            presenter.prepareSuccessView(
                    new EditFlashcardOutputData(id, "Flashcard deleted.")
            );
            return;
        }

        String newSourceWord = inputData.getNewSourceWord();
        String newTargetWord = inputData.getNewTargetWord();
        String newSourceLangStr = inputData.getNewSourceLang();
        String newTargetLangStr = inputData.getNewTargetLang();

        if (newSourceWord == null || newSourceWord.trim().isEmpty()
                || newTargetWord == null || newTargetWord.trim().isEmpty()) {
            presenter.prepareFailView("Source and target words cannot be empty.");
            return;
        }

        try {
            Language newSourceLang = Language.valueOf(newSourceLangStr);
            Language newTargetLang = Language.valueOf(newTargetLangStr);

            card.setSourceWord(newSourceWord.trim());
            card.setTargetWord(newTargetWord.trim());
            card.setSourceLang(newSourceLang);
            card.setTargetLang(newTargetLang);

            flashcardDAO.update(id);

            presenter.prepareSuccessView(
                    new EditFlashcardOutputData(id, "Flashcard updated.")
            );
        } catch (IllegalArgumentException e) {
            presenter.prepareFailView("Invalid languages for flashcard.");
        }
    }

    @Override
    public void delete(EditFlashcardInputData inputData) {
        EditFlashcardInputData deleteInput = new EditFlashcardInputData(
                inputData.getFlashcardId(),
                inputData.getNewSourceWord(),
                inputData.getNewTargetWord(),
                inputData.getNewSourceLang(),
                inputData.getNewTargetLang(),
                true
        );
        execute(deleteInput);
    }
}
