package interface_adapter.deck;

import use_case.deck.create_deck.CreateDeckOutputBoundary;
import use_case.deck.create_deck.CreateDeckOutputData;

import java.util.ArrayList;
import java.util.List;

public class CreateDeckPresenter implements CreateDeckOutputBoundary {

    private final DeckMenuViewModel vm;

    public CreateDeckPresenter(DeckMenuViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void prepareSuccess(CreateDeckOutputData output) {
        List<DeckMenuViewModel.DeckTileVM> tiles = new ArrayList<>();
        for (CreateDeckOutputData.DeckSummary s : output.getDeckSummaries()) {
            tiles.add(new DeckMenuViewModel.DeckTileVM(s.deckId, s.title));
        }
        vm.setErrorMessage(null);
        vm.setTiles(tiles);
    }

    @Override
    public void prepareFail(String errorMessage) {
        vm.setErrorMessage(errorMessage);
    }
}
