package com.example.comp228lab5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerGameApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PlayerGameView.fxml"));
        primaryStage.setTitle("Player and Game Management");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}

