package com.app.dictionaryapp.BusinessLogicLayer;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class APITextToSpeech extends API {
    // Url: https://text-to-speech27.p.rapidapi.com/speech?text=
    // key: fc10970bb6msh2aad50d7bfa8cdap1b6d23jsna6a14e4b2126
    private final static String US = "&lang=en-us";
    private final static String UK = "&lang=en-uk";

    public APITextToSpeech(String APIURL, String APIKey) {
        super(APIURL, APIKey);
    }

    /**
     * textToSpeech
     * @param text : String.
     * @param lang : US Or UK
     */
    public byte[] textToSpeech(String text, String lang) {
        String url = "";

        if(lang.equals("US")) {
            url = getAPIURL() + text + US;
        } else if (lang.equals("UK")) {
            url = getAPIURL() + text + UK;
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", getAPIKey())
                .header("X-RapidAPI-Host", "text-to-speech27.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Loi APITextToSpeech.textToSpeech(): ");
            e.printStackTrace();
            return null;
        }
    }
}
