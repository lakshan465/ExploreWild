package lk.ruhcs.explorewild;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //should uncomment after testing
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoadingScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 525, 360);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon=new Image("/logo.png");
        stage.getIcons().add(icon);

        stage.show();
        //stage.setResizable(false);

        //teparrly code for testing Strat
//        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Cuz.fxml"));
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