package interface_adapter.study_deck;

import usecase.study_deck.flip_card.FlipCardOutputBoundary;
import usecase.study_deck.flip_card.FlipCardOutputData;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyOutputBoundary;
import usecase.study_deck.load_deck_for_study.LoadDeckForStudyOutputData;
import usecase.study_deck.mark_known.MarkKnownOutputBoundary;
import usecase.study_deck.mark_known.MarkKnownOutputData;
import usecase.study_deck.mark_unknown.MarkUnknownOutputData;
import usecase.study_deck.mark_unknown.MarkUnknownOutputBoundary;
import usecase.study_deck.next_card.NextCardOutputBoundary;
import usecase.study_deck.next_card.NextCardOutputData;
import usecase.study_deck.previous_card.PreviousCardOutputBoundary;
import usecase.study_deck.previous_card.PreviousCardOutputData;

public class StudyDeckPresenter implements FlipCardOutputBoundary, LoadDeckForStudyOutputBoundary,
        NextCardOutputBoundary, PreviousCardOutputBoundary, MarkKnownOutputBoundary, MarkUnknownOutputBoundary {
    private final StudyDeckViewModel viewModel;

    public StudyDeckPresenter(StudyDeckViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccessView(FlipCardOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();

        studyDeckState.setShowingFront(outputData.isShowingFrontNow());
        studyDeckState.setCardText(outputData.getTextToShow());

        viewModel.firePropertyChange();
    }

    @Override
    public void presentSuccessView(LoadDeckForStudyOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();

        studyDeckState.setDeckName(outputData.getDeckTitle());
        studyDeckState.setCardText(outputData.getSourceWord());
        studyDeckState.setCardIndex(0);
        studyDeckState.setShowingFront(true);

        viewModel.setState(studyDeckState);
        viewModel.firePropertyChange();
    }

    @Override
    public void presentSuccessView(MarkKnownOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();
        // will be written after interactor is implemented
    }

    @Override
    public void presentSuccessView(MarkUnknownOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();
        // will be written after interactor is implemented
    }


    @Override
    public void presentSuccessView(NextCardOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();

        boolean startsWithSource = studyDeckState.startsWithSource();
        String textToShow = startsWithSource
                ? outputData.getSourceWord()
                : outputData.getTargetWord();

        studyDeckState.setCardIndex(outputData.getCardIndex());
        studyDeckState.setCardText(textToShow);
        studyDeckState.setShowingFront(true);

        viewModel.firePropertyChange();
    }

    @Override
    public void presentSuccessView(PreviousCardOutputData outputData) {
        final StudyDeckState studyDeckState = viewModel.getState();

        boolean startsWithSource = studyDeckState.startsWithSource();
        String textToShow = startsWithSource ? outputData.getSourceWord() : outputData.getTargetWord();
        studyDeckState.setCardIndex(outputData.getCardIndex());
        studyDeckState.setCardText(textToShow);
        studyDeckState.setShowingFront(true);

        viewModel.firePropertyChange();
    }

    @Override
    public void presentFailureView(String errorMessage) {
    }
}
