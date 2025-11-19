package data_access;

import entity.Language;

public class ApiTranslatorGateway implements TranslatorGateway {

    @Override
    public String translate(String sourceWord, Language sourceLang, Language targetLanguage) {
        // TODO: OkHttp request to Google API once we figure it out.
        return "Not Implemented";
    }
}
