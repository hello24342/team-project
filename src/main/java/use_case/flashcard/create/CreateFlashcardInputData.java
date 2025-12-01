package use_case.flashcard.create;

import entity.Language;

public class CreateFlashcardInputData {
    private final String sourceWord;
    private final Language sourceLang;
    private final Language targetLang;

    public CreateFlashcardInputData(String sourceWord,
                                    Language sourceLang,
                                    Language targetLang) {
        this.sourceWord = sourceWord;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public String getSourceWord() { return sourceWord; }
    public Language getSourceLang() { return sourceLang; }
    public Language getTargetLang() { return targetLang; }

}

//DONE