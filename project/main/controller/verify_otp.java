package controller;

import includes.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class verify_otp {

    @FXML
    private Label timeLabel;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField otp1;

    @FXML
    private TextField otp2;

    @FXML
    private TextField otp3;

    @FXML
    private TextField otp4;

    @FXML
    private TextField otp5;

    // User information variables
    private String username;
    private String email;
    private String id;
    private String phone;
    private String gender;


    public void initialize() {
        updateTimeLabel();

        SingleDigit(otp1);
        SingleDigit(otp2);
        SingleDigit(otp3);
        SingleDigit(otp4);
        SingleDigit(otp5);

        otp1.setOnKeyReleased(event -> moveToNextField(otp1, otp2));
        otp2.setOnKeyReleased(event -> moveToNextField(otp2, otp3));
        otp3.setOnKeyReleased(event -> moveToNextField(otp3, otp4));
        otp4.setOnKeyReleased(event -> moveToNextField(otp4, otp5));


    }

    private void updateTimeLabel() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);
        timeLabel.setText(formattedTime);
    }
    private void SingleDigit(TextField textField) {

        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!Character.isDigit(event.getCharacter().charAt(0))) {
                event.consume(); // Ignore non-digit characters
            }
            if (textField.getText().length() >= 1) {
                event.consume(); // Ignore additional input
            }
        });
    }

    private void moveToNextField(TextField currentField, TextField nextField) {
        if (currentField.getText().length() == 1) {
            nextField.requestFocus();
        }
    }
    private boolean verifyOTP() {
        String enteredOTP = otp1.getText() + otp2.getText() + otp3.getText() + otp4.getText() + otp5.getText();

        String expectedOTP = "12345";
        return enteredOTP.equals(expectedOTP);
    }

    @FXML
    void sign_in(ActionEvent event) {
        boolean isVerified = verifyOTP();
        if (isVerified) {
            System.out.println("OTP verification successful.");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/set_password.fxml"));
                Parent root = loader.load();

                // Pass user information to set_password controller
                set_password setPasswordController = loader.getController();
                setPasswordController.receiveUserData(id, username, email, phone, gender);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle FXML loading error
            }
        } else {
            lblErrors.setText("Incorrect OTP. Please enter the correct OTP.");
        }
    }

    // Method to receive user data from sign_up controller
    public void receiveUserData(String id, String username, String email, String phone, String gender) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }
}
