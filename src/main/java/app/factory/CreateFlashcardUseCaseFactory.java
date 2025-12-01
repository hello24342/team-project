package app.factory;

import data_access.ApiTranslatorGateway;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardPresenter;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.flashcard.create.*;

public class CreateFlashcardUseCaseFactory {

    public static class CreateFlashcardBundle {
        public final CreateFlashcardViewModel vm;
        public final CreateFlashcardController controller;

        public CreateFlashcardBundle(CreateFlashcardViewModel vm,
                                     CreateFlashcardController controller) {
            this.vm = vm;
            this.controller = controller;
        }
    }

    public static CreateFlashcardBundle build(FlashcardDataAccessInterface cardDAO) {

        CreateFlashcardViewModel vm = new CreateFlashcardViewModel();
        CreateFlashcardPresenter presenter = new CreateFlashcardPresenter(vm);

        TranslatorGateway translator = new ApiTranslatorGateway();

        CreateFlashcardInputBoundary interactor =
                new CreateFlashcardInteractor(translator, cardDAO, presenter);

        CreateFlashcardController controller =
                new CreateFlashcardController(interactor);

        return new CreateFlashcardBundle(vm, controller);
    }
}
