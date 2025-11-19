package data_access;

import entity.Language;

public interface TranslatorGateway {
    String translate(String sourceWord, Language sourceLang, Language targetLang);
}