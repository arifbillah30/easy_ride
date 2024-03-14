module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;

    requires javafx.web;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;

    // add icon pack modules
    requires org.kordamp.ikonli.fontawesome5;


    opens application to javafx.fxml;
    opens controller to javafx.fxml;

    exports application;
    exports controller;
    exports controller.DashboardController;
    opens controller.DashboardController to javafx.fxml;
}