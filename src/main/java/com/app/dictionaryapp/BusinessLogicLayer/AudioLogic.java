package com.app.dictionaryapp.BusinessLogicLayer;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Files;

public class AudioLogic {
    private final APITextToSpeech apiTextToSpeech = new APITextToSpeech();

    public String setTextFollowFormatRapidApi(String text) {
        return text.replace(" ", "%20");
    }

    /**
     * play audio
     */
    public void playAudio(String text, String lang) {
        text = setTextFollowFormatRapidApi(text);
        byte[] bytes = apiTextToSpeech.textToSpeech(text, lang);
        try {
            File file = File.createTempFile("temp", ".mp3");
            Files.write(file.toPath(), bytes);

            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

        } catch (Exception e) {
            System.out.println("Loi AudioLogic.playAudio(): ");
            e.printStackTrace();
        }
    }

    /**
     * pause audio
     */
    public void pauseAudio() {

    }
}
