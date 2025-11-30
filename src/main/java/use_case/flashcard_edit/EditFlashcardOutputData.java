package use_case.flashcard_edit;

public class EditFlashcardOutputData {
    private final int flashcardId;
    private final String message;

    public EditFlashcardOutputData(int flashcardId, String message) {
        this.flashcardId = flashcardId;
        this.message = message;
    }

    public int getFlashcardId() { return flashcardId; }
    public String getMessage() { return message; }
}