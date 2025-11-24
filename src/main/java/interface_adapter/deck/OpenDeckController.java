package interface_adapter.deck;

import usecase.deck.open_deck.OpenDeckInputBoundary;
import usecase.deck.open_deck.OpenDeckInputData;
import usecase.deck.open_deck.OpenDeckOutputData;

public class OpenDeckController {
    private final OpenDeckInputBoundary openDeckInteractor;

    public OpenDeckController(OpenDeckInputBoundary openDeckInteractor) {
        this.openDeckInteractor = openDeckInteractor;
    }

    public void execute(String deckName) {
        OpenDeckInputData inputData = new OpenDeckInputData(deckName);
        openDeckInteractor.execute(inputData);
    }
}