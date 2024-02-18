package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

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

}
