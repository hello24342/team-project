package usecase.progressTracker;

import java.time.LocalDate;

/**
 * The output boundary for the progress tracker usecase.
 */
public interface progressTrackerOutputBoundary {
    void setWordsStudied(int wordsStudied);
    void setWordsMastered(int wordsMastered);
}
