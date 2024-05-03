package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class LiveFeedController {

    @FXML
    private Button exitBtn;

    @FXML
    private WebView webView;

    @FXML
    void exit(ActionEvent event) {

        //exitBtn.setOnAction(e -> stopVideo(webView));

        stopVideo(webView);

        Stage window=(Stage)webView.getScene().getWindow();
        window.close();

    }

    @FXML
    void play(ActionEvent event) {
        webView.getEngine().load("https://www.youtube.com/embed/pb-j3svRQLI?si=JZ1r7e3_LcHKYsdG");

        webView.setPrefSize(600,357);
    }

    public void play(){








    }




    private void stopVideo(WebView webView) {
        // Execute JavaScript to stop the video
        webView.getEngine().executeScript("var video = document.querySelector('video');" +
                "if (video) video.pause();");
    }


}
