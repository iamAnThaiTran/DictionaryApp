package com.app.dictionaryapp.BusinessLogicLayer;

import animatefx.animation.*;
import com.app.dictionaryapp.DataAccessLayer.Database;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.text.Text;

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
    private TextField wordEdit;

    @FXML
    private JFXTextArea textToDisplay;
    @FXML
    private TextArea inputTextTranslation;
    @FXML
    private TextArea descriptionEdit;

    @FXML
    private TextArea outputTextTranslation;

    // image
    @FXML
    private ImageView btnSetting;
    @FXML
    private ImageView btnStarToMark;
    @FXML
    private ImageView btnStarToUnMark;

    @FXML
    private TableView<String> suggestionWordTableView;

    @FXML
    private TableColumn<String, String> suggestionWordCol;

    //label
    @FXML
    private Label word;
    @FXML
    private Label pronunciation;

    // recent logic
    private RecentLogic recentLogic = new RecentLogic();

    // audio logic
    private AudioLogic audioLogic = new AudioLogic();

    // search logic
    private SearchLogic searchLogic = new SearchLogic();

    // edit logic
    private EditLogic editLogic = new EditLogic();

    //SuggestionWord
    private SuggestionWordLogic suggestionWordLogic = new SuggestionWordLogic();

    // TextTranslate
    private APITextTranslate apiTextTranslate = new APITextTranslate();




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
                // display word and pronounce
                word.setText(text);
                pronunciation.setText(searchLogic.getPronounciation(text));
                displayWordSound.setVisible(true);

                // display description
                textToDisplay.setText(res);
                textToDisplay.setVisible(true);

                // add word to recent.txt
                recentLogic.addRecentWord(text);
            }
        }
    }

    // key event text
    @FXML
    void textKeyEvent(KeyEvent event) {
        String text;
        // Search when user enter
        if (editPane.isVisible() || textTranslation.isVisible()) {
            txtFieldSearch.setEditable(false);
        } else {
            txtFieldSearch.setEditable(true);
        }

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
                    // display word and pronounce
                    word.setText(text);
                    pronunciation.setText(searchLogic.getPronounciation(text));
                    displayWordSound.setVisible(true);

                    // display description
                    textToDisplay.setText(res);
                    textToDisplay.setVisible(true);

                    // add word to recent.txt
                    recentLogic.addRecentWord(text);
                }
            }
        } else if (event.getText().length() != 0) {
            suggestionWordTableView.getItems().clear();
            System.out.println(event.getText());
            suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // remove horizontal
            text = txtFieldSearch.getText() + event.getText();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList = suggestionWordLogic.getObservableList(text);
            suggestionWordTableView.setItems(observableList);
            suggestionWordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
            System.out.println(text);
        }
    }

    @FXML
    void clickSuggestionWordTable(MouseEvent event) {
        String text = suggestionWordTableView.getSelectionModel().getSelectedItem();
        txtFieldSearch.setText(text);
    }

    @FXML
    void keyTyped(KeyEvent event) {
//        ObservableList<String> observableList = suggestionWordLogic.getObservableList(txtFieldSearch.getText());
//        suggestionWordListView.setItems(observableList);
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
        outputTextTranslation.setText(apiTextTranslate.translate(text));
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

    // Recent.txt Button
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

    @FXML
    void addBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
        descriptionEdit.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

        } else {
            editLogic.insert(wordEdit.getText(), descriptionEdit.getText());
        }
    }

    @FXML
    void updateBtnEdit(ActionEvent event) {

    }

    @FXML
    void deleteBtnEdit(ActionEvent event) {

    }

    // Sound Button
    @FXML
    void clickBtnUK(MouseEvent event) {
        audioLogic.playAudio(word.getText(), "UK");
    }

    @FXML
    void clickBtnUS(MouseEvent event) {
        audioLogic.playAudio(word.getText(), "US");
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
