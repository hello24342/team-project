package usecase.study_deck.load_deck_for_study;

public interface LoadDeckForStudyOutputBoundary {
    void presentSuccessView(LoadDeckForStudyOutputData outputData);
    void presentFailureView(String errorMessage);
}
