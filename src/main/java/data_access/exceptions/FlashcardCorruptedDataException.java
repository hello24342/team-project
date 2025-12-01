package data_access.exceptions;

public class FlashcardCorruptedDataException extends RuntimeException {
    public FlashcardCorruptedDataException(String message) {
        super(message);
    }

    public FlashcardCorruptedDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

