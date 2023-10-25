package com.app.dictionaryapp.BusinessLogicLayer;

import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.cell.MediaImageCell;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import javafx.scene.media.Media;

public class AudioLogic {
    private File file;
    private Timer timer;
    private MediaPlayer mediaPlayer;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 200};
    private Media media;

    /**
     * set speed
     * @param value : int (%)
     */
    private void setSpeedSpeaker(int value) {
        mediaPlayer.setRate(value * 0.01);
    }

    /**
     * Constructor AudioLogic
     * @param nameAudio : String.
     */
    public AudioLogic(String nameAudio) {
        String path = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Audio/" + nameAudio;
        try {
            file = new File(path);
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * play audio
     */
    public void playAudio() {
        mediaPlayer.play();
    }

    /**
     * pause audio
     */
    public void pauseAudio() {
        mediaPlayer.pause();
    }

}
