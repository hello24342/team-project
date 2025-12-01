package interface_adapter.deck;

import use_case.deck.list_deck.ListDecksInputBoundary;
import use_case.deck.list_deck.ListDecksInputData;

public class ListDecksController {
    private final ListDecksInputBoundary interactor;
    private final int userId;

    public ListDecksController(ListDecksInputBoundary interactor, int userId) {
        this.interactor = interactor;
        this.userId = userId;
    }

    // Triggered when the user enters the deck menu
    public void onEnterDeckMenu() {
        interactor.execute(new ListDecksInputData(userId));
    }
}