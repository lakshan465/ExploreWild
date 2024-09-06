module com.example.demo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires javafx.web;
//    requires javafx.web;



    opens lk.ruhcs.explorewild to javafx.fxml;
    exports lk.ruhcs.explorewild;
}