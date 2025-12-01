package interface_adapter.edit_flashcard;

import use_case.flashcard.edit.EditFlashcardOutputBoundary;
import use_case.flashcard.edit.EditFlashcardOutputData;
// TODO: Hannah pls fix the errors
public class EditFlashcardPresenter implements EditFlashcardOutputBoundary {

    private final EditFlashcardViewModel viewModel;

    public EditFlashcardPresenter(EditFlashcardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(EditFlashcardOutputData outputData) {
        EditFlashcardState state = viewModel.getState();
        state.setFlashcardId(outputData.getFlashcardId());
        state.setMessage(outputData.getMessage());
        state.setErrorMessage(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        EditFlashcardState state = viewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setMessage(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
