package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    private MainApp mainApp;  // Reference to MainApp

    // ✅ Add this method to allow MainApp to pass itself
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // ✅ This method opens the Equipment Management Interface
    @FXML
    private void handleManageEquipment() {
        loadInterface("/ui/views/EquipmentInterface.fxml", "Manage Equipment");
    }

    // ✅ This method opens the Rentals Interface
    @FXML
    private void handleViewRentals() {
        loadInterface("/ui/views/RentalInterface.fxml", "View Rentals");
        System.out.println(getClass().getResource("/ui/views/RentalInterface.fxml"));
    }

    // ✅ This method handles the "Exit" button click
    @FXML
    private void handleExit() {
        javafx.application.Platform.exit();
    }

    // ✅ Helper method to load a new FXML interface and initialize its controller
    private void loadInterface(String fxmlPath, String title) {
        // Debug: Print the resource URL for verification
        java.net.URL resource = getClass().getResource(fxmlPath);
        System.out.println("Loading FXML from: " + resource);
        if (resource == null) {
            System.err.println("❌ FXML file not found at path: " + fxmlPath);
            return;  // Stop execution if the file is missing
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // Get the controller of the loaded FXML
            Object controller = loader.getController();

            // Pass the MainApp reference to the controller if needed
            if (controller instanceof RentalController) {
                ((RentalController) controller).setMainApp(mainApp);
            } else if (controller instanceof EquipmentController) {
                ((EquipmentController) controller).setMainApp(mainApp);
            }

            // Open in a new window
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            System.err.println("❌ Error loading " + title + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
