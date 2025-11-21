package usecase.study_deck.load_deck_for_study;

import entity.Flashcard;
import entity.FlashcardDeck;
import usecase.FlashcardDataAccessInterface;
import usecase.deck.DeckDataAccessInterface;

public class LoadDeckForStudyInteractor implements LoadDeckForStudyInputBoundary {
    private final LoadDeckForStudyOutputBoundary presenter;
    private final DeckDataAccessInterface deckDAO;
    private final FlashcardDataAccessInterface flashcardDAO;

    public LoadDeckForStudyInteractor(LoadDeckForStudyOutputBoundary presenter, DeckDataAccessInterface deckDAO, FlashcardDataAccessInterface flashcardDAO) {
        this.presenter = presenter;
        this.deckDAO = deckDAO;
        this.flashcardDAO = flashcardDAO;
    }

    @Override
    public void execute(LoadDeckForStudyInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();

        FlashcardDeck deck = deckDAO.findById(deckId);

        if (deck == null) {
            presenter.presentFailureView("Deck not found.");
        }

        Flashcard firstCard = flashcardDAO.findByDeck(deckId).get(0);

        LoadDeckForStudyOutputData outputData = new LoadDeckForStudyOutputData(deckId, deck.getTitle(),
                firstCard.getId(), flashcardDAO.findByDeck(deckId).size(), 0, firstCard.getSourceWord());

        presenter.presentSuccessView(outputData);
    }
}
