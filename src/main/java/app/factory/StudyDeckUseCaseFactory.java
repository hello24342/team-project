package app.factory;

import data_access.FileUserDataAccessObject;
import interface_adapter.study_deck.StudyDeckController;
import interface_adapter.study_deck.StudyDeckPresenter;
import interface_adapter.study_deck.StudyDeckViewModel;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;
import use_case.study_deck.flip_card.FlipCardInputBoundary;
import use_case.study_deck.flip_card.FlipCardInteractor;
import use_case.study_deck.load_deck_for_study.LoadDeckForStudyInputBoundary;
import use_case.study_deck.load_deck_for_study.LoadDeckForStudyInteractor;
import use_case.study_deck.mark_known.MarkKnownInputBoundary;
import use_case.study_deck.mark_known.MarkKnownInteractor;
import use_case.study_deck.mark_unknown.MarkUnknownInputBoundary;
import use_case.study_deck.mark_unknown.MarkUnknownInteractor;
import use_case.study_deck.next_card.NextCardInputBoundary;
import use_case.study_deck.next_card.NextCardInteractor;
import use_case.study_deck.previous_card.PreviousCardInputBoundary;
import use_case.study_deck.previous_card.PreviousCardInteractor;

public class StudyDeckUseCaseFactory {

    public static class StudyDeckBundle {
        public final StudyDeckViewModel vm;
        public final StudyDeckController controller;

        public StudyDeckBundle(StudyDeckViewModel vm, StudyDeckController controller) {
            this.vm = vm;
            this.controller = controller;
        }
    }

    public static StudyDeckBundle build(DeckDataAccessInterface deckDAO, FileUserDataAccessObject userDAO, FlashcardDataAccessInterface cardDAO) {
        StudyDeckViewModel vm = new StudyDeckViewModel();

        StudyDeckPresenter presenter = new StudyDeckPresenter(vm);

        LoadDeckForStudyInputBoundary loadInteractor = new LoadDeckForStudyInteractor(presenter, deckDAO, cardDAO, userDAO);

        FlipCardInputBoundary flipInteractor = new FlipCardInteractor(cardDAO, presenter);

        NextCardInputBoundary nextInteractor = new NextCardInteractor(cardDAO, presenter);

        PreviousCardInputBoundary prevInteractor =
                new PreviousCardInteractor(cardDAO, presenter);

         MarkKnownInputBoundary markKnownInteractor = new MarkKnownInteractor(cardDAO, userDAO, presenter);

         MarkUnknownInputBoundary markUnknownInteractor = new MarkUnknownInteractor(deckDAO, cardDAO, presenter);

        StudyDeckController controller = new StudyDeckController(loadInteractor, flipInteractor, nextInteractor,
                prevInteractor, markKnownInteractor, markUnknownInteractor
        );

        return new StudyDeckBundle(vm, controller);
    }
}
