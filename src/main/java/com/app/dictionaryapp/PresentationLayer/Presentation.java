package com.app.dictionaryapp.PresentationLayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Presentation extends Application {
    private FXMLLoader fxmlLoader;
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        // load file index.fxml
        fxmlLoader = new FXMLLoader(Presentation.class.getResource("index.fxml"));

        // init scene from fxml file
        scene = new Scene(fxmlLoader.load());

        // set title
        stage.setTitle("DictionaryApp");

        // set scene for stage
        stage.setScene(scene);

        // show
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
