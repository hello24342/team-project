package app.factory;

import interface_adapter.deck.*;
import use_case.FlashcardDataAccessInterface;
import data_access.FileUserDataAccessObject;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;
import use_case.deck.create_deck.CreateDeckInteractor;
import use_case.deck.list_deck.ListDecksInteractor;
import use_case.deck.open_deck.OpenDeckInteractor;


public class DeckManageUseCaseFactory {
    public static class DeckMenuBundle {
        public final DeckMenuViewModel vm;
        public final CreateDeckController createController;
        public final ListDecksController listController;
        public final OpenDeckController openController;
        public final DeckDetailViewModel detailVM;
        public final FileUserDataAccessObject userDAO;

        public DeckMenuBundle(DeckMenuViewModel vm,
                              CreateDeckController createCtl,
                              ListDecksController listCtl,
                              OpenDeckController openCtl,
                              DeckDetailViewModel detailVM, FileUserDataAccessObject userDAO) {
            this.vm = vm;
            this.createController = createCtl;
            this.listController = listCtl;
            this.openController = openCtl;
            this.detailVM = detailVM;
            this.userDAO = userDAO;
        }
    }
    // Builds the use case components for managing decks so that they can be used in the UI
    public static DeckMenuBundle build(DeckDataAccessInterface deckDAO,
                                       FlashcardDataAccessInterface cardDAO,
                                       FileUserDataAccessObject userDAO,
                                       int userId) {
        DeckMenuViewModel vm = new DeckMenuViewModel();

        // Presenters
        CreateDeckPresenter createPresenter = new CreateDeckPresenter(vm);
        ListDecksPresenter listPresenter = new ListDecksPresenter(vm);

        // Interactors
        CreateDeckInteractor createInteractor =
                new CreateDeckInteractor(deckDAO, userDAO, createPresenter);
        ListDecksInteractor listInteractor =
                new ListDecksInteractor(deckDAO, listPresenter);

        // Controllers
        CreateDeckController createCtl =
                new CreateDeckController(createInteractor, userId);
        ListDecksController listCtl =
                new ListDecksController(listInteractor, userId);

        //Detail VM + OpenDeck
        DeckDetailViewModel detailVM = new DeckDetailViewModel();
        OpenDeckPresenter openPresenter = new OpenDeckPresenter(detailVM);
        OpenDeckInteractor openInteractor = new OpenDeckInteractor(deckDAO, cardDAO, openPresenter);
        OpenDeckController openCtl = new OpenDeckController(openInteractor);

        return new DeckMenuBundle(vm, createCtl, listCtl, openCtl, detailVM, userDAO);



    }
}
