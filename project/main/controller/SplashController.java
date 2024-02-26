package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private Label lblLoading;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startBackgroundTasks();
    }

    private void startBackgroundTasks() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Your Journey Starts Here.");
                Thread.sleep(1500);

                updateMessage("Ride with Ease, Easy Ride.");
                Thread.sleep(1500);

                updateMessage("Effortless Travel Awaits.");
                loadMainStage();
                return null;
            }
        };

        lblLoading.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

    private void loadMainStage() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/onboarding/onboarding.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) lblLoading.getScene().getWindow();
                stage.hide();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
