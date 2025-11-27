package usecase.flashcard_create;

import entity.Language;

public interface TranslatorGateway {
    String translate(String sourceWord, Language sourceLang, Language targetLang);
}