package interface_adapter.deck;

import use_case.deck.open_deck.OpenDeckOutputBoundary;
import use_case.deck.open_deck.OpenDeckOutputData;

import java.util.ArrayList;
import java.util.List;

public class OpenDeckPresenter implements OpenDeckOutputBoundary {

    private final DeckDetailViewModel vm;

    public OpenDeckPresenter(DeckDetailViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(OpenDeckOutputData output) {
        List<DeckDetailViewModel.CardVM> vms = new ArrayList<>();
        for (OpenDeckOutputData.CardSummary s : output.getCards()) {
            vms.add(new DeckDetailViewModel.CardVM(s.id, s.sourceWord, s.targetWord));
        }
        vm.setError(null);
        vm.setDetail(output.getDeckId(), output.getDeckTitle(), vms);
    }

    @Override
    public void presentNotFound(String message) {
        vm.setError(message);
    }
}
