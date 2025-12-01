package interface_adapter.edit_flashcard;

import use_case.flashcard.edit.EditFlashcardInputBoundary;
import use_case.flashcard.edit.EditFlashcardInputData;

public class EditFlashcardController {
    private final EditFlashcardInputBoundary editFlashcardInteractor;
    private int currentCardId;
    private String currentSourceWord;
    private String currentTargetWord;

    public EditFlashcardController(EditFlashcardInputBoundary editFlashcardInteractor,
                                   EditFlashcardViewModel viewModel) {
        this.editFlashcardInteractor = editFlashcardInteractor;
        this.viewModel = viewModel;

    }

    public void prepareViewForEdit(int cardId, String sourceWord, String targetWord) {
        this.currentCardId = cardId;
        this.currentSourceWord = sourceWord;
        this.currentTargetWord = targetWord;

        EditFlashcardState state = viewModel.getState();
        state.setFlashcardId(cardId);
        state.setSourceWord(sourceWord);
        state.setTargetWord(targetWord);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    public void executeEdit(String newSourceWord, String newTargetWord,
                            String newSourceLang, String newTargetLang) {
        EditFlashcardInputData inputData = new EditFlashcardInputData(
                currentCardId, newSourceWord, newTargetWord, newSourceLang, newTargetLang, false
        );
        editFlashcardInteractor.execute(inputData);
    }

    public void executeDelete() {
        EditFlashcardInputData inputData = new EditFlashcardInputData(
                currentCardId, null, null, null, null, true
        );
        editFlashcardInteractor.execute(inputData);
    }

    public int getCurrentCardId() { return currentCardId; }
    public String getCurrentSourceWord() { return currentSourceWord; }
    public String getCurrentTargetWord() { return currentTargetWord; }
}