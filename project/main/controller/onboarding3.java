package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class onboarding3 {

    @FXML
    private Label timeLabel;

    public void initialize() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Format the time in 12-hour format with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        // Update the time label text
        timeLabel.setText(formattedTime);
    }

    public void Home(ActionEvent event) throws IOException {

        // Load the FXML file for the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/onboarding.fxml"));
        Parent root = loader.load();

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the stage from the button's scene and set the new scene
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
