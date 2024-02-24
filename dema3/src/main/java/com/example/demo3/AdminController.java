package com.example.demo3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<AnimalData> animal_tableView;

    @FXML
    private TableColumn<AnimalData, Integer> animalId_col;

    @FXML
    private TableColumn<AnimalData, String> animalType_col;

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
    private TableColumn<AnimalData, Integer> cageId_col;

    @FXML
    private TableColumn<AnimalData, String> sex_col;



    Connection con;
    PreparedStatement pre;
    ResultSet result;

    public  ObservableList<AnimalData> addAnimalData(){//AnimalData is newly created class name
        ObservableList<AnimalData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM animal";
        con = dbConnection.connection();
        try{
            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            AnimalData animal_d;

            while(result.next()){
                animal_d = new AnimalData(
                        result.getInt("animal_id"),
                        result.getString("animal_type"),
                        result.getInt("cage_id"),
                        result.getString("sex") );
                listData.add(animal_d);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;

    }
    private ObservableList<AnimalData> addAnimalList;

    public void addAnimalShowListData(){
        addAnimalList = addAnimalData();

        animalId_col.setCellValueFactory(new PropertyValueFactory<>("AnimalId"));
        animalType_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cageId_col.setCellValueFactory(new PropertyValueFactory<>("CageId"));
        sex_col.setCellValueFactory(new PropertyValueFactory<>("Sex"));

        animal_tableView.setItems(addAnimalList);
    }

    public void close(){
        System.exit(0);
    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == btn_updateAnimal){
            anc_Animal.setVisible(true);
            anc_ChangePwd.setVisible(false);
            anc_live.setVisible(false);
            anc_task.setVisible(false);
            //when press update animal btn table also show
            addAnimalShowListData();
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
        System.out.println("logout");
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
            addAnimalShowListData();
    }
}