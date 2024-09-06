package lk.ruhcs.explorewild;

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
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminController extends User implements Initializable {


    //assign task
    @FXML
    private Button Search_task_btn;

    @FXML
    private Button add_task_btn;

    @FXML
    private Button Delete_task_btn;

    @FXML
    private TextField txt_description;

    @FXML
    private TextField txt_keeper_id;

    @FXML
    private TextField txt_task_id;

    //end

    //changing pwd start

    @FXML
    private TextField cuz_id_chng_pwd;

    @FXML
    private TextField cuz_pwd_chng_pwd;

    @FXML
    private TextField keeper_id_chng_pwd;

    @FXML
    private TextField keeper_pwd_chng_pwd;

    //changind pwd end
    @FXML
    private AnchorPane anc_Animal;

    @FXML
    private AnchorPane anc_ChangePwd;

    @FXML
    private AnchorPane anc_live;

    @FXML
    private AnchorPane anc_task;


    //fxml for animalData start

    @FXML
    private ChoiceBox<String> gender_list;

    @FXML
    private ChoiceBox<String> animal_list;
    @FXML
    private TableView<AnimalData> animal_tableView;

    @FXML
    private TextField animal_id;
    @FXML
    private TextField cage_id;

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
    private TableColumn<TaskData, Integer> keeper_id_col;

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


    Connection con, conload, conTask;
    PreparedStatement pre, precuz, prekeeper, preTask;
    ResultSet resultAnimal, resultTask, resultIssue, resultReloadcuz, resultReloadkeeprer, resultAddAnimal;


    //search animal by id or cage id or animal type
//    public void searchAnimal(){
    //dumb code...........................................................................................
//        String animalId = animal_id.getText();
//        String cageId = cage_id.getText();
//        String type = animal_list.getSelectionModel().getSelectedItem();
//
//        String sqlAnimaId = "SELECT animal_id FROM animal WHERE animal_id LIKE '"+animalId+"'";
//
//        try {
//            PreparedStatement preAnimalId =  con.prepareStatement(sqlAnimaId);
//            ResultSet rsAnimalId = preAnimalId.executeQuery();
//
//            AnimalData animalData;//create object from animaldata
//
//            while (rsAnimalId.next()) {
//                animalData = new animalData(//pass data to issuedata object throuh its constructor
////                        //to object attribiute
////                        resultIssue.getInt("issu_id"),
////                        resultIssue.getString("Description"));
////
////                listData.add(issueData);//add object to the list
////            }
////            con.close();
////            resultIssue.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    //task tab starting from here

    //
    //clicked  task data row  will move to boxes
    public void addTaskSelect() {
        TaskData taskData = task_table_view.getSelectionModel().getSelectedItem();


        int num = task_table_view.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;


        txt_task_id.setText(String.valueOf(taskData.getTaskId()));
        txt_keeper_id.setText(String.valueOf(taskData.getKeeperId()));
        txt_description.setText(String.valueOf(taskData.getDescription()));


    }

    //add task start
    public void addTask() {//insert new animal information to database
        String sql = "INSERT INTO task (zoo_keeper_id, status, description) VALUES (?, ?, ?)";
        Connection conAdTsk = dbConnection.connection();
        PreparedStatement preTask = null;

        try {
            Alert alert;

            if (txt_keeper_id.getText().isEmpty()
                    || txt_description.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage");
                alert.setContentText("Please fill blank fields except task ID");
                alert.showAndWait();//user can add data without filling animal_id box
            } else {

                assert conAdTsk != null;

                preTask = conAdTsk.prepareStatement(sql);
                //pre.setString(1, animal_id.getText()); no need bcz its auto incremnt by itself
                preTask.setInt(1, Integer.parseInt(txt_keeper_id.getText()));
                //preTask.setInt();
                preTask.setString(2, "Pending");
                preTask.setString(3, txt_description.getText());

                preTask.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added!");
                alert.showAndWait();

                //this will update the table view when we press add btn
                //to update table view
                addTaskShowListData();

                //clear the field
                addTaskClear();


            }
            preTask.close();
            conAdTsk.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    //add task end

    //search by pending
    public void searchByPending() throws SQLException {
        String sql = "SELECT * FROM task WHERE status ='Pending'";
        addTaskShowListDatabySerach(sql);
    }

    public void searchByDone() throws SQLException {
        String sql = "SELECT * FROM task WHERE status ='Done'";
        addTaskShowListDatabySerach(sql);
    }
    //search by pending end

    //search task start

    public void searchTask() throws SQLException {
        String taskIdSearch = txt_task_id.getText();
        String keeperIdSearch = txt_keeper_id.getText();

        if (!txt_task_id.getText().isEmpty()) {
            String sql = "SELECT * FROM task WHERE task_id LIKE '" + taskIdSearch + "'";
            addTaskShowListDatabySerach(sql);
        } else {
            // Corrected the SQL string
            String sql = "SELECT * FROM task WHERE zoo zoo_keeper_id = '" + keeperIdSearch + "'";
            addTaskShowListDatabySerach(sql);
        }

        addTaskClear();
    }


    public ObservableList<TaskData> addTaskDatabySerach(String sql1) throws SQLException {
        //AnimalData is newly created class name
        //retrrive data from server and give to the linked list
        //then function return it
        ObservableList<TaskData> listData = FXCollections.observableArrayList();


        String sql = sql1;
        conTask = dbConnection.connection();
        try {
            preTask = conTask.prepareStatement(sql);
            ResultSet resultTask = preTask.executeQuery();
            TaskData task_d;

            while (resultTask.next()) {
                task_d = new TaskData(
                        resultTask.getInt("task_id"),
                        resultTask.getInt("zoo_keeper_id"),
                        resultTask.getString("status"),
                        resultTask.getString("description"));
                listData.add(task_d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conTask.close();
        resultTask.close();
        return listData;

    }

    public void addTaskShowListDatabySerach(String str) throws SQLException//addTaskShowListDatabySerach
    {//data that retrive form server spred to the table in order
        ObservableList<TaskData> addTaskListforsearch = addTaskDatabySerach(str);

        task_id_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));//AnimalId is attribute of Animal
        keeper_id_col.setCellValueFactory(new PropertyValueFactory<>("keeperId"));//this map object attribute and table column
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        des_col.setCellValueFactory(new PropertyValueFactory<>("Description"));

        task_table_view.setItems(addTaskListforsearch);
    }


    //search task end

    public void deleteTask() {
        try {
            Alert alert;
            if (txt_task_id.getText().isEmpty() && txt_keeper_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("fill at least one id field");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation massage");
                if (!txt_task_id.getText().isEmpty()) {
                    alert.setContentText("Are you sure about deleting task with task id:" + txt_task_id.getText() + " ?");
                }
                if (!txt_keeper_id.getText().isEmpty()) {
                    alert.setContentText("Are you sure about deleting task that related to Zoo keeper id:" + txt_keeper_id.getText() + " ?");
                }
                Optional<ButtonType> option = alert.showAndWait();
                Connection conD;
                Statement st;
                if (option.get().equals(ButtonType.OK)) {
                    if (!txt_task_id.getText().isEmpty()) {
                        String sql = "DELETE FROM task where task_id = '" + txt_task_id.getText() + "'";
                        conD = dbConnection.connection();
                        st = conD.createStatement();
                        st.executeUpdate(sql);

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info massage!");
                        alert.setContentText("Deleted!");
                        alert.showAndWait();
                    } else {
                        String sql = "DELETE FROM task where zoo_keeper_id = '" + txt_keeper_id.getText() + "'";
                        conD = dbConnection.connection();
                        st = conD.createStatement();
                        st.executeUpdate(sql);

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info massage!");
                        alert.setContentText("Deleted!");
                        alert.showAndWait();
                    }

                    conD.close();
                    st.close();
                }
            }
            addTaskClear();
            addTaskShowListData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Delete task start


    //Delete task end
    public void addTaskClear() {
        txt_task_id.setText("");
        txt_keeper_id.setText("");
        txt_description.setText("");

    }

    public void changePwdCuz() {

        Alert alert;
        if ((cuz_id_chng_pwd.getText().isEmpty() || cuz_pwd_chng_pwd.getText().isEmpty()) && (keeper_id_chng_pwd.getText().isEmpty() || keeper_pwd_chng_pwd.getText().isEmpty())) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText("Please fill text field !");
            alert.showAndWait();
            // return(0);

        } else if (!cuz_id_chng_pwd.getText().isEmpty()) {
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation massage");
                alert.setContentText("Are you sure about updating pwd as" + cuz_pwd_chng_pwd.getText() + " ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String pwd = cuz_pwd_chng_pwd.getText();//cuz_pwd_chng_pwd
                    String sql = "UPDATE user SET password = '" + pwd + "'WHERE username ='" + cuz_id_chng_pwd.getText() + "'";
                    Connection concpwd = dbConnection.connection();
                    Statement st = null;
                    try {
                        st = concpwd.createStatement();
                        st.executeUpdate(sql);
                        concpwd.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information massage");
                    alert.setContentText("Deleted!!");
                    alert.showAndWait();
                    //pre.close();

                }
            }
            //  addPetClear();

            // addAnimalShowListData();

//            return(0);
            cuz_id_chng_pwd.setText("");
            cuz_pwd_chng_pwd.setText("");
            //return 0;
        } else if (!keeper_id_chng_pwd.getText().isEmpty()) {
            //changes
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("confirmation massage");
            alert.setContentText("Are you sure about updating pwd as" + keeper_pwd_chng_pwd.getText() + " ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String pwd = keeper_pwd_chng_pwd.getText();//keeper_pwd_chng_pwd
                String sql = "UPDATE staff SET password = '" + pwd + "'WHERE username ='" + keeper_id_chng_pwd.getText() + "'";
                Connection concpwd = dbConnection.connection();
                Statement st = null;
                try {
                    st = concpwd.createStatement();
                    st.executeUpdate(sql);
                    concpwd.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information massage");
                alert.setContentText("Deleted!!");
                alert.showAndWait();
                //pre.close();

            }
            keeper_id_chng_pwd.setText("");
            keeper_pwd_chng_pwd.setText("");
            //return 0;
        }
        //  addPetClear();

        // addAnimalShowListData();

//            return(0);

        //changes end


        // return 0;
    }


//delese animal

    public void deleteAnimal() {

        try {
            Alert alert;
            if (animal_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation massage");
                alert.setContentText("Are you sure about deleting animal id:" + animal_id.getText() + " ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String sql = "DELETE FROM animal where animal_id = '" + animal_id.getText() + "'";
                    Connection conD = dbConnection.connection();
                    Statement st = conD.createStatement();
                    st.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information massage");
                    alert.setContentText("Deleted!!");
                    alert.showAndWait();
                    pre.close();
                    conD.close();
                }
            }
            addPetClear();

            addAnimalShowListData();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //search animal by id or cage id or animal type
    public void search() throws SQLException {//searchAnimal

        String animalIdserch = animal_id.getText();
        String cageIdforserch = cage_id.getText();
        String typeserch = animal_list.getSelectionModel().getSelectedItem();

        if (!animal_id.getText().isEmpty()) {
            String sql = "SELECT * FROM animal WHERE animal_id LIKE '" + animalIdserch + "'";
            addAnimalShowListDatabySerach(sql);
        } else if (!cage_id.getText().isEmpty()) {
            String sql = "SELECT * FROM animal WHERE cage_id LIKE '" + cageIdforserch + "'";
            addAnimalShowListDatabySerach(sql);
        } else {
            String sql = "SELECT * FROM animal WHERE animal_type LIKE '" + typeserch + "'";
            addAnimalShowListDatabySerach(sql);
        }
        addPetClear();
    }//(typeserch != null)


    public ObservableList<AnimalData> addAnimalDatabySerach(String sql1) throws SQLException {//AnimalData is newly created class name
        //retrrive data from server and give to the linked list
        //then function return it
        ObservableList<AnimalData> listData = FXCollections.observableArrayList();


        //String sql = "SELECT animal_id FROM animal WHERE animal_id LIKE '" + animalId + "'";
        String sql = sql1;
        con = dbConnection.connection();
        try {
            pre = con.prepareStatement(sql);
            resultAnimal = pre.executeQuery();
            AnimalData animal_d;

            while (resultAnimal.next()) {
                animal_d = new AnimalData(
                        resultAnimal.getInt("animal_id"),
                        resultAnimal.getString("animal_type"),
                        resultAnimal.getInt("cage_id"),
                        resultAnimal.getString("sex"));
                listData.add(animal_d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.close();
        resultAnimal.close();
        return listData;

    }

    public void addAnimalShowListDatabySerach(String animalId) throws SQLException {//data that retrive form server spred to the table in order
        ObservableList<AnimalData> addAnimalListforsearch = addAnimalDatabySerach(animalId);

        animalId_col.setCellValueFactory(new PropertyValueFactory<>("AnimalId"));
        animalType_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cageId_col.setCellValueFactory(new PropertyValueFactory<>("CageId"));
        sex_col.setCellValueFactory(new PropertyValueFactory<>("Sex"));

        animal_tableView.setItems(addAnimalListforsearch);
    }

    //clicked row data will move to boxes
    public void addPetSelect() {
        AnimalData animalData = animal_tableView.getSelectionModel().getSelectedItem();
        int num = animal_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) return;

        animal_id.setText(String.valueOf(animalData.getAnimalId()));
        // ObservableList<String> typeList = FXCollections.observableArrayList(animalData.getType());
        animal_list.setValue(animalData.getType());
        cage_id.setText(String.valueOf((animalData.getCageId())));
        //SingleSelectionModel<String> genList = (SingleSelectionModel<String>) FXCollections.observableArrayList(animalData.getSex());
        gender_list.setValue(animalData.getSex());

    }

    public void addAnimal() {//insert new animal information to database
        String sql = "INSERT INTO animal (animal_type,cage_id,sex) VALUES (?,?,?)";
        con = dbConnection.connection();

        try {
            Alert alert;

            if (animal_list.getSelectionModel().getSelectedItem() == null
                    || cage_id.getText().isEmpty()
                    || gender_list.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage");
                alert.setContentText("Please fill blank fields except animal ID");
                alert.showAndWait();//user can add data without filling animal_id box
            } else {

                pre = con.prepareStatement(sql);
                //pre.setString(1, animal_id.getText()); no need bcz its auto incremnt by itself
                pre.setString(1, animal_list.getSelectionModel().getSelectedItem());
                pre.setString(2, cage_id.getText());
                pre.setString(3, gender_list.getSelectionModel().getSelectedItem());

                pre.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added!");
                alert.showAndWait();

                //this will update the table view when we press add btn
                //to update table view
                addAnimalShowListData();
                //clear the field
                addPetClear();


            }
            pre.close();
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private String[] sexArray = {"Male", "Female"};
    private String[] animalArray = {"Panda", "Lion", "Fish"};

    public void genderList() {

        //List<String> list1 = new ArrayList<>();

        //for(String data:sexArray){
        //    list1.add(data);
        // }
        ObservableList<String> list3 = FXCollections.observableArrayList(sexArray);
        gender_list.setItems(list3);

    }

    public void animalTypeList() {//drop down menu  for animal gender

        //List<String> list1 = new ArrayList<>();

        //for(String data:sexArray){
        //    list1.add(data);
        // }
        ObservableList<String> list2 = FXCollections.observableArrayList(animalArray);
        animal_list.setItems(list2);

    }

    public void addAnimalUpdate() {//user need to fill all box other than animal id box
        String sql = "UPDATE animal SET animal_type= '"
                + animal_list.getSelectionModel().getSelectedItem() + "', cage_id = '"
                + cage_id.getText() + "', sex = '"
                + gender_list.getSelectionModel().getSelectedItem() + "' WHERE animal_id = " + animal_id.getText();

        con = dbConnection.connection();
        try {
            Alert alert;

            if (animal_list.getSelectionModel().getSelectedItem() == null
                    || cage_id.getText().isEmpty()
                    || gender_list.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage");
                alert.setContentText("Please fill blank fields except animal ID");
                alert.showAndWait();//user can add data without filling all boxes

            } else {
                alert = new Alert((Alert.AlertType.CONFIRMATION));
                alert.setTitle("Confirmation massage");
                alert.setContentText("You Sure!");
                Optional<ButtonType> option = alert.showAndWait();//user can add data without filling all boxes

                if (option.get().equals((ButtonType.OK))) {
                    Statement statement = con.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Changed!");
                    alert.showAndWait();

                    //this will update the table view when we press add btn
                    //to update table view
                    addAnimalShowListData();
                    //clear the field
                    addPetClear();

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPetClear() {//after enter data to boxes and press add btn,but still entered data are there use this to clean them

//        //pre.setString(1, animal_id.getText());
//        pre.setString(1, animal_list.getSelectionModel().getSelectedItem()); to copy idss'
//        pre.setString(2, cage_id.getText());
//        pre.setString(3, gender_list.getSelectionModel().getSelectedItem());

        animal_id.setText("");
        animal_list.getSelectionModel().clearSelection();
        cage_id.setText("");
        gender_list.getSelectionModel().clearSelection();

    }

    public void reload() {
        try {
            conload = dbConnection.connection();
            String sqlcuz = " SELECT COUNT(id) AS ID FROM  current_cus";
            String sqlkeeper = "SELECT COUNT(id) AS ID FROM current_keepers2";

            precuz = conload.prepareStatement(sqlcuz);
            prekeeper = conload.prepareStatement(sqlkeeper);

            resultReloadcuz = precuz.executeQuery();
            if (resultReloadcuz.next()) {
                int vlive_cuz = resultReloadcuz.getInt("ID");
                live_cuz.setText(String.valueOf(vlive_cuz));
                resultReloadcuz.close();
            }
            resultReloadkeeprer = prekeeper.executeQuery();
            if (resultReloadkeeprer.next()) {

                int vlive_keepers = resultReloadkeeprer.getInt("ID");
                live_keepers.setText(String.valueOf(vlive_keepers));
                resultReloadkeeprer.close();
            }
            conload.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//issueTableStart


    public ObservableList<IssueData> addissueData() {
        ObservableList<IssueData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM issue";
        con = dbConnection.connection();
        try {
            pre = con.prepareStatement(sql);
            resultIssue = pre.executeQuery();//get data from table and save in resultIssue
            IssueData issueData;//create object from isuuedata

            while (resultIssue.next()) {
                issueData = new IssueData(//pass data to issuedata object throuh its constructor
                        //to object attribiute
                        resultIssue.getInt("issu_id"),
                        resultIssue.getString("Description"));

                listData.add(issueData);//add object to the list
            }
            con.close();
            resultIssue.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<IssueData> addIssueList;

    public void addIssueShowListData() {
        try {

            //data that retrive form server spred to the table in order
            addIssueList = addissueData();
            //green colum name are related to object attribute and pink are in table view
            issue_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
            issue_id_code.setCellValueFactory(new PropertyValueFactory<>("issueId"));

            //now table will recive data based on above order, that mean issue_descrption col will have description data
            issue_table_view.setItems(addIssueList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//isuueTableEND


    //taskTableStart
    public ObservableList<TaskData> addTaskData() throws SQLException {
        ObservableList<TaskData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM task";
        con = dbConnection.connection();
        try {
            pre = con.prepareStatement(sql);
            resultTask = pre.executeQuery();
            TaskData taskData;

            while (resultTask.next()) {
                taskData = new TaskData(
                        resultTask.getInt("task_id"),
                        resultTask.getInt("zoo_keeper_id"),
                        resultTask.getString("status"),
                        resultTask.getString("description"));
                listData.add(taskData);
            }

        } catch (Exception e) {
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


        //
        task_id_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        keeper_id_col.setCellValueFactory(new PropertyValueFactory<>("keeperId"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        des_col.setCellValueFactory(new PropertyValueFactory<>("Description"));


        task_table_view.setItems(addTaskList);
    }
//taskTableEnd

    //animalTableStart
    public ObservableList<AnimalData> addAnimalData() throws SQLException {//AnimalData is newly created class name
        //retrrive data from server and give to the linked list
        //then function return it
        ObservableList<AnimalData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM animal";
        con = dbConnection.connection();
        try {
            pre = con.prepareStatement(sql);
            resultAnimal = pre.executeQuery();
            AnimalData animal_d;

            while (resultAnimal.next()) {
                animal_d = new AnimalData(
                        resultAnimal.getInt("animal_id"),
                        resultAnimal.getString("animal_type"),
                        resultAnimal.getInt("cage_id"),
                        resultAnimal.getString("sex"));
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
        addAnimalList = addAnimalData();//linked list

        animalId_col.setCellValueFactory(new PropertyValueFactory<>("AnimalId"));
        animalType_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cageId_col.setCellValueFactory(new PropertyValueFactory<>("CageId"));//Sex
        sex_col.setCellValueFactory(new PropertyValueFactory<>("Sex"));//CageId

        animal_tableView.setItems(addAnimalList);
    }


//animalTableEnd

    public void close() {
        System.exit(0);
    }

    public void switchForm(ActionEvent event) {
        try {
            if (event.getSource() == btn_updateAnimal) {
                genderList();
                anc_Animal.setVisible(true);
                anc_ChangePwd.setVisible(false);
                anc_live.setVisible(false);
                anc_task.setVisible(false);

//              //  btn_assignTask.getStyleClass().add("login-btnLK");
//                btn_updateAnimal.setStyle("-fx-background-color: linear-gradient(to bottom right, #4f937a, #6e2773);");
//
//                btn_changePwd.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//
//
//                btn_assignTask.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//                btn_liveUpdate.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");


                //when press update animal btn table also show
                addAnimalShowListData();
                animalTypeList();
                addPetClear();


            } else if (event.getSource() == btn_changePwd) {
                anc_Animal.setVisible(false);
                anc_ChangePwd.setVisible(true);
                anc_live.setVisible(false);
                anc_task.setVisible(false);

//                btn_changePwd.setStyle("-fx-background-color: linear-gradient(to bottom right, #4f937a, #6e2773);");
//
////                btn_changePwd
////                        btn_updateAnimal
////                btn_assignTask
////                        btn_liveUpdate
//                btn_updateAnimal.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//                btn_assignTask.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//                btn_liveUpdate.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");


            } else if (event.getSource() == btn_assignTask) {
                anc_Animal.setVisible(false);
                anc_ChangePwd.setVisible(false);
                anc_live.setVisible(false);
                anc_task.setVisible(true);

//                btn_assignTask.setStyle("-fx-background-color: linear-gradient(to bottom right, #4f937a, #6e2773);");
//
//                btn_updateAnimal.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
////                btn_changePwd
////                        btn_updateAnimal
////                btn_assignTask
////                        btn_liveUpdate
//                btn_changePwd.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//                btn_liveUpdate.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
                addTaskClear();
                addTaskShowListData();
            } else if (event.getSource() == btn_liveUpdate) {
                anc_Animal.setVisible(false);
                anc_ChangePwd.setVisible(false);
                anc_live.setVisible(true);
                anc_task.setVisible(false);

//                btn_liveUpdate.setStyle("-fx-background-color: linear-gradient(to bottom right, #4f937a, #6e2773);");
//
//                btn_updateAnimal.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
////                btn_changePwd
////                        btn_updateAnimal
////                btn_assignTask
////                        btn_liveUpdate
//                btn_assignTask.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");
//
//                btn_changePwd.setStyle("-fx-background-color: linear-gradient(to bottom right, #085203, #693803);" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-cursor: hand;" +
//                        "-fx-text-fill: #fff;" +
//                        "-fx-font-size:14px;" +
//                        "-fx-font-family:'Arial';");

                addIssueShowListData();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you want to logout ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                System.out.println("logout");

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
            addTaskShowListData();
            addIssueShowListData();
            addTaskShowListData();
            genderList();
            animalTypeList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public AnchorPane getAnc_ChangePwd() {
        return anc_ChangePwd;
    }
}