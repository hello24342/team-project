package usecase.flashcard_create;

import entity.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateFlashcardOutputData {
    private final int id;
    private final String sourceWord;
    private final String targetWord;
    private final Language sourceLang;
    private final Language targetLang;
    private final List<Integer> deckIds;

    public CreateFlashcardOutputData(int id,
                                        String sourceWord,
                                        String targetWord,
                                        Language sourceLang,
                                        Language targetLang) {
        this.id = id;
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.deckIds = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getSourceWord() { return sourceWord; }
    public String getTargetWord() { return targetWord; }
    public Language getSourceLang() { return sourceLang; }
    public Language getTargetLang() { return targetLang; }

    public void addDeckId(int deckId) { deckIds.add(deckId); }

    public List<Integer> getDeckIds() {
        return Collections.unmodifiableList(deckIds); }

}
