module com.example.otherpc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.otherpc to javafx.fxml;
    exports com.example.otherpc;
}