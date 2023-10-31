package com.app.dictionaryapp.BusinessLogicLayer;

import animatefx.animation.*;
import com.app.dictionaryapp.DataAccessLayer.Database;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class BusinessLogic {

    // Pane
    @FXML
    private AnchorPane introPane;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane displayWordSound;
    @FXML
    private AnchorPane textTranslation;
    @FXML
    private AnchorPane editPane;
    @FXML
    private ScrollPane displaySuggest;


    // Button
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


    // vbox
    @FXML
    private VBox dictionaryDisplay;

    // text field
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private JFXTextArea textToDisplay;

    // image
    @FXML
    private ImageView btnSetting;
    @FXML
    private ImageView btnStarToMark;
    @FXML
    private ImageView btnStarToUnMark;

    @FXML
    private TableView suggestionWordTableView;

    @FXML
    private TableColumn suggestionWordCol;


    // audio
    private AudioLogic audioLogic = new AudioLogic();

    // search
    private SearchLogic searchLogic = new SearchLogic();

    //SuggestionWord
    private SuggestionWordLogic suggestionWordLogic = new SuggestionWordLogic();

    // TextTranslate
    private APITextTranslate apiTextTranslate = new APITextTranslate();
    @FXML
    private JFXTextArea inputTextTranslation;

    @FXML
    private JFXTextArea displayTextTranslation;


    // StartNow Action
    @FXML
    void startNowAction(ActionEvent event) {
        introPane.setVisible(false);
    }

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
            if (res.length() == 0) {
                textToDisplay.setText("No Result");
                displayWordSound.setVisible(false);
            } else {
                displayWordSound.setVisible(true);
                textToDisplay.setText(res);
            }
        }
    }

    // key event text
    @FXML
    void textKeyEvent(KeyEvent event) {
        String text;
        // Search when user enter
        if (event.getCode() == KeyCode.ENTER) {
            text = txtFieldSearch.getText();

            if (text.length() == 0) {
                new Shake(txtFieldSearch).play();
                new Shake(btnSearch).play();
            } else {
                String res = searchLogic.getDetail(text);
                if (res.length() == 0) {
                    textToDisplay.setText("No Result");
                    displayWordSound.setVisible(false);
                } else {
                    displayWordSound.setVisible(true);
                    textToDisplay.setText(res);
                }
            }
        } else if (event.getText().length() != 0){
            text = txtFieldSearch.getText() + event.getText();
            System.out.println(text);
        }
    }

    // Text Translation Button
    @FXML
    void textTranslationAction(ActionEvent event) {
        if (!editPane.isVisible()) {
            textTranslation.setVisible(true);
        }
    }
    @FXML
    void translateTextTranslation(ActionEvent event) {
        String text = inputTextTranslation.getText();
        displayTextTranslation.setText(apiTextTranslate.translate(text));
    }
    @FXML
    void close(MouseEvent event) {
        textTranslation.setVisible(false);
        editPane.setVisible(false);
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

    // Edit Button
    @FXML
    void btnEditAction(ActionEvent event) {
        if (!textTranslation.isVisible()) {
            editPane.setVisible(true);
        }
    }

    // Sound Button
    @FXML
    void clickBtnUK(MouseEvent event) {
        audioLogic.playAudio(txtFieldSearch.getText(), "UK");
    }

    @FXML
    void clickBtnUS(MouseEvent event) {
        audioLogic.playAudio(txtFieldSearch.getText(), "US");
    }

    // Star Button
    @FXML
    void clickStarToMark(MouseEvent event) {
        btnStarToMark.setVisible(false);
        btnStarToUnMark.setVisible(true);
    }

    @FXML
    void clickStarToUnMark(MouseEvent event) {
        btnStarToUnMark.setVisible(false);
        btnStarToMark.setVisible(true);
    }

}
