package interface_adapter.study_deck;

import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputBoundary;
import usecase.study_deck.load_deck_for_study.StudyDeckInputBoundary;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputData;

public class StudyDeckController {
    private final LoadDeckForStudyInputBoundary loadDeckForStudyInputBoundary;
    private final int userId;
    private final int deckId;

    public StudyDeckController(int userId, int deckId) {
        this.userId = userId;
        this.deckId = deckId;
    }
    public void loadDeckForStudy() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        loadDeckForStudyInputBoundary.execute(inputData);
    }

    public void flipCard() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void nextCard() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void previousCard() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void markKnown() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void markUnknown() {
        LoadDeckForStudyInputData inputData = new LoadDeckForStudyInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }
}