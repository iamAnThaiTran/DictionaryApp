package com.app.dictionaryapp.BusinessLogicLayer;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class APITextToSpeech {
    private final API api =  new API("https://text-to-speech27.p.rapidapi.com/speech?text=",
                                "fc10970bb6msh2aad50d7bfa8cdap1b6d23jsna6a14e4b2126");

    private final static String US = "&lang=en-us";
    private final static String UK = "&lang=en-uk";

    /**
     * textToSpeech
     * @param text : String.
     * @param lang : US Or UK
     */
    public byte[] textToSpeech(String text, String lang) {
        String url = "";

        if(lang.equals("US")) {
            url = api.getAPIURL() + text + US;
        } else if (lang.equals("UK")) {
            url = api.getAPIURL() + text + UK;
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", api.getAPIKey())
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
