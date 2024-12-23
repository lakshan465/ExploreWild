package lk.ruhcs.explorewild;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegForm {


    @FXML
    private ImageView back;
    @FXML
    private PasswordField confirmPwdBox;

    @FXML
    private PasswordField pwdBox;

    @FXML
    private TextField unameBox;
    @FXML
    private Button backBtn;

    public void print() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene=new Scene(loader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        Image icon=new Image("/logo.png");
        stage.getIcons().add(icon);
        stage.show();
        back.getScene().getWindow().hide();
    }


    String getHashPwd(String pwd) {
        try {
            MessageDigest md = null;

            md = MessageDigest.getInstance("SHA"); //oSHAn

            md.update(pwd.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : rbt) {

                sb.append(String.format("%02x", b));

            }


            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    void reg(){
        String uname=unameBox.getText();
        String pwd=getHashPwd(pwdBox.getText());
        String sql="INSERT INTO user (`username`, `password`) VALUES('"+uname+"','"+pwd+"')";

        try {
            Connection con= dbConnection.connection();

            PreparedStatement pst=con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        back.getScene().getWindow().hide();

    }

    public int flag = 0;

    int flagCheck() {
        String uname = unameBox.getText();
        String pwd = getHashPwd(pwdBox.getText());

        // Corrected SQL query using placeholders (?)
        String sql = "SELECT username, password FROM user WHERE username = ? AND password = ?";

        try {
            // Get the connection
            Connection con = dbConnection.connection();

            // Use PreparedStatement with placeholders
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, uname);  // Set the username
            pst.setString(2, pwd);    // Set the hashed password

            // Execute the query
            ResultSet rs = pst.executeQuery();

            // If a matching user is found, set flag to 1, otherwise it remains 0
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flag;
    }



    @FXML
    void regBtnClicked() throws IOException {
        flagCheck();
        if(unameBox.getText().isEmpty() || pwdBox.getText().isEmpty() ||confirmPwdBox.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Fill all details!");
            alert.showAndWait();
        } else if (!pwdBox.getText().equals(confirmPwdBox.getText())) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error massage");
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();//user can add data without filling animal_id box
            pwdBox.clear();
            confirmPwdBox.clear();

        } else if (flag==1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error massage");
            alert.setContentText("Use another Username and Password!");
            alert.showAndWait();//user can add data without filling animal_id box
            unameBox.clear();
            pwdBox.clear();
            confirmPwdBox.clear();

        } else{
            reg();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registered!");
            alert.setContentText("Registration successful!");
            alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadingScreen.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 525, 360);
            Stage stage = new Stage();

            stage.setScene(scene);
            Image icon=new Image("/logo.png");
            stage.getIcons().add(icon);
            stage.show();
            stage.setResizable(false);
        }
    }


}
