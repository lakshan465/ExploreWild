package com.example.demo3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //should uncomment after testing
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        //teparrly code for testing Strat
//        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/example/demo3/Admin.fxml"));
//
//        Scene scene2 = new Scene(fxmlLoader2.load(), 862, 560);
//        Stage stage2 = new Stage();
//
//        stage2.setScene(scene2);
//        stage2.show();
//        stage2.setResizable(false);
        //teparrly code for testing END

    }

    public static void main(String[] args) {
        launch();
    }
}