package interface_adapter.deck;

import use_case.deck.open_deck.OpenDeckInputBoundary;
import use_case.deck.open_deck.OpenDeckInputData;

public class OpenDeckController {

    private final OpenDeckInputBoundary interactor;

    public OpenDeckController(OpenDeckInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void open(int deckId) {
        interactor.open(new OpenDeckInputData(deckId));
    }
}
