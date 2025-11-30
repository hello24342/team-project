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

    /**
     * Add a deck to the list of decks this flashcard belongs to.
     * @param deckId the ID of the deck to add
     */
    public void addDeck(Integer deckId) {
        if (!deckIds.contains(deckId)) {
            deckIds.add(deckId);
        }
    }

    /**
     * Remove a deck from the list of decks this flashcard belongs to.
     * @param deckId the ID of the deck to remove
     */
    public void removeDeck(Integer deckId) {
        deckIds.remove(deckId);
    }

    public List<Integer> getDeckIds() {
        return deckIds;
    }

    /**
     * Get the language of the source word.
     * @return the language of the source word
     */
    public Language getSourceLang() {
        return sourceLang;
    }

    /**
     * Get the language of the target word.
     * @return the language of the target word
     */
    public Language getTargetLang() {
        return targetLang;
    }

    public int getId() {
        return id;
    }
}
