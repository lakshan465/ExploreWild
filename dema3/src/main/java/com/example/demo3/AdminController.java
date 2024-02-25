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


    //fxml for issue start
    @FXML
    private TableColumn<IssueData, String> issue_description_col;

    @FXML
    private TableColumn<IssueData, Integer> issue_id_code;

    @FXML
    private TableView<IssueData> issue_table_view;

    //fxml for issue end

    //fxml for task START
    @FXML
    private TableColumn<TaskData, String> status_col;

    @FXML
    private TableColumn<TaskData, Integer> task_id_col;
    @FXML
    private TableColumn<TaskData, String> des_col;

    @FXML
    private TableView<TaskData> task_table_view;

    @FXML
    private TextField txt_keeper_id;

    @FXML
    private TextField txt_task_id;
    @FXML
    private TableColumn<TaskData,Integer> keeper_id_col;
    @FXML
    private Button add_task_btn;
    //fxml for task END
    @FXML
    private Label admin_name_label;

    //live count start
    @FXML
    private Button btn_admin_reload;
    @FXML
    private Label live_cuz;

    @FXML
    private Label live_keepers;
    //live count end

    static String name;//based on this var value welcome text will be change



    Connection con,conload;
    PreparedStatement pre,precuz,prekeeper;
    ResultSet resultAnimal,resultTask,resultIssue,resultReloadcuz,resultReloadkeeprer;


    public void reload(){
        try {
            conload = dbConnection.connection();
            String sqlcuz = " SELECT COUNT(id) AS ID FROM  current_cus";
            String sqlkeeper = "SELECT COUNT(id) AS ID FROM current_keepers";

            precuz = conload.prepareStatement(sqlcuz);
            prekeeper = conload.prepareStatement(sqlkeeper);

            resultReloadcuz = precuz.executeQuery();
            if(resultReloadcuz.next()) {
                int vlive_cuz = resultReloadcuz.getInt("ID");
                live_cuz.setText(String.valueOf(vlive_cuz));
                resultReloadcuz.close();
            }
            resultReloadkeeprer = prekeeper.executeQuery();
            if(resultReloadkeeprer.next()) {

                int vlive_keepers = resultReloadkeeprer.getInt("ID");
                live_keepers.setText(String.valueOf(vlive_keepers));
                resultReloadkeeprer.close();
            }
            conload.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    //issueTableStart
    public ObservableList<IssueData> addissueData(){
        ObservableList<IssueData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM issue";
        con = dbConnection.connection();
        try{
            pre = con.prepareStatement(sql);
            resultIssue = pre.executeQuery();
            IssueData issueData;

            while(resultIssue.next()){
                issueData = new IssueData(
                        resultIssue.getInt("issu_id"),
                        resultIssue.getString("Description"));

                listData.add(issueData);
            }
            con.close();
            resultIssue.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<IssueData> addIssueList;
    public void addIssueShowListData()  {
        try {

            //data that retrive form server spred to the table in order
            addIssueList = addissueData();

            task_id_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));
            keeper_id_col.setCellValueFactory(new PropertyValueFactory<>("keeperId"));
            status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
            des_col.setCellValueFactory(new PropertyValueFactory<>("Description"));

            issue_table_view.setItems(addIssueList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //isuueTableEND


    //taskTableStart
    public ObservableList<TaskData>  addTaskData() throws SQLException {
        ObservableList<TaskData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM task";
        con = dbConnection.connection();
        try{
            pre = con.prepareStatement(sql);
            resultTask = pre.executeQuery();
            TaskData taskData;

            while(resultTask.next()){
                taskData = new TaskData(
                        resultTask.getInt("task_id"),
                        resultTask.getInt("zoo keeper_id"),
                        resultTask.getString("status"),
                        resultTask.getString("description") );
                listData.add(taskData);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        con.close();
        resultTask.close();
        return listData;
    }
    private ObservableList<TaskData> addTaskList;

    public void addTaskShowListData() throws SQLException {
        //data that retrive form server spred to the table in order
        addTaskList = addTaskData();

        issue_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        issue_id_code.setCellValueFactory(new PropertyValueFactory<>("issueId"));


        task_table_view.setItems(addTaskList);
    }
    //taskTableEnd

    //animalTableStart
    public  ObservableList<AnimalData> addAnimalData() throws SQLException {//AnimalData is newly created class name
        //retrrive data from server and give to the linked list
        //then function return it
        ObservableList<AnimalData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM animal";
        con = dbConnection.connection();
        try{
            pre = con.prepareStatement(sql);
            resultAnimal = pre.executeQuery();
            AnimalData animal_d;

            while(resultAnimal.next()){
                animal_d = new AnimalData(
                        resultAnimal.getInt("animal_id"),
                        resultAnimal.getString("animal_type"),
                        resultAnimal.getInt("cage_id"),
                        resultAnimal.getString("sex") );
                listData.add(animal_d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.close();
        resultAnimal.close();
        return listData;

    }
    private ObservableList<AnimalData> addAnimalList;

    public void addAnimalShowListData() throws SQLException {//data that retrive form server spred to the table in order
        addAnimalList = addAnimalData();

        animalId_col.setCellValueFactory(new PropertyValueFactory<>("AnimalId"));
        animalType_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cageId_col.setCellValueFactory(new PropertyValueFactory<>("CageId"));
        sex_col.setCellValueFactory(new PropertyValueFactory<>("Sex"));

        animal_tableView.setItems(addAnimalList);
    }


    //animalTableEnd

    public void close(){
        System.exit(0);
    }

    public void switchForm(ActionEvent event) throws SQLException {
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
            addTaskShowListData();
        }else if (event.getSource() ==btn_liveUpdate) {
            anc_Animal.setVisible(false);
            anc_ChangePwd.setVisible(false);
            anc_live.setVisible(true);
            anc_task.setVisible(false);
            addIssueShowListData();
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


        admin_name_label.setText(name);//based on this welcome text will be change

        try {
            reload();
            addAnimalShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            addTaskShowListData();
            addIssueShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}