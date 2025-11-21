package usecase.study_deck.next_card;

public interface NextCardOutputBoundary {
    void presentSuccessView(NextCardOutputData outputData);
    void presentFailureView(String errorMessage);
}
