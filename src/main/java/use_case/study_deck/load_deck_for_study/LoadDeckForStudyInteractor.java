package use_case.study_deck.load_deck_for_study;

import entity.Flashcard;
import entity.FlashcardDeck;
import use_case.UserDataAccessInterface;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;

import java.util.List;

public class LoadDeckForStudyInteractor implements LoadDeckForStudyInputBoundary {
    private final LoadDeckForStudyOutputBoundary presenter;
    private final DeckDataAccessInterface deckDAO;
    private final FlashcardDataAccessInterface flashcardDAO;
    private final UserDataAccessInterface userDao;

    public LoadDeckForStudyInteractor(LoadDeckForStudyOutputBoundary presenter, DeckDataAccessInterface deckDAO,
                                      FlashcardDataAccessInterface flashcardDAO, UserDataAccessInterface userDao) {
        this.presenter = presenter;
        this.deckDAO = deckDAO;
        this.flashcardDAO = flashcardDAO;
        this.userDao = userDao;
    }

    @Override
    public void execute(LoadDeckForStudyInputData inputData) {
        int userId = inputData.getUserId();
        int deckId = inputData.getDeckId();
        String username = userDao.getCurrentUsername();

        FlashcardDeck deck = deckDAO.findById(deckId);

        if (deck == null) {
            presenter.presentFailureView("Deck not found.");
            return;
        }

        List<Flashcard> cards = flashcardDAO.findByDeck(deckId);

        if (cards.isEmpty()) {
            presenter.presentFailureView("Deck is empty. Add some cards first!");
            return;
        }

        Flashcard firstCard = cards.get(0);

        LoadDeckForStudyOutputData outputData = new LoadDeckForStudyOutputData(userId, deckId,deck.getTitle(),
                firstCard.getId(), flashcardDAO.findByDeck(deckId).size(), 0, firstCard.getSourceWord(),
                firstCard.getTargetWord(), username);

        presenter.presentSuccessView(outputData);
    }
}
