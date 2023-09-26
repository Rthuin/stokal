package com.example.stokal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StokAlApplication extends Application {
    @Override
    public void start(Stage PrimaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StokAlApplication.class.getResource("StokAl.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        PrimaryStage.setResizable(true);
        PrimaryStage.setTitle("Stok Al");
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
