module com.example.circusschool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;
    requires java.desktop;
    requires jasperreports;
    //requires javax.mail.api;

    opens com.example.circusschool to javafx.fxml;
    exports com.example.circusschool;
}