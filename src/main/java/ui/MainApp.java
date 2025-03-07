package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/views/MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            primaryStage.setTitle("Nautic Club Management");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Get the controller and set the MainApp reference
            MainController controller = fxmlLoader.getController();
            controller.setMainApp(this);  // Pass MainApp to the controller
        } catch (IOException e) {
            System.err.println("❌ Error loading MainView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
