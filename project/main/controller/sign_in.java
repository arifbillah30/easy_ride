package controller;

import controller.DashboardController.dashboard;
import includes.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class sign_in {



    @FXML
    private TextField userInfo;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label lblErrors;

    @FXML
    private Label timeLabel;

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
        // Update time label
        updateTimeLabel();


    }

    private void updateTimeLabel() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);
        timeLabel.setText(formattedTime);
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
    }


    public void signInBtn() {
        String userInput = userInfo.getText();
        String password = passwordField.getText();

        // Check if the username/email and password fields are not empty
        if (userInput.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Please enter username/email and password.");
            return;
        }

        try {
            // Establish a connection to the database
            Connection con = dbconnect.conDB();
            if (con == null) {
                setLblError(Color.TOMATO, "Database connection error.");
                return;
            }

            // Prepare the SQL query to fetch user data based on username or email
            String sql;
            if (userInput.startsWith("0")) {
                // Treat as ID
                sql = "SELECT id,email, password FROM users WHERE id = ?";
            } else {

                // Treat as email
                sql = "SELECT email,id, password FROM users WHERE email = ?";

            }

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInput);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                // Extract the stored password
                String storedPassword = resultSet.getString("password");

                // Check if the entered password matches the stored password
                if (password.equals(storedPassword)) {


                    SessionController session = new SessionController();
                    session.setUserId(resultSet.getString("id"));
                    dashboard(session);

                } else {

                    setLblError(Color.TOMATO, "Incorrect password. Please try again.");
                }
            } else {
                setLblError(Color.TOMATO, "User not found. Please check your username/email.");

            }

            // Close the database resources
            resultSet.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            setLblError(Color.TOMATO, "An error occurred. Please try again.");
        }
    }


    public class SessionController {
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        // ... other getters and setters
    }


    // Method to navigate to the dashboard scene
    @FXML
    private void dashboard(SessionController session) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard_fxml/dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        dashboard controller = loader.getController();
        controller.setSession(session);

        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void signUpBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        switchScene("/application/sign_up.fxml", stage);
    }

    private void switchScene(String fxmlPath, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
