package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class home extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/dashboard_fxml/dashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 360, 640);
        stage.setTitle("Easy Ride - Book your E-Rickshaw ");

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/ui_images/logo/favicon.png")).toExternalForm());
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
