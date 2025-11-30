package data_access;

import entity.Language;
import use_case.flashcard_create.TranslationException;
import use_case.flashcard_create.TranslatorGateway;

import org.json.JSONArray;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiTranslatorGateway implements TranslatorGateway {

    @Override
    public String translate(String sourceWord, Language sourceLang, Language targetLang) {
        try {
            if (sourceLang.getIsoCode() == null || targetLang.getIsoCode() == null) {
                // Language doesn't have a code yet. Might remove if I have time to
                // verify all languages supported by the API (unlikely)
                throw new TranslationException("Error: No language code for" + sourceLang.getDisplayName());
            }

            String encodedText = URLEncoder.encode(sourceWord, StandardCharsets.UTF_8);

            String url = "https://clients5.google.com/translate_a/t" +
                    "?client=dict-chrome-ex" +
                    "&sl=" + sourceLang.getIsoCode() +
                    "&tl=" + targetLang.getIsoCode() +
                    "&q=" + encodedText;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                // Call went through but returned a failure
                throw new TranslationException("HTTP error: " + response.statusCode());
            }

            JSONArray jsonArray = new JSONArray(response.body());

            if (jsonArray.isEmpty()) {
                // Not a real word, iso code not supported, etc.
                throw new TranslationException("No translation returned.");
            }

            return jsonArray.getString(0);

        } catch (InterruptedException ie) {
            // Interruption error (added to fix SonarQube warning)
            Thread.currentThread().interrupt();
            throw new TranslationException("Process error: Thread interrupted");

        } catch (Exception e) {
            // Everything else
            throw new TranslationException("Process error: " + e.getMessage());
        }
    }
}

//DONE