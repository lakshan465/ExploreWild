package lk.ruhcs.explorewild;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        stage.show();
        back.getScene().getWindow().hide();
    }


    String getHashPwd(String pwd) {
        try {
            MessageDigest md = null;

            md = MessageDigest.getInstance("SHA");

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
//    void backToLogin() throws IOException {
//        FXMLLoader loader=new FXMLLoader(getClass().getResource("login.fxml"));
//        Scene scene=new Scene(loader.load());
//        Stage stage=new Stage();
//        stage.setScene(scene);
//        stage.show();
//
//    }

    @FXML
    void regBtnClicked() throws IOException {
        if(unameBox.getText().isEmpty() || pwdBox.getText().isEmpty() ||confirmPwdBox.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Fill all details!");
            alert.showAndWait();
        }
        else{
            reg();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registerd!");
            alert.setContentText("Close this window!");
            alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadingScreen.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 525, 360);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        }
    }


}
