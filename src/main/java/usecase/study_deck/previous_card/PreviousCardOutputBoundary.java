package usecase.study_deck.previous_card;

public interface PreviousCardOutputBoundary {
    void presentSuccessView(PreviousCardOutputData outputData);
    void presentFailureView(String errorMessage);
}
