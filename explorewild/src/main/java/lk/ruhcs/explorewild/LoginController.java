package lk.ruhcs.explorewild;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * @author l3n
 */
public class LoginController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button loginBtn1;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private PasswordField pwdTxt;

    @FXML
    private TextField unameTxt;

//    private Connection connection;
//    private PreparedStatement prepare;
//    private ResultSet result;

    public void close() {

        System.exit(0);
    }

    public void adminLogin() {
        try {
            int flag=0;

            if (unameTxt.getText().isEmpty() || pwdTxt.getText().isEmpty()) {
                //Forced to Fill all text box

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage !");
                alert.setHeaderText(null);
                alert.setContentText("Fill all text box please !");
                alert.showAndWait();
            } else {
                //after filling all text box checking weather enter data exist or not database
                String sqlForAdmin = "SELECT * from admin WHERE username = ? and password = ?";
                String sqlForStaff = "SELECT * from staff WHERE username = ? and password = ?";
                String sqlForUser = "SELECT * from user WHERE username = ? and password = ?";

                Connection conn = dbConnection.connection();

                PreparedStatement preAdmin = conn.prepareStatement(sqlForAdmin);
                PreparedStatement preStaff = conn.prepareStatement(sqlForStaff);
                PreparedStatement preUser = conn.prepareStatement(sqlForUser);

                //String uname= unameTxt.getText();
                //String pwd=getHashPwd(pwdTxt.getText());

                preAdmin.setString(1, unameTxt.getText());
                preAdmin.setString(2, getHashPwd(pwdTxt.getText()) );

                preStaff.setString(1, unameTxt.getText());
                preStaff.setString(2,getHashPwd(pwdTxt.getText()));
                //System.out.println("sff");
                preUser.setString(1, unameTxt.getText());
                preUser.setString(2, getHashPwd(pwdTxt.getText()));


                ResultSet resultAdmin = preAdmin.executeQuery();
//                System.out.println("before if "+resultAdmin.next());
//                boolean ra=resultAdmin.next();
//                System.out.println(ra);
                if (resultAdmin.next()) {
                    System.out.println("resultAdmin.next() is ok");
                    flag++;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    System.out.println("You login as an Admin!");
                    alert.setContentText("You login as an Admin!");
                    alert.showAndWait();


                    //based on username admin name wil change
                    AdminController.name= unameTxt.getText();


                    //hide login window
                    loginBtn.getScene().getWindow().hide();
                    //preAdmin.close();

                    //load admin window with alert
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk.ruhcs.explorewild/Admin.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 950, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                }


                ResultSet resultStaff = preStaff.executeQuery();
                if (resultStaff.next()) {

                    String sqls = "INSERT INTO `current_keepers2` (`id`) VALUES (NULL)";
                    PreparedStatement prelives = conn.prepareStatement(sqls);
                    prelives.execute();
                    flag++;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("You login as a Staff member!");
                    alert.showAndWait();
                    //resultStaff.close();
                    //preStaff.close();
                    //load staff window with alert

                    ZooKeeperController.name = unameTxt.getText();

                    //hide login window
                    loginBtn.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk.ruhcs.explorewild/CoWorker.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);

                }

                ResultSet resultUser = preUser.executeQuery();
                if (resultUser.next()) {
                    String sqlu = "INSERT INTO `current_cus` (`id`) VALUES (NULL)";
                    PreparedStatement preliveu = conn.prepareStatement(sqlu);
                    preliveu.execute();

                    flag++;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    System.out.println("You login as Customer!");
                    alert.setContentText("You login as Customer!");
                    alert.showAndWait();
                    //load user window with alert

                    CuzController.name= unameTxt.getText();//this take the username and pass that to customer class to show in the interface

                    //hide login window
                    loginBtn.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk.ruhcs.explorewild/Cuz.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                }
                if( flag==0){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error massage !");
                    alert.setHeaderText(null);
                    alert.setContentText("Enterd UserName and Password not match!");
                    alert.showAndWait();
                    unameTxt.setText("");
                    pwdTxt.setText("");
                }
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            System.out.println(sb.toString());
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void registerFormLoad() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/lk.ruhcs.explorewild/RegForm.fxml"));
        Scene scene=new Scene(loader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

        loginBtn1.getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}