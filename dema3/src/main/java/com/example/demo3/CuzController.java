package com.example.demo3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.lang.System.exit;

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
    private Button btn_map;

    @FXML
    private Button btn_pay;

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


    Connection con = dbConnection.connection();
    ResultSet rs;
    PreparedStatement ps;
    static String  name;


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
//        try {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Error Message");
//            alert.setHeaderText(null);
//            alert.setContentText("Are you want to logout ?");
//            Optional<ButtonType> option = alert.showAndWait();
//
//            if (option.get().equals(ButtonType.OK)) {
//                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//                Stage stage = new Stage();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//                System.out.println("logout");
//
//                //hide login window
//                btn_LogOut.getScene().getWindow().hide();
//            }
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

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

    public void pay() throws SQLException {//making balance and quereis execute for two tables


        if (txt_fname.getText().isEmpty() || txt_Lname.getText().isEmpty() || txt_parent.getText().isEmpty() || txt_child.getText().isEmpty()) {
            Alert alert;

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill empaty text field!");
            alert.showAndWait();

        }else {


            int payment = Integer.parseInt(txt_payAmount.getText());

            int numParent = Integer.parseInt(txt_parent.getText());
            int numChild = Integer.parseInt(txt_child.getText());

            int total = numParent * 500 + numChild * 200;

            label_balance.setText(String.valueOf(payment - total));

            String sql = "INSERT INTO `ticket` ( `Fname`, `Lname`, `parentCount`, `kidCount`) VALUES ( ?, ?, ?, ?)";
            String sql2 = "INSERT INTO `revenue` (`Fname`, `total`) VALUES (?, ?)";

            try {
                con.setAutoCommit(false); //step one
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {

                ps = con.prepareStatement(sql);
                ps.setString(1, txt_fname.getText());
                ps.setString(2, txt_Lname.getText());
                ps.setInt(3, Integer.parseInt(txt_parent.getText()));
                ps.setInt(4, Integer.parseInt(txt_child.getText()));

                ps.executeUpdate();

                ps = con.prepareStatement(sql2);
                ps.setString(1,txt_fname.getText());
                ps.setInt(2,total);

                ps.executeUpdate();
                con.commit(); //step two
            } catch (SQLException e) {
                e.printStackTrace();
                if(con!=null) {
                    try {
                        con.rollback(); //step three
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

    }

    //Live Feed

    public void playBtn() throws IOException {

        //WebView webView=new WebView();
        //webView.getEngine().load("https://www.youtube.com/watch?v=3szkFHfr6sA");

        //webView.setPrefSize(700,500);
        //Scene scene=new Scene(webView);
        Stage stage = new Stage();
        //stage.setScene(scene);
        //stage.show();


        //Stage.setScene(new Scene(webView));
       // Stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LiveFeed.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            stage.close();
        });


    }


//    con.close();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lebal_name.setText(name);
    }
}
