package use_case.flashcard.edit;

public class EditFlashcardInputData {
    private final int flashcardId;
    private final String newSourceWord;
    private final String newTargetWord;
    private final String newSourceLang;
    private final String newTargetLang;

    private final boolean delete; // true if delete request

    public EditFlashcardInputData(
            int flashcardId,
            String newSourceWord,
            String newTargetWord,
            String newSourceLang,
            String newTargetLang,
            boolean delete) {
        this.flashcardId = flashcardId;
        this.newSourceWord = newSourceWord;
        this.newTargetWord = newTargetWord;
        this.newSourceLang = newSourceLang;
        this.newTargetLang = newTargetLang;
        this.delete = delete;
    }

    // getters...
}

