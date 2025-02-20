package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    @FXML private ChoiceBox<String> roleSelection;
    @FXML private Button loginButton;

    @FXML
    public void initialize() {
        roleSelection.getItems().addAll("Admin", "Member", "Instructor");
        roleSelection.setValue("Admin"); // Default selection
    }

    @FXML
    private void handleLogin() {
        String selectedRole = roleSelection.getValue();
        if (selectedRole != null) {
            switch (selectedRole) {
                case "Admin":
                    openNewWindow("/ui/admin/AdminView.fxml", "Admin Dashboard");
                    break;
                case "Member":
                    openNewWindow("/ui/member/MemberView.fxml", "Member Dashboard");
                    break;
                case "Instructor":
                    openNewWindow("/ui/instructor/InstructorView.fxml", "Instructor Dashboard");
                    break;
            }
        }
    }

    private void openNewWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
