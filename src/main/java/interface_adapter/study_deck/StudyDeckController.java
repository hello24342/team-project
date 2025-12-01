package interface_adapter.study_deck;

import use_case.UserDataAccessInterface;
import use_case.study_deck.flip_card.FlipCardInputBoundary;
import use_case.study_deck.flip_card.FlipCardInputData;
import use_case.study_deck.load_deck_for_study.LoadDeckForStudyInputBoundary;
import use_case.study_deck.load_deck_for_study.LoadDeckForStudyInputData;
import use_case.study_deck.mark_known.MarkKnownInputBoundary;
import use_case.study_deck.mark_known.MarkKnownInputData;
import use_case.study_deck.mark_unknown.MarkUnknownInputBoundary;
import use_case.study_deck.mark_unknown.MarkUnknownInputData;
import use_case.study_deck.next_card.NextCardInputBoundary;
import use_case.study_deck.next_card.NextCardInputData;
import use_case.study_deck.previous_card.PreviousCardInputBoundary;
import use_case.study_deck.previous_card.PreviousCardInputData;

public class StudyDeckController {
    private final LoadDeckForStudyInputBoundary loadDeckForStudyInputBoundary;
    private final FlipCardInputBoundary flipCardInputBoundary;
    private final NextCardInputBoundary nextCardInputBoundary;
    private final PreviousCardInputBoundary previousCardInputBoundary;
    private final MarkKnownInputBoundary markKnownInputBoundary;
    private final MarkUnknownInputBoundary markUnknownInputBoundary;
    private final UserDataAccessInterface userDataAccessObject;

    public StudyDeckController(LoadDeckForStudyInputBoundary loadDeckForStudyInputBoundary,
                               FlipCardInputBoundary flipCardInputBoundary, NextCardInputBoundary nextCardInputBoundary,
                               PreviousCardInputBoundary previousCardInputBoundary,
                               MarkKnownInputBoundary markKnownInputBoundary,
                               MarkUnknownInputBoundary markUnknownInputBoundary, UserDataAccessInterface userDataAccessObject) {
        this.loadDeckForStudyInputBoundary = loadDeckForStudyInputBoundary;
        this.flipCardInputBoundary = flipCardInputBoundary;
        this.nextCardInputBoundary = nextCardInputBoundary;
        this.previousCardInputBoundary = previousCardInputBoundary;
        this.markKnownInputBoundary = markKnownInputBoundary;
        this.markUnknownInputBoundary = markUnknownInputBoundary;
        this.userDataAccessObject = userDataAccessObject;
    }
    public void loadDeckForStudy(int userId, int deckId, String username) {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId, username);
        loadDeckForStudyInputBoundary.execute(inputData);
    }

    public void flipCard(int deckId, int cardIndex, boolean isShowingFront, boolean startWithSource) {
        FlipCardInputData inputData = new FlipCardInputData(deckId, cardIndex, isShowingFront, startWithSource);
        flipCardInputBoundary.execute(inputData);
    }

    public void nextCard(int deckId, int cardIndex) {
        NextCardInputData inputData = new NextCardInputData(deckId, cardIndex);
        nextCardInputBoundary.execute(inputData);
    }

    public void previousCard(int deckId, int cardIndex) {
        PreviousCardInputData inputData = new PreviousCardInputData(deckId, cardIndex);
        previousCardInputBoundary.execute(inputData);
    }

    public void markKnown(int deckId, int cardIndex, int dontKnowDeckId) {
        String username = userDataAccessObject.getCurrentUsername();
        Integer userId = userDataAccessObject.getCurrentUserId();
        MarkKnownInputData inputData = new MarkKnownInputData(userId, deckId, cardIndex, username, dontKnowDeckId);
        markKnownInputBoundary.execute(inputData);
    }

    public void markUnknown(int userId, int deckId, int cardIndex) {
        MarkUnknownInputData inputData = new MarkUnknownInputData(userId, deckId, cardIndex);
        markUnknownInputBoundary.execute(inputData);
    }
}