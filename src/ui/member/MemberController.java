package ui.member;

import dao.PaymentDAO;
import dao.TicketDAO;
import dao.FeedbackDAO;
import entities.Payment;
import entities.Ticket;
import entities.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class MemberController {
    @FXML private TextField amountField;
    @FXML private ComboBox<String> methodBox;
    @FXML private Label confirmationLabel;
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML private TableColumn<Ticket, Integer> eventColumn;
    @FXML private TableColumn<Ticket, Date> dateColumn;
    @FXML private TextField commentField;
    @FXML private TextField ratingField;

    private PaymentDAO paymentDAO = new PaymentDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    private FeedbackDAO feedbackDAO = new FeedbackDAO();
    private ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    private int userId = 1;  // 🔹 Change this to the actual logged-in user ID

    @FXML
    public void initialize() {
        // ✅ Set Payment Method Options Dynamically
        methodBox.setItems(FXCollections.observableArrayList(
                "Credit Card", "PayPal", "Bank Transfer"
        ));

        // ✅ Set up Ticket Table Columns
        ticketIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        eventColumn.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().issueDateProperty());

        // ✅ Load existing tickets on startup
        loadTicketsForUser(userId);
    }

    @FXML
    public void handleConfirmPayment() {
        try {
            // ✅ Validate Amount
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                confirmationLabel.setText("❌ Amount is required!");
                confirmationLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    confirmationLabel.setText("❌ Amount must be greater than zero!");
                    confirmationLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            } catch (NumberFormatException e) {
                confirmationLabel.setText("❌ Invalid amount format!");
                confirmationLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // ✅ Validate Payment Method
            String method = methodBox.getValue();
            if (method == null || method.isEmpty()) {
                confirmationLabel.setText("❌ Please select a payment method!");
                confirmationLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // ✅ Automatically assign an event (Change this logic if needed)
            int eventId = ticketDAO.getNextAvailableEvent();
            if (eventId == -1) {
                confirmationLabel.setText("❌ No available events!");
                confirmationLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // ✅ Step 1: Create a new ticket
            Ticket newTicket = new Ticket(0, eventId, userId, new Date(System.currentTimeMillis()));
            ticketDAO.addTicket(newTicket);

            // ✅ Step 2: Fetch the latest ticket ID for this user
            Ticket latestTicket = ticketDAO.getLatestTicketForUser(userId);
            if (latestTicket == null) {
                confirmationLabel.setText("❌ Ticket creation failed!");
                confirmationLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // ✅ Step 3: Create the payment entry linked to the new ticket
            Payment newPayment = new Payment(0, latestTicket.getId(), amount, method, new Date(System.currentTimeMillis()));
            paymentDAO.addPayment(newPayment);

            // ✅ Step 4: Show success message
            confirmationLabel.setText("✅ Payment successful!");
            confirmationLabel.setStyle("-fx-text-fill: green;");

            // ✅ Step 5: Refresh the ticket table
            loadTicketsForUser(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTicketsForUser(int userId) {
        ticketList.clear();
        List<Ticket> tickets = ticketDAO.getAllTicketsForUser(userId); // Fetch only this user's tickets
        ticketList.addAll(tickets);
        ticketTable.setItems(ticketList);
    }

    @FXML
    public void handleSubmitFeedback() {
        int eventId = Integer.parseInt(commentField.getText());
        String comment = commentField.getText();
        int rating = Integer.parseInt(ratingField.getText());
        Feedback feedback = new Feedback(0, userId, eventId, comment, rating);
        feedbackDAO.addFeedback(feedback);
    }
}

