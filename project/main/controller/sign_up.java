package controller;

import includes.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class sign_up {

    public void initialize() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Format the time in 12-hour format with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        // Update the time label text
        timeLabel.setText(formattedTime);

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

    @FXML
    private Label timeLabel;
    public ChoiceBox<String> countryChoiceBox;
    public Label lblErrors;

    public TextField signup_user;
    public TextField signup_email;

    public TextField signup_phone;

    public TextField signup_pass;

    public Button btnSignup;


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    public void register() {
        String status = "Success";
        con = dbconnect.conDB();

        String username = signup_user.getText();
        String email = signup_email.getText();
        String phone = signup_phone.getText();
        String password = signup_pass.getText();

        if(username.isEmpty()||email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        }

        else {

            String sql = "SELECT * FROM users Where email = ? and password = ?";
            try {

                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
    }






    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public void Back(ActionEvent event) throws IOException {

        // Load the FXML file for the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/enable_location.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



}
