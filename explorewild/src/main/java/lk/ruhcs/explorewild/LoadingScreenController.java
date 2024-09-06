package lk.ruhcs.explorewild;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class LoadingScreenController {

    @FXML
    private Label loadingPercentage;

    @FXML
    private Rectangle recMain;

    @FXML
    private Rectangle recSub;


    public void initialize() {

        LoadingTask task=new LoadingTask();

        task.progressProperty().addListener((obv, ov, nw) -> {

            String fs=String.format("%.0f", nw.doubleValue()*100);
            //System.out.println(fs);
            loadingPercentage.setText(fs+"%");

            recSub.setWidth(recMain.getWidth()*nw.doubleValue());

            if(nw.doubleValue()==1.0){
                Window window=loadingPercentage.getScene().getWindow();
                Stage s=(Stage) window;
                s.close();

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/lk.ruhcs.explorewild/login.fxml"));
                Scene scene=null;
                try {
                    scene = new Scene(fxmlLoader.load(), 600, 400);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage=new Stage();
                stage.setScene(scene);
                //stage.initStyle(StageStyle.UNDECORATED);


                stage.show();

            }


        });

        new Thread(task).start();
    }




}
