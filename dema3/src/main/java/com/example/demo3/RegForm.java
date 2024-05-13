package com.example.demo3;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegForm {

    @FXML
    private PasswordField confirmPwdBox;

    @FXML
    private PasswordField pwdBox;

    @FXML
    private TextField unameBox;




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

        String sql="INSERT INTO admin (`username`, `password`) VALUES('"+uname+"','"+pwd+"')";

        try {
            Connection con=dbConnection.connection();

            PreparedStatement pst=con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void regBtnClicked(){
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
        }
    }


}
