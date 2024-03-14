package controller.DashboardController;

import controller.sign_in;
import javafx.fxml.FXML;
import includes.dbconnect;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class dashboard {

    String UserId;

    private Connection con = null;

    @FXML
    private Label timeLabel;

    @FXML
    private Label SetName;

    private double lastX = 0;
    private double currentX = 0;

    public void initialize() {

        // Get the current time
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        timeLabel.setText(formattedTime);

        con = dbconnect.conDB();

    }

    public void setSession(sign_in.SessionController session) {
        UserId = session.getUserId();
         fetchAndSetUserName();
    }

    private void fetchAndSetUserName() {
        String sql;
        if (UserId.startsWith("0")) {
            sql = "SELECT id,email,username FROM users WHERE id = ?";
        } else {
            sql = "SELECT email,id,username FROM users WHERE email = ?";
        }

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, UserId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("username");
                SetName.setText("Welcome, " + name + "!");
            } else {
                SetName.setText("Welcome! (User not found)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
