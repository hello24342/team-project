package entity;

import java.util.ArrayList;
import java.util.List;

public class Flashcard {
    private final int id;
    private String sourceWord;
    private String targetWord;
    private Language sourceLang;
    private Language targetLang;
    private boolean known;
    private List<Integer> deckIds;

    public Flashcard(int id, String sourceWord, String targetWord, Language sourceLang, Language targetLang) {
        this.id = id;
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.deckIds = new ArrayList<>();
        this.known = false;
    }

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    public String getSourceWord() {
        return sourceWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public void setSourceLang(Language sourceLang) {
        this.sourceLang = sourceLang;
    }

    public void setTargetLang(Language targetLang) {
        this.targetLang = targetLang;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public boolean isKnown() {
        return this.known;
    }

    public void addDeck(Integer deckId) {
        if (!deckIds.contains(deckId)) {
            deckIds.add(deckId);
        }
    }

    public void removeDeck(Integer deckId) {
        deckIds.remove(deckId);
    }

    public List<Integer> getDeckIds() {
        return deckIds;
    }
    public Language getSourceLang() {
        return sourceLang;
    }
    public Language getTargetLang() {
        return targetLang;
    }

    public int getId() {
        return id;
    }
}
