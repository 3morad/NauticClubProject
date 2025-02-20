package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class MainController {
    @FXML private Button paymentsButton;
    @FXML private Button ticketsButton;
    @FXML private Button feedbackButton;

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

    @FXML
    private void openPaymentsView() {
        openNewWindow("/ui/PaymentView.fxml", "Manage Payments");
    }

    @FXML
    private void openTicketsView() {
        openNewWindow("/ui/TicketView.fxml", "Manage Tickets");
    }

    @FXML
    private void openFeedbackView() {
        openNewWindow("/ui/FeedbackView.fxml", "Manage Feedback");
    }
}
