package app.factory;

import data_access.DeckDataAccess;
import interface_adapter.deck.*;
import usecase.deck.create_deck.CreateDeckInteractor;
import usecase.deck.list_deck.ListDecksInteractor;

//TODO:OpenDeck use case assembly
public class DeckManageUseCaseFactory {
    public static class DeckMenuBundle {
        public final DeckMenuViewModel vm;
        public final CreateDeckController createController;
        public final ListDecksController listController;

        public DeckMenuBundle(DeckMenuViewModel vm,
                              CreateDeckController createCtl,
                              ListDecksController listCtl) {
            this.vm = vm;
            this.createController = createCtl;
            this.listController = listCtl;
        }
    }
    // Builds the use case components for managing decks so that they can be used in the UI
    public static DeckMenuBundle build(DeckDataAccess deckDAO,
                                       int userId) {
        DeckMenuViewModel vm = new DeckMenuViewModel();

        // Presenters
        CreateDeckPresenter createPresenter = new CreateDeckPresenter(vm);
        ListDecksPresenter listPresenter = new ListDecksPresenter(vm);

        // Interactors
        CreateDeckInteractor createInteractor =
                new CreateDeckInteractor(deckDAO, createPresenter);
        ListDecksInteractor listInteractor =
                new ListDecksInteractor(deckDAO, listPresenter);

        /// Controllers
        CreateDeckController createCtl =
                new CreateDeckController(createInteractor, userId);
        ListDecksController listCtl =
                new ListDecksController(listInteractor, userId);

        return new DeckMenuBundle(vm, createCtl, listCtl);

        //TODO: assemble opendeck
    }
}
