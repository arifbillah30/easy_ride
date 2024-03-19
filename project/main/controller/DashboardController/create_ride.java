package controller.DashboardController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class create_ride {
    @FXML
    private Label timeLabel;


    @FXML
    private Label SrcAddress;

    @FXML
    private Label DesAddress;

    @FXML
    private ChoiceBox<String> SelectPay;


    public void initialize() {

        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        timeLabel.setText(formattedTime);

        SelectPay.getItems().addAll( "Digital Payment", "Cash Payment");
        SelectPay.getSelectionModel().select(1);


    }


    public void setAddresses(String srcAddress, String desAddress) {
        SrcAddress.setText(srcAddress);
        DesAddress.setText(desAddress);
    }
    public void CreateRide(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard_fxml/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) timeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
