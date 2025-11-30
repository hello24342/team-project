package use_case;

import data_access.InMemoryDeckDataAccessObject;
import data_access.InMemoryFlashcardDataAccessObject;
import entity.Flashcard;
import entity.FlashcardDeck;
import org.junit.jupiter.api.Test;
import use_case.deck.open_deck.OpenDeckInputData;
import use_case.deck.open_deck.OpenDeckInteractor;
import use_case.deck.open_deck.OpenDeckOutputBoundary;
import use_case.deck.open_deck.OpenDeckOutputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenDeckInteractorTest {

    /**
     * Presenter stub：
     * record the last normal output & not-found message。
     */
    private static class PresenterStub implements OpenDeckOutputBoundary {
        private OpenDeckOutputData lastOutput;
        private String lastNotFoundMessage;

        @Override
        public void present(OpenDeckOutputData output) {
            this.lastOutput = output;
        }

        @Override
        public void presentNotFound(String message) {
            this.lastNotFoundMessage = message;
        }
    }

    /**
     * branch 1：找不到 deck 的情况。
     * InMemoryDeckDataAccessObject 里没有任何 deck，findById 返回 null，
     * 应该调用 presentNotFound 然后直接 return。
     */
    @Test
    void open_whenDeckNotFound_callsPresentNotFound() {
        InMemoryDeckDataAccessObject deckDAO = new InMemoryDeckDataAccessObject();
        InMemoryFlashcardDataAccessObject cardDAO = new InMemoryFlashcardDataAccessObject();
        PresenterStub presenter = new PresenterStub();

        OpenDeckInteractor interactor =
                new OpenDeckInteractor(deckDAO, cardDAO, presenter);

        int deckId = 99;
        OpenDeckInputData input = new OpenDeckInputData(deckId);

        interactor.open(input);

        assertEquals("Deck not found: " + deckId,
                presenter.lastNotFoundMessage);
        assertNull(presenter.lastOutput);
    }

    /**
     * 分支 2：deck 存在：
     *  - InMemoryDeckDataAccessObject 里 save 了一个 deck
     *  - FlashcardDataAccessObject 里 save 了两张属于该 deck 的卡片
     *  - interactor 应该查到卡片，构造 CardSummary，并调用 present。
     */
    @Test
    void open_whenDeckFound_buildsSummariesAndCallsPresent() {
        InMemoryDeckDataAccessObject deckDAO = new InMemoryDeckDataAccessObject();
        InMemoryFlashcardDataAccessObject cardDAO = new InMemoryFlashcardDataAccessObject();
        PresenterStub presenter = new PresenterStub();

        OpenDeckInteractor interactor =
                new OpenDeckInteractor(deckDAO, cardDAO, presenter);

        int userId = 7;
        int deckId = 1;

        // 准备一个 deck 并保存到 InMemoryDeckDataAccessObject
        FlashcardDeck deck =
                new FlashcardDeck(deckId, "My Deck", userId);
        deckDAO.save(deck);

        // 准备两张卡片，并把它们加入 deckId，再保存到 FlashcardDataAccessObject
        Flashcard card1 =
                new Flashcard(10, "hello", "こんにちは", null, null);
        card1.addDeck(deckId);
        cardDAO.save(card1);

        Flashcard card2 =
                new Flashcard(11, "bye", "さようなら", null, null);
        card2.addDeck(deckId);
        cardDAO.save(card2);

        OpenDeckInputData input = new OpenDeckInputData(deckId);
        interactor.open(input);

        // 不应该走 not-found 分支
        assertNull(presenter.lastNotFoundMessage);

        // 检查输出
        OpenDeckOutputData output = presenter.lastOutput;
        assertNotNull(output);
        assertEquals(deckId, output.getDeckId());
        assertEquals("My Deck", output.getDeckTitle());

        List<OpenDeckOutputData.CardSummary> summaries =
                output.getCards();
        assertEquals(2, summaries.size());

        OpenDeckOutputData.CardSummary first = summaries.get(0);
        assertEquals(10, first.id);
        assertEquals("hello", first.sourceWord);
        assertEquals("こんにちは", first.targetWord);

        OpenDeckOutputData.CardSummary second = summaries.get(1);
        assertEquals(11, second.id);
        assertEquals("bye", second.sourceWord);
        assertEquals("さようなら", second.targetWord);
    }
}
