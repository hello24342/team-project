package usecase.flashcard_create;

import entity.Language;

public class CreateFlashcardOutputData {
    private final int id;
    private final String sourceWord;
    private final String targetWord;
    private final Language sourceLang;
    private final Language targetLang;

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
    }

    public int getId() { return id; }
    public String getSourceWord() { return sourceWord; }
    public String getTargetWord() { return targetWord; }
    public Language getSourceLang() { return sourceLang; }
    public Language getTargetLang() { return targetLang; }
}
