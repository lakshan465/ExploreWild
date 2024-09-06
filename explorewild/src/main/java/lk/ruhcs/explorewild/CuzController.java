package lk.ruhcs.explorewild;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class CuzController implements Initializable {

    @FXML
    private AnchorPane anc_ticket;

    @FXML
    private AnchorPane anc_liveFeed;
    @FXML
    private AnchorPane anc_map;


    @FXML
    private Button btn_LogOut;

    @FXML
    private Button btn_calculate;

    @FXML
    private Button btn_live;

    @FXML
    private Button btn_pay;

    @FXML
    private Button btn_map;

    @FXML
    private Button webBtn;

    @FXML
    private Button btn_ticket;

    @FXML
    private Label label_balance;

    @FXML
    private Label label_total;

    @FXML
    private Label lebal_name;

    @FXML
    private TextField txt_Lname;

    @FXML
    private TextField txt_child;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextField txt_parent;

    @FXML
    private TextField txt_payAmount;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private ImageView imgView;



    Connection con = dbConnection.connection();
    ResultSet rs;
    PreparedStatement ps;
    static String  name;



    public void clearAll() {

        txt_fname.setText("");
        txt_Lname.setText("");
        txt_parent.setText("");
        txt_child.setText("");
        txt_payAmount.setText("");
        label_balance.setText("xxxx");
        label_total.setText("xxxx");
    }



    public void switchForm(ActionEvent event){

        if(event.getSource() == btn_ticket){
            anc_liveFeed.setVisible(false);
            anc_map.setVisible(false);
            anc_ticket.setVisible(true);
        } else if (event.getSource() == btn_live) {
            anc_liveFeed.setVisible(true);
            anc_map.setVisible(false);
            anc_ticket.setVisible(false);
        }else if (event.getSource() == btn_map){
            anc_liveFeed.setVisible(false);
            anc_map.setVisible(true);
            anc_ticket.setVisible(false);
        }

    }
    public void logout() {
        try {

            String logoutuser = "DELETE FROM current_cus LIMIT 1";
            PreparedStatement logoutuserps = con.prepareStatement(logoutuser);
            logoutuserps.execute();


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
                Image icon=new Image("/logo.png");
                stage.getIcons().add(icon);
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
    public void close() {

        System.exit(0);
    }

    public void calculate() {//calculate cost based on members, no sql quarres execute in this function



        if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
            Alert alert;

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill empaty text field!");
            alert.showAndWait();

        }else {


            //int numParent =parseInt(txt_parent.getText());
            int numParent = Integer.parseInt(txt_parent.getText());
            int numChild = Integer.parseInt(txt_child.getText());

            int total = numParent * 500 + numChild * 200;

            label_total.setText(String.valueOf(total));

        }
    }

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
        @FXML
        private Button btn_pay; // Add a reference to the pay button

        public PaymentHandler(Connection con, Button payButton) {
            this.con = con;
            this.btn_pay = payButton;
        }




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


        //    public void pay() throws SQLException {//making balance and quereis execute for two tables


//        public void payment() throws SQLException {
//            System.out.println("pay function");
//
//            if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("ERROR Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Fill empty text field!");
//                alert.showAndWait();
//            } else {
//                int payment = Integer.parseInt(txt_payAmount.getText());
//                int numParent = Integer.parseInt(txt_parent.getText());
//                int numChild = Integer.parseInt(txt_child.getText());
//                int total = numParent * 500 + numChild * 200;
//
//                label_balance.setText(String.valueOf(payment - total));
//
//                String sql = "INSERT INTO `ticket` ( `Fname`, `Lname`, `parentCount`, `kidCount`) VALUES (?, ?, ?, ?)";
//                String sql2 = "INSERT INTO `revenue` (`Fname`, `total`) VALUES (?, ?)";
//
//                try {
//                    con.setAutoCommit(false); // Disable auto-commit
//
//                    ps = con.prepareStatement(sql);
//                    ps.setString(1, txt_fname.getText());
//                    ps.setString(2, txt_Lname.getText());
//                    ps.setInt(3, Integer.parseInt(txt_parent.getText()));
//                    ps.setInt(4, Integer.parseInt(txt_child.getText()));
//                    ps.executeUpdate();
//
//                    ps = con.prepareStatement(sql2);
//                    ps.setString(1, txt_fname.getText());
//                    ps.setInt(2, total);
//                    ps.executeUpdate();
//
//                    System.out.println("payment is " + payment + " total is " + total);
//
//                    if (payment < total) {
//                        con.rollback(); // Rollback if payment is insufficient
//
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("ERROR Message");
//                        alert.setHeaderText(null);
//                        alert.setContentText("payment not sufficient!");
//                        alert.showAndWait();
//
//                    } else {
//                        con.commit(); // Commit if payment is sufficient
//
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    if (con != null) {
//                        try {
//                            con.rollback(); // Rollback in case of an exception
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                } finally {
//                    // Ensure the button is re-enabled after the operation
//                    Platform.runLater(() -> btn_pay.setDisable(false));
//                }
//            }
//
//            // Disable the button for 10 seconds to prevent multiple submissions
//            btn_pay.setDisable(true);
//            new Thread(() -> {
//                try {
//                    Thread.sleep(10000); // 10 seconds
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Platform.runLater(() -> btn_pay.setDisable(false)); // Re-enable the button
//            }).start();
//        }


    }
    //......................................................using thread....
    //code end
public void clearForm(){
        clearAll();
}
    //Live Feed



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lebal_name.setText(name);
    }
}
