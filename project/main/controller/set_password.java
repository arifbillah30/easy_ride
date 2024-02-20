package controller;

import includes.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class set_password {

    @FXML
    private PasswordField txtPass;

    @FXML
    private PasswordField verifiers;

    @FXML
    private Label lblErrors;

    public Label timeLabel;

    private String username;
    private String email;
    private String id;
    private String phone;
    private String gender;

    private Connection con = null;

    public void initialize() {
        con = dbconnect.conDB();
        if (con == null) {
            setLblError("Failed to connect to the database.");
        }
    }

    public void receiveUserData(String id, String username, String email, String phone, String gender) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    @FXML
    void savePassword(ActionEvent event) {
        String password = txtPass.getText();
        String confirmPassword = verifiers.getText();

        if (!isValidPassword(password)) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            setLblError("Passwords do not match.");
            return;
        }

        try {
            String sql = "INSERT INTO users (id, username, email, phone, gender, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                setLblError("Sign up successful. Password saved.");
            } else {
                setLblError("Failed to save password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            setLblError("Database error: " + e.getMessage());
        }

        try {
            navigateToScene("/application/sign_in.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
            setLblError("An error occurred. Please try again.");
        }
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            setLblError("Password must be at least 6 characters long.");
            return false;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");

        if (!hasUppercase || !hasLowercase || !hasDigit) {
            setLblError("Password must have 1 uppercase, 1 lowercase, 1 digit.");
            return false;
        }

        return true;
    }

    private void setLblError(String errorMessage) {
        lblErrors.setText(errorMessage);
        lblErrors.setVisible(true);
    }

    private void navigateToScene(String fxmlPath, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
