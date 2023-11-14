package com.app.dictionaryapp.BusinessLogicLayer;
import com.app.dictionaryapp.DataAccessLayer.Object;
import animatefx.animation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;

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
    @FXML
    private AnchorPane settingPane;
    @FXML
    private AnchorPane miniSettingPane;
    // Button
    @FXML
    private Button btnStart;
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
    private Button btnTextTranslation;
    @FXML
    private JFXToggleButton btnLang;
    @FXML
    private JFXToggleButton btnThemes;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnTextTranslate;
    // vbox
    @FXML
    private VBox dictionaryDisplay;

    // text field
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private TextField wordEdit;

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
    private ImageView download;
    @FXML
    private ImageView downloadClick;
    @FXML
    private ImageView btnUS;
    @FXML
    private ImageView btnUK;
    @FXML
    private ImageView btnTranslate;
    @FXML
    private ImageView WhiteThemes1;
    @FXML
    private ImageView WhiteThemes2;

    @FXML
    private TableView<String> suggestionWordTableView;

    @FXML
    private TableColumn<String, String> suggestionWordCol;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;

    //label
    @FXML
    private Label word;
    @FXML
    private Label pronunciation;
    @FXML
    private Label labelThemes;
    @FXML
    private Label labelLang;
    @FXML
    private Label labelEdit;
    @FXML
    private Label labelWord;
    @FXML
    private Label labelDetail;
    @FXML
    private Label labelTextTranslation;
    // recent logic
    private final RecentLogic recentLogic = new RecentLogic();

    // favorites logic
    private final FavoritesLogic favoritesLogic = new FavoritesLogic();

    // audio logic
    private final AudioLogic audioLogic = new AudioLogic();

    // search logic
    private final SearchLogic searchLogic = new SearchLogic();

    // edit logic
    private final EditLogic editLogic = new EditLogic();

    //SuggestionWord
    private final SuggestionWordLogic suggestionWordLogic = new SuggestionWordLogic();

    // TextTranslate
    private final APITextTranslate apiTextTranslate = new APITextTranslate();

    // SaveOrRead
    private final Save_Or_Read saveOrRead = new Save_Or_Read();

    // Address and variable
    private final Object object = new Object();
    // Enum
    private enum MODE {
        SEARCH,
        RECENT,
        GAMES,
        TEXTTRANSLATION,
        FAVOURITES,
        EDIT
    }


    private MODE mode = MODE.SEARCH;
    // mode

    //private ObservableList<String> observableList = FXCollections.observableArrayList();

    // StartNow Action
    @FXML
    void startNowAction(ActionEvent event) {
        introPane.setVisible(false);
    }

    // Search
    @FXML
    void btnSearchAction(ActionEvent event) {
        //if (mode == MODE.SEARCH)
        {
            // get text from TextField Search
            String text = txtFieldSearch.getText().toLowerCase();

            // check length of String
            if (text.length() == 0) {
                new Shake(txtFieldSearch).play();
                new Shake(btnSearch).play();
            } else {
//                text = upperCaseFirstLetter(text);

                String res = searchLogic.getHtml(text);
                if (res.length() == 0) {
                    // webView
                    webView.setVisible(true);
                    loadCssForWebView();
                    webEngine.loadContent("No result!");

                    displayWordSound.setVisible(false);
                } else {
                    // display word and pronounce
                    word.setText(text);
                    pronunciation.setText(searchLogic.getPronounciation(text));
                    displayWordSound.setVisible(true);

                    // webview
                    loadCssForWebView();
                    webView.setVisible(true);
                    webEngine.loadContent(res);

                    // add word to recent.txt
                    recentLogic.addRecentWord(text);

                    //Table View.
                    //updateTableView(suggestionWordLogic.getObservableList(text), "Suggestion Word");

                    // favourites
                    if (favoritesLogic.checkTextInFavouriteFile(text)) {
                        btnStarToMark.setVisible(false);
                        btnStarToUnMark.setVisible(true);
                    }

                    // add word to history.txt
                    saveOrRead.setWord(text, object.getSearchWord_directory());
                }
            }
        }
    }

    // key event text
    @FXML
    void textKeyEvent(KeyEvent event) {
        if (editPane.isVisible() || textTranslation.isVisible()) {
            txtFieldSearch.setEditable(false);
        } else {
            txtFieldSearch.setEditable(true);
        }
        if (mode.equals(MODE.SEARCH)) {
            if (event.getCode() == KeyCode.ENTER) {
                String text = txtFieldSearch.getText();

                if (text.length() == 0) {
                    new Shake(txtFieldSearch).play();
                    new Shake(btnSearch).play();
                } else {
                    String res = searchLogic.getHtml(text);
                    if (res.length() == 0) {
                        // webview
                        loadCssForWebView();
                        webView.setVisible(true);
                        webView.getEngine().loadContent("No result!");
                        displayWordSound.setVisible(false);
                    } else  {
                        // display word and pronounce
                        word.setText(text);
                        pronunciation.setText(searchLogic.getPronounciation(text));
                        displayWordSound.setVisible(true);

                        word.setText(text);
                        pronunciation.setText(searchLogic.getPronounciation(text));
                        displayWordSound.setVisible(true);

                        // webview
                        loadCssForWebView();
                        webView.setVisible(true);
                        webEngine.loadContent(res);

                        // add word to recent.txt
                        recentLogic.addRecentWord(text);
                    }
                }
            } else if (event.getText().length() != 0) {
                suggestionWordTableView.getItems().clear();
                //System.out.println(event.getText());
                suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // remove horizontal
                String text = txtFieldSearch.getText();// + event.getText();
                ObservableList<String> observableList = FXCollections.observableArrayList();
                observableList = suggestionWordLogic.getObservableList(text);
                suggestionWordTableView.setItems(observableList);
                suggestionWordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
            }
        }
    }

    @FXML
    void clickSuggestionWordTable(MouseEvent event) {
        String text = suggestionWordTableView.getSelectionModel().getSelectedItem();
        txtFieldSearch.setText(text);
    }

    // Text Translation Button
    @FXML
    void textTranslationAction(ActionEvent event) {
        mode = MODE.TEXTTRANSLATION;
        textTranslation.setVisible(true);
        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);
        txtFieldSearch.setText("");
//        // set visible false
//        editPane.setVisible(false);
//        suggestionWordTableView.setVisible(false);
//        displayWordSound.setVisible(false);
//        webView.setVisible(false);

        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, editPane, suggestionWordTableView, displayWordSound, webView);
        setVisibleFalse(nodes);
    }
    @FXML
    void translateTextTranslation(ActionEvent event) {
        String text = inputTextTranslation.getText();
        outputTextTranslation.setText(apiTextTranslate.translate(text));
    }
    @FXML
    void close(MouseEvent event) {
        mode = MODE.SEARCH;

        // set visible false
        textTranslation.setVisible(false);
        editPane.setVisible(false);

        // set editable
        txtFieldSearch.setEditable(true);
        suggestionWordTableView.setVisible(true);
    }

    // Favorites Button
    @FXML
    void favoritesAction(ActionEvent event) {
        mode = MODE.SEARCH;
        if(btnLang.isSelected()) updateTableView(favoritesLogic.getContentInFavourite(),"Từ yêu thích");
        else updateTableView(favoritesLogic.getContentInFavourite(), "Favourite Word");

        // set editable
        txtFieldSearch.setEditable(true);

//        // set visible false
//        displayWordSound.setVisible(false);
//        webView.setVisible(false);
//        textTranslation.setVisible(false);
//        editPane.setVisible(false);

        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, displayWordSound, webView, textTranslation, editPane);
        setVisibleFalse(nodes);
    }

    // Games Button
    @FXML
    void gamesAction(ActionEvent event) {

    }

    // Recent.txt Button
    @FXML
    void recentAction(ActionEvent event) {
        mode = MODE.SEARCH;

        if(!btnLang.isSelected()) updateTableView(recentLogic.getContentRecent(), "Recent Word");
        else updateTableView(recentLogic.getContentRecent(),"Từ gần đây");
        // set txtFieldSearch
        txtFieldSearch.setText("");
        txtFieldSearch.setEditable(true);

//        // set visible false
//        displayWordSound.setVisible(false);
//        webView.setVisible(false);
//        textTranslation.setVisible(false);
//        editPane.setVisible(false);
        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, displayWordSound, webView, textTranslation, editPane);
        setVisibleFalse(nodes);
    }

    // Setting Button
    @FXML
    void moveSettingBtn(MouseEvent mouseEvent) {
        miniSettingPane.setVisible(true);
    }
    @FXML
    void exitSettingBtn(MouseEvent mouseEvent) {
        miniSettingPane.setVisible(false);
    }

    @FXML
    void clickSettingBtn(MouseEvent mouseEvent) {
        settingPane.setVisible(true);
    }

    // Edit Button
    @FXML
    void btnEditAction(ActionEvent event) {
        mode = MODE.EDIT;

        editPane.setVisible(true);
        miniSettingPane.setVisible(false);
        settingPane.setVisible(false);
        suggestionWordTableView.setVisible(false);
        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);
        txtFieldSearch.setText("");

        // set visible false
//        textTranslation.setVisible(false);
//        suggestionWordTableView.setVisible(false);
//        displayWordSound.setVisible(false);
//        webView.setVisible(false);
        List<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, textTranslation, suggestionWordTableView, displayWordSound, webView);
        setVisibleFalse(nodeList);

    }

    @FXML
    void addBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
        descriptionEdit.getText().length() == 0) {
            showAlert("Error add new word", "", AlertType.ERROR);
        } else {
            if (editLogic.insert(upperCaseFirstLetter(wordEdit.getText().toLowerCase())
                , upperCaseFirstLetter(descriptionEdit.getText()))) {
                showAlert("Add new word successfully", "", AlertType.INFORMATION);
            } else {
                showAlert("Error add new word", "Word Had In Your Dictionary", AlertType.ERROR);
            }
        }
    }

    @FXML
    void updateBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
            descriptionEdit.getText().length() == 0) {
            showAlert("Error update new word", "Word and description is required", AlertType.ERROR);
        } else {
            editLogic.update(upperCaseFirstLetter(wordEdit.getText().toLowerCase()),
                upperCaseFirstLetter(descriptionEdit.getText().toLowerCase()));
                showAlert("Update new word successfully", "", AlertType.INFORMATION);

        }
    }

    @FXML
    void deleteBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0) {
            showAlert("Error delete word", "Word is required", AlertType.ERROR);
        } else {
            if (editLogic.delete(upperCaseFirstLetter(wordEdit.getText().toLowerCase()))) {
                showAlert("Delete word successfully", "", AlertType.INFORMATION);
            } else {
                showAlert("Error delete word", "No Word In Your Dictionary", AlertType.ERROR);
            }
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

        favoritesLogic.addTextToFavorite(upperCaseFirstLetter(txtFieldSearch.getText()));

    }

    @FXML
    void clickStarToUnMark(MouseEvent event) {
        btnStarToUnMark.setVisible(false);
        btnStarToMark.setVisible(true);

        favoritesLogic.deleteTextInFavorite(upperCaseFirstLetter(txtFieldSearch.getText()));
    }

    // Download Button

    @FXML
    void clickDownload(MouseEvent event) {
        download.setVisible(false);
        downloadClick.setVisible(true);

        String html = searchLogic.getHtml(txtFieldSearch.getText());
        String htmlToPlainText = Jsoup.parse(html).wholeText();

    }

    @FXML
    void clickDownloadClick(MouseEvent event) {
        downloadClick.setVisible(false);
        download.setVisible(true);
    }

    void loadCssForWebView() {
        webEngine = webView.getEngine();
        String path = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/StyleWebView.css").toExternalForm();
        webEngine.setUserStyleSheetLocation(path);
    }


    String upperCaseFirstLetter(String text) {
        String firstText = text.substring(0, 1).toUpperCase();
        String remainText = text.substring(1);

        return firstText + remainText;
    }

    void updateTableView(ObservableList<String> observableList, String title) {
        suggestionWordTableView.getItems().clear();
        suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        suggestionWordTableView.setItems(observableList);
        suggestionWordCol.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue()));
        suggestionWordCol.setText(title);
        suggestionWordTableView.setVisible(true);
    }

    void setVisibleFalse(List<Node> listNode) {
        for (Node node : listNode) {
            node.setVisible(false);
        }
    }

    void showAlert(String title, String contentText, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void closeSettingPane(MouseEvent event) {
        settingPane.setVisible(false);
        suggestionWordTableView.setVisible(true);
    }
    @FXML
    void changeTranslationSetting(MouseEvent event) {
        if (btnLang.isSelected()) {
            btnRecent.setText("Gần đây");
            btnGames.setText("Trò chơi");
            btnTextTranslation.setText("Văn bản");
            btnFavorites.setText("Yêu thích");
            btnEdit.setText("Chỉnh sửa");
            btnUS.setAccessibleText("Anh Mỹ");
            btnUK.setAccessibleText("Anh Anh");
            btnTextTranslation.setText("Dịch");
            //btnStart.setText("Báº¯t Ä‘áº§u");
            suggestionWordCol.setText("Từ gợi ý");
            labelLang.setText("Ngôn ngữ");
            btnLang.setText("Tiếng Anh");
            labelThemes.setText("Chế độ màn hình");
            btnThemes.setText("Tối");
            btnTextTranslate.setText("Dịch");
            labelTextTranslation.setText("Dịch văn bản");
        }
        else if(!btnLang.isSelected())
        {
            btnRecent.setText("Recent");
            btnGames.setText("Game");
            btnTextTranslation.setText("Text Translation");
            btnFavorites.setText("Favourite");
            btnEdit.setText("Edit");
            btnUS.setAccessibleText("US");
            btnUK.setAccessibleText("UK");
            btnTextTranslation.setText("Translate");
           // btnStart.setText("Start");
            suggestionWordCol.setText("Suggestion Word");
            labelLang.setText("Language");
            btnLang.setText("Vietnamese");
            labelThemes.setText("Themes");
            btnThemes.setText("Dark");
            btnTextTranslation.setText("Translate");
            labelTextTranslation.setText("Text Translation");
            inputTextTranslation.setPromptText("Đoạn văn bản cần dịch");
            outputTextTranslation.setPromptText("Đoạn văn bản đã dịch");
            inputTextTranslation.setPromptText("Input");
            outputTextTranslation.setPromptText("Output");
        }
        if(btnThemes.isSelected())
        {
            WhiteThemes1.setVisible(true);
            WhiteThemes2.setVisible(true);
            mainPane.getStylesheets().remove(object.getDarkMode());
            mainPane.getStylesheets().add(object.getLightMode());
        }
        else {
            mainPane.getStylesheets().remove(object.getLightMode());
            mainPane.getStylesheets().add(object.getDarkMode());
            WhiteThemes1.setVisible(false);
            WhiteThemes2.setVisible(false);
        }
    }
    public static void main(String[] args) {
    }
}
