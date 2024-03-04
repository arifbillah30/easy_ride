package controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private Label lblLoading;

    @FXML
    private ImageView logoImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startBackgroundTasks();
        applyFadeTransition();
        applyRotationTransition();

    }

    private void startBackgroundTasks() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Your Journey Starts Here.");
                Thread.sleep(1500);

                updateMessage("Ride with Ease, Easy Ride.");
                Thread.sleep(1500);

                updateMessage("Effortless Travel Awaits.");
                Thread.sleep(1500);
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

    private void applyFadeTransition() {
        FadeTransition fade = new FadeTransition(Duration.seconds(5.0), logoImage);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }


    private void applyRotationTransition() {
        RotateTransition rotate = new RotateTransition(Duration.seconds(2), logoImage);
        rotate.setFromAngle(0);
        rotate.setToAngle(360); // Full rotation
        rotate.setCycleCount(1); // Rotate once
        rotate.play();
    }

}
