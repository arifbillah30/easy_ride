module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;

    opens application to javafx.fxml;
    opens controller to javafx.fxml;

    exports application;
    exports controller;
}