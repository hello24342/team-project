package use_case.study_deck.mark_unknown;

import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;

public class MarkUnknownInteractor implements MarkUnknownInputBoundary {
    private final DeckDataAccessInterface deckDataAccess;
    private final FlashcardDataAccessInterface flashcardDataAccess;
    private final MarkUnknownOutputBoundary markUnknownPresenter;

    public MarkUnknownInteractor(DeckDataAccessInterface deckDataAccess,
                                 FlashcardDataAccessInterface flashcardDataAccess,
                                 MarkUnknownOutputBoundary markUnknownPresenter) {
        this.deckDataAccess = deckDataAccess;
        this.flashcardDataAccess = flashcardDataAccess;
        this.markUnknownPresenter = markUnknownPresenter;
    }

    @Override
    public void execute(MarkUnknownInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        int cardIndex = inputData.getCardId();

        // debugging why dont know card has not been added to dont know deck
        //System.out.println("[DEBUG] MarkUnknown called!");
        //System.out.println("  userId = " + userId);
        //System.out.println("  deckId = " + deckId);
        //System.out.println("  cardIndex = " + cardIndex);

        int dontKnowDeckId = deckDataAccess.findOrCreateDontKnowDeckId(userId);



        // debugging why dont know card has not been added to dont know deck
        // print don’t know deck id for debugging
        //System.out.println("  dontKnowDeckId = " + dontKnowDeckId);

        // Move the flashcard to the "Don't Know" deck
        flashcardDataAccess.markCardAsUnknown(cardIndex, deckId, dontKnowDeckId);
        MarkUnknownOutputData outputData = new MarkUnknownOutputData(dontKnowDeckId, cardIndex);
        markUnknownPresenter.presentSuccessView(outputData);
    }
}
