package com.example.demo3;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ZooKeeperController  implements Initializable {


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
    private TableColumn<?, ?> description_col;

    @FXML
    private Label label_balance;

    @FXML
    private Label label_total;

    @FXML
    private Label lebal_name;

    @FXML
    private TableColumn<?, ?> status_col;

    @FXML
    private TableView<?> table_task;

    @FXML
    private TableColumn<?, ?> taskid_col;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

