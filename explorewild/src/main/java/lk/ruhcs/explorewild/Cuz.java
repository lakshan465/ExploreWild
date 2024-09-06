package lk.ruhcs.explorewild;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Cuz extends Application {

    public static void main(String[] args) {
        launch(args);



    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Cuz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setScene(scene);
        Image icon=new Image("/logo.png");
        stage.getIcons().add(icon);
        stage.show();


        //stage.close();
    }
}
