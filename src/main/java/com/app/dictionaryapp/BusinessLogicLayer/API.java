package com.app.dictionaryapp.BusinessLogicLayer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class API {
    // APIURL
    private final static String APIURL = "https://text-to-speech27.p.rapidapi.com/speech?text=";

    // APIKEY
    private final static String APIKEY = "a2b711a3efmsh50240a86d75d255p19ed2fjsnf42d2dfee495";

    private final static String US = "&lang=en-us";
    private final static String UK = "&lang=en-uk";

    private final static String urlUS = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Audio/US.mp3";
    private final static String urlUK = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Audio/UK.mp3";

    /**
     * textToSpeech
     * @param text : String.
     * @param lang : US Or UK
     */
    public void textToSpeech(String text, String lang) {
        String url = "";
        String pathFileAudio = "";

        if(lang.equals("US")) {
            url = APIURL + text + US;
            pathFileAudio = urlUS;
        } else if (lang.equals("UK")) {
            url = APIURL + text + UK;
            pathFileAudio = urlUK;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", "text-to-speech27.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == 200) {
                InputStream inputDataAudio = response.body();
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pathFileAudio, true))) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputDataAudio.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Audio data has been appended to " + pathFileAudio);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
