package com.app.dictionaryapp.BusinessLogicLayer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;

public class APITextTranslate extends API {
    // https://text-translator2.p.rapidapi.com/translate
    // 6f22e6bbf4mshc8e808871b5b310p1c99cajsnda97e360477f

    public APITextTranslate(String APIURL, String APIKey) {
        super(APIURL, APIKey);
    }

    public String translate(String text) {
        String json;

        text = text.replace(" ", "%20");
        String textFormat = "source_language=en&target_language=vi&text=" + text;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getAPIURL()))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", getAPIKey())
                .header("X-RapidAPI-Host", "text-translator2.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(textFormat))
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                json = response.body();
                JSONObject jsonObject = new JSONObject(json);
                return jsonObject.getJSONObject("data")
                        .getString("translatedText");
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("Loi APITextTranslate");
            e.printStackTrace();
            return "";
        }
    }
}
