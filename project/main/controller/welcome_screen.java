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

public class welcome_screen {

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

    public void ButtonCreate(ActionEvent event) throws IOException {

        // Load the FXML file for the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/sign_up.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void ButtonSignIn(ActionEvent event) throws IOException {

        // Load the FXML file for the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/sign_in.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
