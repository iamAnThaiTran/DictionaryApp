package com.app.dictionaryapp.BusinessLogicLayer;

import animatefx.animation.*;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import com.app.dictionaryapp.DataAccessLayer.Database;

public class BusinessLogic {

    @FXML
    private AnchorPane displayWord;

    @FXML
    private Button btnDaily;

    @FXML
    private Button btnFavorites;

    @FXML
    private Button btnGames;

    @FXML
    private Button btnRecent;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnVoice;

    @FXML
    private VBox dictionaryDisplay;

    @FXML
    private TextField txtFieldSearch;

    @FXML
    private JFXTextArea textToDisplay;

    @FXML
    private ImageView btnSetting;

    @FXML
    private ScrollPane displaySuggest;


    public String getTextInSearch() {
        return txtFieldSearch.getText();
    }

    private AudioLogic audioUK = new AudioLogic("UK.mp3");
    private AudioLogic audioUS = new AudioLogic("US.mp3");
    private AudioLogic audioTest = new AudioLogic("TestAudio.mp3");
    private SearchLogic searchLogic = new SearchLogic();

    private API api = new API();



    // Search
    @FXML
    void btnSearchAction(ActionEvent event) {
        // get text from TextField Search
        String text = txtFieldSearch.getText();

        // check length of String
        if (text.length() == 0) {
            new Shake(txtFieldSearch).play();
            new Shake(btnSearch).play();
        } else {
            String res = searchLogic.getDetail(text);
            if (res == null) {
                textToDisplay.setText("No Result");
                displayWord.setVisible(false);
            } else {
                displayWord.setVisible(true);
                textToDisplay.setText(res);

                // Sound
                api.textToSpeech(text, "US");
                api.textToSpeech(text, "UK");
            }
        }
    }

    // key event text
    @FXML
    void textKeyEvent(KeyEvent event) {
        String text = txtFieldSearch.getText();

        if (event.getCode() == KeyCode.ENTER) {
            if (text.length() == 0) {
                new Shake(txtFieldSearch).play();
                new Shake(btnSearch).play();
            } else {
                String res = searchLogic.getDetail(text);
                if (res.length() == 0) {
                    textToDisplay.setText("No Result");
                    displayWord.setVisible(false);
                } else {
                    displayWord.setVisible(true);
                    textToDisplay.setText(res);

                    // Sound
                    api.textToSpeech(text, "US");
                    api.textToSpeech(text, "UK");
                }
            }
        }


    }


    // Voice Button
    @FXML
    void btnVoiceAction(ActionEvent event) {

    }

    // Daily Button
    @FXML
    void dailyAction(ActionEvent event) {

    }

    // Favorites Button
    @FXML
    void favoritesAction(ActionEvent event) {

    }

    // Games Button
    @FXML
    void gamesAction(ActionEvent event) {

    }

    // Recent Button
    @FXML
    void recentAction(ActionEvent event) {

    }

    // Setting Button
    @FXML
    void moveSettingBtn(MouseEvent mouseEvent) {

    }

    @FXML
    void clickSettingBtn(MouseEvent mouseEvent) {

    }

    // Sound Button
    @FXML
    void clickBtnUK(MouseEvent event) {
        audioUK.playAudio();
    }

    @FXML
    void clickBtnUS(MouseEvent event) {
        audioUS.playAudio();
    }

    // Star Button
    @FXML
    void clickStar(MouseEvent event) {

    }

}
