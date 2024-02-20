package controller;

import includes.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up {

    @FXML
    private ChoiceBox<String> countryChoiceBox;

    @FXML
    private CheckBox checkbox;
    @FXML
    private Label timeLabel;
   @FXML
    private TextField signup_id;
    @FXML
    private TextField signup_user;
    @FXML
    private TextField signup_email;

    @FXML
    private TextField signup_phone;
    @FXML
    private ChoiceBox<String> signup_gender;
    @FXML
    private Label lblErrors;

    // Database connection
    private Connection con = null;

    public void initialize() {
        // Establish database connection
        con = dbconnect.conDB();
        if (con == null) {
            setLblError(Color.TOMATO, "Server Error");
        } else {
            setLblError(Color.GREEN, "Server is up");
        }
        signup_gender.getItems().addAll("Male", "Female", "Other");
        // Set default gender value
        signup_gender.setValue("Male");

        // Update time label
        updateTimeLabel();
        BD();
    }

    // Method to update time label
    private void updateTimeLabel() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);
        timeLabel.setText(formattedTime);
    }

    private void BD(){

        countryChoiceBox.getSelectionModel().select("BD");
        signup_phone.setText("+880");

        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the selected country is "BD"
            if ("BD".equals(newValue)) {
                // Update the text of the phone TextField with the country code
                signup_phone.setText("+880");
            } else {
                // Clear the phone TextField if another country is selected
                signup_phone.clear();
            }
        });
    }



    // Method to set error label
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
    }

    @FXML
    public void signup(ActionEvent event) {
        String username = signup_user.getText();
        String email = signup_email.getText();
        String id = signup_id.getText();
        String phone = signup_phone.getText();
        String gender = signup_gender.getValue();

        // Validate input fields
        if (username.isEmpty() || email.isEmpty() || id.isEmpty() || phone.isEmpty() || gender == null) {
            setLblError(Color.TOMATO, "Please fill in all fields");
            return;
        }



        if (!isValidId(id)) {
            setLblError(Color.TOMATO, "ID must start with 0 and have 9 digits");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            setLblError(Color.TOMATO, "Invalid email format");
            return;
        }

        // Validate phone number format



        if (!isValidPhone(phone)) {
            setLblError(Color.TOMATO, "Invalid phone number format");
            return;
        }


        if (!checkbox.isSelected()) {
            setLblError(Color.TOMATO, "Please agree to the terms and conditions.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/verify_otp.fxml"));
            Parent root = loader.load();

            // Pass user information to verify_otp controller
            verify_otp verifyOtpController = loader.getController();
            verifyOtpController.receiveUserData(id,username, email, phone,gender);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle FXML loading error
        }
    }


    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate phone number format
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^\\+880\\d{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isValidId(String id) {
        String IdRegex = "^0\\d{8}$";
        Pattern pattern = Pattern.compile(IdRegex);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }



    @FXML
    public void sign_in(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/sign_in.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();    }


}
