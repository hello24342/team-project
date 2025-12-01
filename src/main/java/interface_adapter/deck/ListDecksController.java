package interface_adapter.deck;

import data_access.FileUserDataAccessObject;
import use_case.deck.list_deck.ListDecksInputBoundary;
import use_case.deck.list_deck.ListDecksInputData;

public class ListDecksController {
    private final ListDecksInputBoundary interactor;
    private final FileUserDataAccessObject userDAO;

    public ListDecksController(ListDecksInputBoundary interactor, FileUserDataAccessObject userDAO) {
        this.interactor = interactor;
        this.userDAO = userDAO;
    }

    // Triggered when the user enters the deck menu
    public void onEnterDeckMenu() {
        interactor.execute(new ListDecksInputData(userDAO.getCurrentUserId()));
    }
}