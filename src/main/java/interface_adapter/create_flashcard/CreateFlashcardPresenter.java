package interface_adapter.create_flashcard;

import use_case.flashcard.create.CreateFlashcardOutputBoundary;
import use_case.flashcard.create.CreateFlashcardOutputData;

public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {

    private final CreateFlashcardViewModel viewModel;

    public CreateFlashcardPresenter(CreateFlashcardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(CreateFlashcardOutputData data) {
        String msg =
                "Flashcard created!\n" +
                        "ID: " + data.getId() + "\n" +
                        "Source: " + data.getSourceWord() + "\n" +
                        "Translated: " + data.getTargetWord();

        viewModel.setMessage(msg);
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.setMessage("Error: " + errorMessage);
    }
}


