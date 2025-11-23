package interface_adapter.study_deck;

import usecase.study_deck.flip_card.FlipCardInputBoundary;
import usecase.study_deck.flip_card.FlipCardInputData;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputBoundary;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputData;
import usecase.study_deck.mark_known.MarkKnownInputBoundary;
import usecase.study_deck.mark_known.MarkKnownInputData;
import usecase.study_deck.mark_unknown.MarkUnknownInputBoundary;
import usecase.study_deck.mark_unknown.MarkUnknownInputData;
import usecase.study_deck.next_card.NextCardInputBoundary;
import usecase.study_deck.next_card.NextCardInputData;
import usecase.study_deck.previous_card.PreviousCardInputBoundary;
import usecase.study_deck.previous_card.PreviousCardInputData;

public class StudyDeckController {
    private final LoadDeckForStudyInputBoundary loadDeckForStudyInputBoundary;
    private final FlipCardInputBoundary flipCardInputBoundary;
    private final NextCardInputBoundary nextCardInputBoundary;
    private final PreviousCardInputBoundary previousCardInputBoundary;
    private final MarkKnownInputBoundary markKnownInputBoundary;
    private final MarkUnknownInputBoundary markUnknownInputBoundary;
    private final int userId;
    private final int deckId;
    private final int cardIndex;
    private final boolean isShowingFront;
    private final boolean startWithSource;

    public StudyDeckController(LoadDeckForStudyInputBoundary loadDeckForStudyInputBoundary,
                               FlipCardInputBoundary flipCardInputBoundary, NextCardInputBoundary nextCardInputBoundary,
                               PreviousCardInputBoundary previousCardInputBoundary,
                               MarkKnownInputBoundary markKnownInputBoundary,
                               MarkUnknownInputBoundary markUnknownInputBoundary, int userId, int deckId, int cardIndex,
                               boolean isShowingFront, boolean startWithSource) {
        this.loadDeckForStudyInputBoundary = loadDeckForStudyInputBoundary;
        this.flipCardInputBoundary = flipCardInputBoundary;
        this.nextCardInputBoundary = nextCardInputBoundary;
        this.previousCardInputBoundary = previousCardInputBoundary;
        this.markKnownInputBoundary = markKnownInputBoundary;
        this.markUnknownInputBoundary = markUnknownInputBoundary;
        this.userId = userId;
        this.deckId = deckId;
        this.cardIndex = cardIndex;
        this.isShowingFront = isShowingFront;
        this.startWithSource = startWithSource;
    }
    public void loadDeckForStudy() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        loadDeckForStudyInputBoundary.execute(inputData);
    }

    public void flipCard() {
        FlipCardInputData inputData = new FlipCardInputData(deckId, cardIndex, isShowingFront, startWithSource);
    }

    public void nextCard() {
        NextCardInputData inputData = new NextCardInputData();
    }

    public void previousCard() {
        PreviousCardInputData inputData = new PreviousCardInputData();
    }

    public void markKnown() {
        MarkKnownInputData inputData = new MarkKnownInputData();
    }

    public void markUnknown() {
        MarkUnknownInputData inputData = new MarkUnknownInputData();
    }
}