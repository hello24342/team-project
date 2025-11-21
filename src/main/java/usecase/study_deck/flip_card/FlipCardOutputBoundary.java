package usecase.study_deck.flip_card;

public interface FlipCardOutputBoundary {
    void presentSuccessView(FlipCardOutputData outputData);
    void presentFailureView(String errorMessage);
}
