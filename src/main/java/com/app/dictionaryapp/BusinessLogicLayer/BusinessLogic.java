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
import javafx.scene.control.Alert.AlertType;
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

    // Enum
    private enum MODE {
        RECENT,
        GAMES,
        TEXTTRANSLATION,
        FAVOURITES,
        EDIT
    }

    // mode
    private MODE mode;


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
            // set visible
            suggestionWordTableView.setVisible(true);

            // change tablecol name
            suggestionWordCol.setText("Suggestion Word");

            // clear table view
            suggestionWordTableView.getItems().clear();

            // remove horizontal scroll
            suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // get text
            text = txtFieldSearch.getText() + event.getText();

            // get observable list
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList = suggestionWordLogic.getObservableList(text);

            // set item for tableview
            suggestionWordTableView.setItems(observableList);
            suggestionWordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

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
        textTranslation.setVisible(true);

        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);

        // set disible
        editPane.setVisible(false);
        suggestionWordTableView.setVisible(false);
        displayWordSound.setVisible(false);
        textToDisplay.setVisible(false);
    }
    @FXML
    void translateTextTranslation(ActionEvent event) {
        String text = inputTextTranslation.getText();
        outputTextTranslation.setText(apiTextTranslate.translate(text));
    }
    @FXML
    void close(MouseEvent event) {
        txtFieldSearch.setEditable(true);

        // set visible textTranslation, editPane
        textTranslation.setVisible(false);
        editPane.setVisible(false);
    }

    // Favorites Button
    @FXML
    void favoritesAction(ActionEvent event) {
        suggestionWordTableView.setVisible(true);
        suggestionWordCol.setText("Favourite Word");

        // set visible
        displayWordSound.setVisible(false);
        textToDisplay.setVisible(false);
        textTranslation.setVisible(false);
        editPane.setVisible(false);
    }

    // Games Button
    @FXML
    void gamesAction(ActionEvent event) {

    }

    // Recent.txt Button
    @FXML
    void recentAction(ActionEvent event) {
        suggestionWordTableView.setVisible(true);
        suggestionWordCol.setText("Recent Word");

        // set visible
        displayWordSound.setVisible(false);
        textToDisplay.setVisible(false);
        textTranslation.setVisible(false);
        editPane.setVisible(false);
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
        editPane.setVisible(true);

        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);

        // set disible
        textTranslation.setVisible(false);
        suggestionWordTableView.setVisible(false);
        displayWordSound.setVisible(false);
        textToDisplay.setVisible(false);
    }

    @FXML
    void addBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
        descriptionEdit.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error add new word");
            alert.setContentText("Word and description is required");

            alert.showAndWait();
        } else {
            editLogic.insert(wordEdit.getText(), descriptionEdit.getText());
        }
    }

    @FXML
    void updateBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
            descriptionEdit.getText().length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error update new word");
            alert.setContentText("Word and description is required");

            alert.showAndWait();
        } else {

        }
    }

    @FXML
    void deleteBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error delete word");
            alert.setContentText("Word is required");

            alert.showAndWait();
        } else {

        }
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
