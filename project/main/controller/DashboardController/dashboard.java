package controller.DashboardController;

import controller.sign_in;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import includes.dbconnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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

    @FXML
    private Label DesAddressLabel;


    @FXML
    private ChoiceBox<String> SrcAddressSelect;

    @FXML
    private ChoiceBox<String> DesAddressSelect;

    @FXML
    private BorderPane borderPane;

    private boolean sliderIsVisible = true;




    public void initialize() {

        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        timeLabel.setText(formattedTime);

        con = dbconnect.conDB();

        SrcAddressSelect.getItems().addAll("United International University", "Natun Bazar");
        DesAddressSelect.getItems().addAll("United International University", "Natun Bazar","Sayeed Nagar" );
        SrcAddressSelect.getSelectionModel().select(0);

        DesAddressSelect.valueProperty().addListener((obs, oldVal, newVal) -> clearLabelIfSelected(DesAddressLabel));

        borderPane.setTranslateX(-borderPane.getPrefWidth());

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

    @FXML
    private void handleDesAddressSelect() {

        String pickup = DesAddressSelect.getValue();

        String drop_off = SrcAddressSelect.getValue();



        if (drop_off != null && pickup != null && !pickup.equals(drop_off)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard_fxml/create_ride.fxml"));
                Parent root = loader.load();

                    create_ride controller = loader.getController();

                    controller.setAddresses(SrcAddressSelect.getValue(), pickup);

                    Stage stage = (Stage) DesAddressSelect.getScene().getWindow(); // Get current stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearLabelIfSelected(Label label) {
        if (label.getText() != null && !label.getText().isEmpty()) {
            label.setText("");
        }
    }


    @FXML
    private void showSlider(ActionEvent event) {
        toggleSlider();
    }

    private void toggleSlider() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), borderPane);

        if (sliderIsVisible) {
            transition.setToX(200); // Slide in from the left
        } else {
            transition.setToX(borderPane.getParent(). prefWidth(400) - borderPane.getPrefWidth()); // Slide out to the right
        }

        transition.play();
        sliderIsVisible = !sliderIsVisible;
    }


}



