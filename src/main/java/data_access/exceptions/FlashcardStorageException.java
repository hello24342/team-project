package data_access.exceptions;

public class FlashcardStorageException extends RuntimeException {
    public FlashcardStorageException(String message) {
        super(message);
    }

    public FlashcardStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
