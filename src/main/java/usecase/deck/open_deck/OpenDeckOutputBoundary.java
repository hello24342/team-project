package usecase.deck.open_deck;

public interface OpenDeckOutputBoundary {
    void present(OpenDeckOutputData output);
    void presentNotFound(String message);
}
