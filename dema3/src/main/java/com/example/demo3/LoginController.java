package com.example.demo3;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.transform.Result;

/**
 * @author l3n
 */
public class LoginController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private PasswordField pwdTxt;

    @FXML
    private TextField unameTxt;

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;

    public void close() {

        System.exit(0);
    }

    public void adminLogin() {
        try {

            if (unameTxt.getText().isEmpty() || pwdTxt.getText().isEmpty()) {
                //Forced to Fill all text box

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage !");
                alert.setHeaderText(null);
                alert.setContentText("Fill all text box please !");
                alert.showAndWait();
            } else {
                //after filling all text box checking weather enter data exist or not database
                String sqlForAdmin = "SELECT * from admin WHERE username = ? and password = ?";
                String sqlForStaff = "SELECT * from staff WHERE username = ? and password = ?";
                String sqlForUser = "SELECT * from user WHERE username = ? and password = ?";

                Connection conn = dbConnection.connection();

                PreparedStatement preAdmin = conn.prepareStatement(sqlForAdmin);
                PreparedStatement preStaff = conn.prepareStatement(sqlForStaff);
                PreparedStatement preUser = conn.prepareStatement(sqlForUser);

                preAdmin.setString(1, unameTxt.getText());
                preAdmin.setString(2, pwdTxt.getText());

                preStaff.setString(1, unameTxt.getText());
                preStaff.setString(2, pwdTxt.getText());

                preUser.setString(1, unameTxt.getText());
                preUser.setString(2, pwdTxt.getText());


                ResultSet resultAdmin = preAdmin.executeQuery();
                if (resultAdmin.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("You login as an Admin!");
                    alert.showAndWait();
                    resultAdmin.close();

                    //hide login window
                    loginBtn.getScene().getWindow().hide();
                    //preAdmin.close();

                    //load admin window with alert
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 862, 560);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                }


                ResultSet resultStaff = preStaff.executeQuery();
                if (resultStaff.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("You login as a Staff member!");
                    alert.showAndWait();
                    resultStaff.close();
                    //preStaff.close();
                    //load staff window with alert

                    //hide login window
                    loginBtn.getScene().getWindow().hide();

                }

                ResultSet resultUser = preStaff.executeQuery();
                if (resultUser.next()) {
                    //load user window with alert

                    //hide login window
                    loginBtn.getScene().getWindow().hide();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
