package use_case.study_deck.flip_card;

import entity.Flashcard;
import use_case.flashcard.FlashcardDataAccessInterface;

public class FlipCardInteractor implements FlipCardInputBoundary {
    private final FlashcardDataAccessInterface flashcardDAO;
    private final FlipCardOutputBoundary presenter;

    public FlipCardInteractor(FlashcardDataAccessInterface flashcardDAO, FlipCardOutputBoundary presenter) {
        this.flashcardDAO = flashcardDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(FlipCardInputData inputData) {
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardIndex();
        Flashcard card = flashcardDAO.findByDeck(deckId).get(cardIndex);
        String front;
        String back;

        // Setting the front and back text based on whether user chose to start with source or target word
        if (inputData.startsWithSource()) {
            front = card.getSourceWord();
            back = card.getTargetWord();
        }
        else {
            front = card.getTargetWord();
            back = card.getSourceWord();
        }

        boolean isShowingFrontNow = !inputData.isShowingFront();
        String textToShow = isShowingFrontNow ? front : back;

        FlipCardOutputData outputData = new FlipCardOutputData(textToShow, isShowingFrontNow);
        presenter.presentSuccessView(outputData);
    }
}
