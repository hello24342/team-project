package app.factory;

import interface_adapter.study_deck.StudyDeckController;
import interface_adapter.study_deck.StudyDeckPresenter;
import interface_adapter.study_deck.StudyDeckViewModel;
import usecase.FlashcardDataAccessInterface;
import usecase.deck.DeckDataAccessInterface;
import usecase.study_deck.flip_card.FlipCardInputBoundary;
import usecase.study_deck.flip_card.FlipCardInteractor;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInputBoundary;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyInteractor;
import usecase.study_deck.next_card.NextCardInputBoundary;
import usecase.study_deck.next_card.NextCardInteractor;
import usecase.study_deck.previous_card.PreviousCardInputBoundary;
import usecase.study_deck.previous_card.PreviousCardInteractor;

public class StudyDeckUseCaseFactory {

    public static class StudyDeckBundle {
        public final StudyDeckViewModel vm;
        public final StudyDeckController controller;

        public StudyDeckBundle(StudyDeckViewModel vm, StudyDeckController controller) {
            this.vm = vm;
            this.controller = controller;
        }
    }

    public static StudyDeckBundle build(DeckDataAccessInterface deckDAO, FlashcardDataAccessInterface cardDAO) {
        StudyDeckViewModel vm = new StudyDeckViewModel();

        StudyDeckPresenter presenter = new StudyDeckPresenter(vm);

        LoadDeckForStudyInputBoundary loadInteractor = new LoadDeckForStudyInteractor(presenter, deckDAO, cardDAO);

        FlipCardInputBoundary flipInteractor = new FlipCardInteractor(cardDAO, presenter);

        NextCardInputBoundary nextInteractor = new NextCardInteractor(cardDAO, presenter);

        PreviousCardInputBoundary prevInteractor =
                new PreviousCardInteractor(cardDAO, presenter);

        // MarkKnownInputBoundary markKnownInteractor = new MarkKnownInteractor();
        // MarkUnknownInputBoundary markUnknownInteractor = new MarkUnknownInteractor();

        StudyDeckController controller = new StudyDeckController(loadInteractor, flipInteractor, nextInteractor,
                prevInteractor, /* markKnownInteractor */ null, /* markUnknownInteractor */ null
        );

        return new StudyDeckBundle(vm, controller);
    }
}
