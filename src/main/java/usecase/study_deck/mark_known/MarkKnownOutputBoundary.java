package usecase.study_deck.mark_known;

public interface MarkKnownOutputBoundary {
    void presentSuccessView(MarkKnownOutputData outputData);
    void presentFailureView(String errorMessage);
}
