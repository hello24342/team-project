package usecase.deck.open_deck;

public interface OpenDeckOutputBoundary {
    void prepareSuccessView(OpenDeckOutputData output);
    void prepareFailView(String message);
}
