package lk.ruhcs.explorewild;

import javafx.application.Platform;
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

//import for pay thread code end..........
public class ZooKeeperController implements Initializable {


    @FXML
    private AnchorPane anc_ticket;

    @FXML
    private AnchorPane anc_tskNissue;

    @FXML
    private Button btn_LogOut;

    @FXML
    private Button btn_calculate;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_done;

    @FXML
    private Button btn_pay;

    @FXML
    private Button btn_send;

    @FXML
    private Button btn_showComplateTask;

    @FXML
    private Button btn_showPendingTask;

    @FXML
    private Button btn_taskNissue;

    @FXML
    private Button btn_ticket;

    @FXML
    private TableColumn<TaskData, String> description_col;

    @FXML
    private Label label_balance;

    @FXML
    private Label label_total;

    @FXML
    private Label lebal_name;

    @FXML
    private TableColumn<TaskData, String> status_col;

    @FXML
    private TableView<TaskData> table_task;

    @FXML
    private TableColumn<TaskData, Integer> taskid_col;

    @FXML
    private TextField txt_Lname;

    @FXML
    private TextField txt_child;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextArea txt_issueReport;

    @FXML
    private TextField txt_parent;

    @FXML
    private TextField txt_payAmount;

    @FXML
    private TextField txt_taskId;


    public static String name;
    public  static int idForName;
    Connection con = dbConnection.connection();
    ResultSet rs;
    PreparedStatement ps;

    public void close() {

        System.exit(0);
    }



//    public void done() throws SQLException {
//        String sql = "UPDATE task SET status = 'Done' WHERE task_id '"+txt_taskId.getText()+"'";
//        ps = con.prepareStatement(sql);
//            ps.executeUpdate();
//  }
    public void done() throws SQLException {
        String sql = "UPDATE task SET status = 'Done' WHERE task_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(txt_taskId.getText())); // Assuming txt_taskId contains task ID as an integer
        ps.executeUpdate();
        showGetTaskData();
    }

    public void reportIssueWithAlert(){
        Alert alert;

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR Message");
        alert.setHeaderText(null);
        alert.setContentText("Fill empaty text field!");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get().equals(ButtonType.OK)){
            reportIssue();
        }
    }
    public void reportIssue() {
        String sql = "INSERT INTO issue (Description) VALUES (?)"; // Corrected SQL query with parameter placeholder
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_issueReport.getText()); // Set the description value as a parameter
            ps.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting issue", e); // Wrap the SQL exception in a RuntimeException
        }
        txt_issueReport.setText("");
    }


    //    public void reportIssue () throws SQLException {
//        String sql = "INSERT INTO `issue` ( `Description`) VALUES'"+txt_issueReport.getText()+"'";
//        ps = con.prepareStatement(sql);
//        try {
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public void searchByPending() {
        String sql = "SELECT task_id, status, description FROM task WHERE status = 'Pending' AND task_id = '" + idForName + "'";
        addTaskShowListDataBySerach(sql);

    }

    public void searchByDone(){
        String sql = "SELECT task_id, status, description FROM task WHERE status = 'Done' AND task_id = '" + idForName + "'";
        addTaskShowListDataBySerach(sql);
    }

    public ObservableList<TaskData> addTaskDatabySerch(String sql1) {
        ObservableList<TaskData> listdata = FXCollections.observableArrayList();
        try {
            //ObservableList<TaskData> listdata = FXCollections.observableArrayList();
            String sql = sql1;

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TaskData taskData = new TaskData(rs.getInt("task_id"), rs.getString("status"), rs.getString("description"));
                listdata.add(taskData);
               // return ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listdata;
    }

    public void addTaskShowListDataBySerach(String sql){
        ObservableList<TaskData> addTaskBySerach = addTaskDatabySerch(sql);

        taskid_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        status_col.setCellValueFactory((new PropertyValueFactory<>("status")));
        description_col.setCellValueFactory(new PropertyValueFactory<>("Description"));

        table_task.setItems(addTaskBySerach);
    }
    public void clear() {

        txt_fname.setText("");
        txt_Lname.setText("");
        txt_parent.setText("");
        txt_child.setText("");
        txt_payAmount.setText("");
    }

    //titcket

    public void calculate() {//calculate cost based on members, no sql quarres execute in this function
        System.out.println("cal function");

        if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
            Alert alert;

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill empaty text field!");
            alert.showAndWait();

        } else {


            //int numParent =parseInt(txt_parent.getText());
            int numParent = Integer.parseInt(txt_parent.getText());
            int numChild = Integer.parseInt(txt_child.getText());

            int total = numParent * 500 + numChild * 200;

            label_total.setText(String.valueOf(total));

        }
    }

    //pay function without thread start.....................

//    public void pay() throws SQLException {//making balance and quereis execute for two tables
//        System.out.println("pay function");
//        if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
//            Alert alert;
//
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("ERROR Message");
//            alert.setHeaderText(null);
//            alert.setContentText("Fill empty text field!");
//            alert.showAndWait();
//
//        } else {
//
//
//            int payment = Integer.parseInt(txt_payAmount.getText());
//
//            int numParent = Integer.parseInt(txt_parent.getText());
//            int numChild = Integer.parseInt(txt_child.getText());
//
//            int total = numParent * 500 + numChild * 200;
//
//            label_balance.setText(String.valueOf(payment - total));
//
//            String sql = "INSERT INTO `ticket` ( `Fname`, `Lname`, `parentCount`, `kidCount`) VALUES ( ?, ?, ?, ?)";
//            String sql2 = "INSERT INTO `revenue` (`Fname`, `total`) VALUES (?, ?)";
//
//            try {
//                con.setAutoCommit(false); //step one
//
//
//                ps = con.prepareStatement(sql);
//                ps.setString(1, txt_fname.getText());
//                ps.setString(2, txt_Lname.getText());
//                ps.setInt(3, Integer.parseInt(txt_parent.getText()));
//                ps.setInt(4, Integer.parseInt(txt_child.getText()));
//
//                ps.executeUpdate();
//
//                ps = con.prepareStatement(sql2);
//                ps.setString(1, txt_fname.getText());
//                ps.setInt(2, total);
//
//                ps.executeUpdate();
//                //con.commit(); //step two
//                System.out.println("payment is "+payment+" total is "+total);
//
//                if (payment < total) {
//                    con.rollback();
//
//                    Alert alert;
//
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("ERROR Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("payment not sufficient!");
//                    alert.showAndWait();
//
//
//                }else {
//                    // Commit should happen only if the payment is sufficient
//                    con.commit();
//
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                if (con != null) {
//                    try {
//                        con.rollback(); //step three
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            }
//        }
//
//    }

    //pay function without thread end.....................

    //......................................................using thread....
    //code start
    public void payment(ActionEvent event) {
        System.out.println("pay function");

        if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill empty text field!");
            alert.showAndWait();
        } else {
            int payment = Integer.parseInt(txt_payAmount.getText());
            int numParent = Integer.parseInt(txt_parent.getText());
            int numChild = Integer.parseInt(txt_child.getText());
            int total = numParent * 500 + numChild * 200;

            label_balance.setText(String.valueOf(payment - total));

            String sql = "INSERT INTO `ticket` ( `Fname`, `Lname`, `parentCount`, `kidCount`) VALUES (?, ?, ?, ?)";
            String sql2 = "INSERT INTO `revenue` (`Fname`, `total`) VALUES (?, ?)";

            try {
                con.setAutoCommit(false); // Disable auto-commit

                ps = con.prepareStatement(sql);
                ps.setString(1, txt_fname.getText());
                ps.setString(2, txt_Lname.getText());
                ps.setInt(3, Integer.parseInt(txt_parent.getText()));
                ps.setInt(4, Integer.parseInt(txt_child.getText()));
                ps.executeUpdate();

                ps = con.prepareStatement(sql2);
                ps.setString(1, txt_fname.getText());
                ps.setInt(2, total);
                ps.executeUpdate();

                System.out.println("payment is " + payment + " total is " + total);

                if (payment < total) {
                    con.rollback(); // Rollback if payment is insufficient

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR Message");
                    alert.setHeaderText(null);
                    alert.setContentText("payment not sufficient!");
                    alert.showAndWait();

                } else {
                    con.commit(); // Commit if payment is sufficient

                }

            } catch (SQLException e) {
                e.printStackTrace();
                if (con != null) {
                    try {
                        con.rollback(); // Rollback in case of an exception
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } finally {
                // Ensure the button is re-enabled after the operation
//                Platform.runLater(() -> btn_pay.setDisable(false));
                btn_pay.setDisable(true);
                new Thread(() -> {
                    try {
                        Thread.sleep(5000); // 5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> btn_pay.setDisable(false)); // Re-enable the button
                }).start();
            }
        }

        // Disable the button for 10 seconds to prevent multiple submissions
//        btn_pay.setDisable(true);
//        new Thread(() -> {
//            try {
//                Thread.sleep(5000); // 5 seconds
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Platform.runLater(() -> btn_pay.setDisable(false)); // Re-enable the button
//        }).start();
    }

    public class PaymentHandler {
        private Connection con;
        private PreparedStatement ps;
        private Button payButton; // Add a reference to the pay button

        public PaymentHandler(Connection con, Button payButton) {
            this.con = con;
            this.payButton = payButton;
        }

        public void pay(ActionEvent event) {
            System.out.println("pay function");

            if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Fill empty text field!");
                alert.showAndWait();
            } else {
                int payment = Integer.parseInt(txt_payAmount.getText());
                int numParent = Integer.parseInt(txt_parent.getText());
                int numChild = Integer.parseInt(txt_child.getText());
                int total = numParent * 500 + numChild * 200;

                label_balance.setText(String.valueOf(payment - total));

                String sql = "INSERT INTO `ticket` ( `Fname`, `Lname`, `parentCount`, `kidCount`) VALUES (?, ?, ?, ?)";
                String sql2 = "INSERT INTO `revenue` (`Fname`, `total`) VALUES (?, ?)";

                try {
                    con.setAutoCommit(false); // Disable auto-commit

                    ps = con.prepareStatement(sql);
                    ps.setString(1, txt_fname.getText());
                    ps.setString(2, txt_Lname.getText());
                    ps.setInt(3, Integer.parseInt(txt_parent.getText()));
                    ps.setInt(4, Integer.parseInt(txt_child.getText()));
                    ps.executeUpdate();

                    ps = con.prepareStatement(sql2);
                    ps.setString(1, txt_fname.getText());
                    ps.setInt(2, total);
                    ps.executeUpdate();

                    System.out.println("payment is " + payment + " total is " + total);

                    if (payment < total) {
                        con.rollback(); // Rollback if payment is insufficient

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR Message");
                        alert.setHeaderText(null);
                        alert.setContentText("payment not sufficient!");
                        alert.showAndWait();

                    } else {
                        con.commit(); // Commit if payment is sufficient

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    if (con != null) {
                        try {
                            con.rollback(); // Rollback in case of an exception
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } finally {
                    // Ensure the button is re-enabled after the operation
//                    Platform.runLater(() -> payButton.setDisable(false));
                    payButton.setDisable(true);
                    new Thread(() -> {
                        try {
                            Thread.sleep(10000); // 10 seconds
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> payButton.setDisable(false)); // Re-enable the button
                    }).start();
                }
            }

            // Disable the button for 10 seconds to prevent multiple submissions
//            payButton.setDisable(true);
//            new Thread(() -> {
//                try {
//                    Thread.sleep(10000); // 10 seconds
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Platform.runLater(() -> payButton.setDisable(false)); // Re-enable the button
//            }).start();
        }


//

    }
    //......................................................using thread....
    //code end




    public void clearAll() {

        txt_fname.setText("");
        txt_Lname.setText("");
        txt_parent.setText("");
        txt_child.setText("");
        txt_payAmount.setText("");
        label_balance.setText("xxxx");
        label_total.setText("xxxx");
    }

    //ticket end
    public int setIdForName() {
        try {
            String sql = "SELECT id FROM staff WHERE username  = '" + name + "'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                idForName = rs.getInt("id");

            }
            System.out.println(idForName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idForName;

    }
//System.out.println(idForName);

    //get task data from table based on login name
    public ObservableList<TaskData> getTaskData() {
       // System.out.println("getTaskdata enter");
        idForName = setIdForName();
        //System.out.println(idForName);
        ObservableList<TaskData> listdata = FXCollections.observableArrayList();
        try {
            String sql = "SELECT task_id, status, description FROM task WHERE zoo_keeper_id = '" + idForName + "'";
            //SELECT task_id, status, description FROM task WHERE zoo_keeper_id = '7';

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            TaskData taskData;
            while (rs.next()) {
                taskData = new TaskData(rs.getInt("task_id"), rs.getString("status"), rs.getString("description"));
                listdata.add(taskData);

//                System.out.println(taskData.getTaskId());
//                System.out.println(taskData.getStatus());
//                System.out.println(taskData.getDescription());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listdata;
    }

    public void showGetTaskData() {
        //ui table column name vs attribute
        taskid_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("Status"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("Description"));

        table_task.setItems(getTaskData());
    }


    public void logout() {
        try {

            String logoutstaff = "DELETE FROM current_keepers2 LIMIT 1";
            PreparedStatement logoutstaffps = con.prepareStatement(logoutstaff);
            logoutstaffps.execute();

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
                btn_LogOut.getScene().getWindow().hide();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void switchForm(ActionEvent event) {
        if (event.getSource() == btn_ticket) {
            anc_ticket.setVisible(true);
            anc_tskNissue.setVisible(false);
            clear();

        } else if (event.getSource() == btn_taskNissue) {

            anc_ticket.setVisible(false);
            anc_tskNissue.setVisible(true);
            showGetTaskData();
        }
    }

    public void clearForm(){
        clearAll();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showGetTaskData();
        setIdForName();
        lebal_name.setText(name);
    }
}

