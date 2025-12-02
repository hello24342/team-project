package app.factory;

import interface_adapter.edit_flashcard.EditFlashcardController;
import interface_adapter.edit_flashcard.EditFlashcardPresenter;
import interface_adapter.edit_flashcard.EditFlashcardViewModel;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.flashcard.edit.EditFlashcardInputBoundary;
import use_case.flashcard.edit.EditFlashcardInteractor;
import use_case.flashcard.edit.EditFlashcardOutputBoundary;

public class EditFlashcardUseCaseFactory {

    public static class EditFlashcardBundle {
        public final EditFlashcardViewModel vm;
        public final EditFlashcardController controller;

        public EditFlashcardBundle(EditFlashcardViewModel vm,
                                   EditFlashcardController controller) {
            this.vm = vm;
            this.controller = controller;
        }
    }

//    public static EditFlashcardBundle build(FlashcardDataAccessInterface cardDAO) {
//        EditFlashcardViewModel vm = new EditFlashcardViewModel();
//
//        EditFlashcardOutputBoundary presenter = new EditFlashcardPresenter(vm);
//
//        EditFlashcardInputBoundary interactor =
//                new EditFlashcardInteractor(cardDAO, presenter);
//
//        EditFlashcardController controller =
//                new EditFlashcardController(interactor, vm);
//
//        return new EditFlashcardBundle(vm, controller);
//    }
}
