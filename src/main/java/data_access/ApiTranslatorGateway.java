package data_access;

import entity.Language;
import usecase.flashcard_create.TranslationException;
import usecase.flashcard_create.TranslatorGateway;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiTranslatorGateway implements TranslatorGateway {

    @Override
    public String translate(String sourceWord, Language sourceLang, Language targetLang) {
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
            throw new TranslationException("API error: " + response.statusCode());
        }

        JSONObject json = new JSONObject(response.body());
        JSONArray sentences = json.getJSONArray("sentences");

        if (sentences.length() == 0){
            throw new TranslationException("No translation returned.");
        }

        return sentences.getJSONObject(0).getString("trans");

