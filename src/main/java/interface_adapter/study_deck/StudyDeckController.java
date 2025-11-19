package interface_adapter.study_deck;

import usecase.study_deck.StudyDeckInputBoundary;
import usecase.study_deck.StudyDeckInputData;

public class StudyDeckController {
    private final StudyDeckInputBoundary studyDeckInputBoundary;
    private final int userId;
    private final int deckId;

    public StudyDeckController(StudyDeckInputBoundary studyDeckInputBoundary, int userId, int deckId) {
        this.studyDeckInputBoundary = studyDeckInputBoundary;
        this.userId = userId;
        this.deckId = deckId;
    }

    public void flipCard() {
        StudyDeckInputData inputData = new StudyDeckInputData(userId, deckId);

        studyDeckInputBoundary.execute(inputData);
    }

    public void nextCard() {
        StudyDeckInputData inputData = new StudyDeckInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void previousCard() {
        StudyDeckInputData inputData = new StudyDeckInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void markKnown() {
        StudyDeckInputData inputData = new StudyDeckInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }

    public void markUnknown() {
        StudyDeckInputData inputData = new StudyDeckInputData(userId, deckId);
        studyDeckInputBoundary.execute(inputData);
    }
}