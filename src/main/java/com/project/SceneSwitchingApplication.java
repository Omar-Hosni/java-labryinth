package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneSwitchingApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
        Parent root = loader.load();
        stage.setTitle("Game");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        //GameController gameController = loader.getController();
        //gameController.initialize();
        //scene.setOnKeyPressed(event->gameController.addKeyPressHandler(event.getCode()));
        //scene.getRoot().requestFocus();

        stage.setScene(scene);
        stage.show();




    }
}
