package usecase.study_deck.flip_card;

import usecase.FlashcardDataAccessInterface;

public class FlipCardInteractor implements FlipCardInputBoundary {
    private final FlashcardDataAccessInterface flashcardDAO;
    private final FlipCardOutputBoundary presenter;

    public FlipCardInteractor(FlashcardDataAccessInterface flashcardDAO, FlipCardOutputBoundary presenter) {
        this.flashcardDAO = flashcardDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(FlipCardInputData inputData) {

    }
}
