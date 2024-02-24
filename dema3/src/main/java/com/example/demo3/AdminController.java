package com.example.demo3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminController implements Initializable {


    @FXML
    private AnchorPane anc_Animal;

    @FXML
    private AnchorPane anc_ChangePwd;

    @FXML
    private AnchorPane anc_live;

    @FXML
    private AnchorPane anc_task;

    @FXML
    private TableColumn<?, ?> animalId_col;

    @FXML
    private TableColumn<?, ?> animalType_col;

    @FXML
    private Button btn_assignTask;

    @FXML
    private Button btn_changePwd;

    @FXML
    private Button btn_liveUpdate;

    @FXML
    private Button btn_logOut;

    @FXML
    private Button btn_updateAnimal;

    @FXML
    private TableColumn<?, ?> cageId_col;

    @FXML
    private TableColumn<?, ?> sex_col;

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    Connection con;
    PreparedStatement pre;
    ResultSet result;

//    public  <Animal> addAnimalData(){
//        ObservableList<Animal> listData = FXCollections.observableArrayList();
//
//        String sql = "SELECT * FROM animal";
//        con = dbConnection.connection();
//        try{
//            pre = con.prepareStatement(sql);
//            result = pre.executeQuery();
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public void close(){
        System.exit(0);
    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == btn_updateAnimal){
            anc_Animal.setVisible(true);
            anc_ChangePwd.setVisible(false);
            anc_live.setVisible(false);
            anc_task.setVisible(false);
        } else if (event.getSource() ==btn_changePwd) {
            anc_Animal.setVisible(false);
            anc_ChangePwd.setVisible(true);
            anc_live.setVisible(false);
            anc_task.setVisible(false);
        }else if (event.getSource() ==btn_assignTask) {
            anc_Animal.setVisible(false);
            anc_ChangePwd.setVisible(false);
            anc_live.setVisible(false);
            anc_task.setVisible(true);
        }else if (event.getSource() ==btn_liveUpdate) {
            anc_Animal.setVisible(false);
            anc_ChangePwd.setVisible(false);
            anc_live.setVisible(true);
            anc_task.setVisible(false);
        }

    }
    public void logout(){

        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you want to logout ?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene= new Scene(root);
                stage.setScene(scene);
                stage.show();

                //hide login window
                btn_logOut.getScene().getWindow().hide();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
