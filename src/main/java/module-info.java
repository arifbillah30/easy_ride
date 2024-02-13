module com.example.easy_ride {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.easy_ride to javafx.fxml;
    exports com.example.easy_ride;
}