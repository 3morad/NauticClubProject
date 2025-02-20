package ui.admin;

import dao.FeedbackDAO;
import dao.PaymentDAO;
import dao.TicketDAO;
import entities.Feedback;
import entities.Payment;
import entities.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class AdminController {
    // ✅ Payment Controls
    @FXML private TextField amountField;
    @FXML private ComboBox<String> methodBox; // ✅ Fixed ComboBox reference
    @FXML private Button addPaymentButton;
    @FXML private Button updatePaymentButton;
    @FXML private Button deletePaymentButton;
    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> paymentIdColumn;
    @FXML private TableColumn<Payment, Double> paymentAmountColumn;
    @FXML private TableColumn<Payment, String> paymentMethodColumn;

    // ✅ Ticket Controls
    @FXML private TextField eventField;
    @FXML private TextField userField;
    @FXML private Button addTicketButton;
    @FXML private Button updateTicketButton;
    @FXML private Button deleteTicketButton;
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML private TableColumn<Ticket, Integer> ticketEventColumn;
    @FXML private TableColumn<Ticket, Integer> ticketUserColumn;

    // ✅ Feedback Controls
    @FXML private TextField feedbackUserField;
    @FXML private TextField feedbackEventField;
    @FXML private TextField commentField;
    @FXML private TextField ratingField;
    @FXML private Button addFeedbackButton;
    @FXML private Button updateFeedbackButton;
    @FXML private Button deleteFeedbackButton;
    @FXML private TableView<Feedback> feedbackTable;
    @FXML private TableColumn<Feedback, Integer> feedbackIdColumn;
    @FXML private TableColumn<Feedback, Integer> feedbackUserColumn;
    @FXML private TableColumn<Feedback, Integer> feedbackEventColumn;
    @FXML private TableColumn<Feedback, String> commentColumn;
    @FXML private TableColumn<Feedback, Integer> ratingColumn;

    // ✅ DAOs and ObservableLists
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final TicketDAO ticketDAO = new TicketDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    private final ObservableList<Payment> paymentList = FXCollections.observableArrayList();
    private final ObservableList<Ticket> ticketList = FXCollections.observableArrayList();
    private final ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // ✅ Set ComboBox options
        methodBox.setItems(FXCollections.observableArrayList("Credit Card", "PayPal", "Bank Transfer"));

        // ✅ Initialize Payment Table
        paymentIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        paymentAmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        paymentMethodColumn.setCellValueFactory(cellData -> cellData.getValue().methodProperty());
        loadPayments();

        // ✅ Initialize Ticket Table
        ticketIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ticketEventColumn.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());
        ticketUserColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        loadTickets();

        // ✅ Initialize Feedback Table
        feedbackIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        feedbackUserColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        feedbackEventColumn.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        loadFeedback();
    }

    // ✅ Payment Methods
    private void loadPayments() {
        paymentList.clear();
        List<Payment> payments = paymentDAO.getAllPayments();
        paymentList.addAll(payments);
        paymentTable.setItems(paymentList);
    }

    @FXML
    public void handleAddPayment() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String method = methodBox.getValue();

            if (method == null || method.isEmpty()) {
                showAlert("Error", "Please select a payment method.");
                return;
            }

            Payment payment = new Payment(0, 1, amount, method, new Date(System.currentTimeMillis()));
            paymentDAO.addPayment(payment);
            loadPayments();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount format.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdatePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String method = methodBox.getValue();

                if (method == null || method.isEmpty()) {
                    showAlert("Error", "Please select a payment method.");
                    return;
                }

                paymentDAO.updatePayment(selected.getId(), amount, method);
                loadPayments();
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid amount format.");
            }
        } else {
            showAlert("Error", "No payment selected.");
        }
    }

    @FXML
    public void handleDeletePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            paymentDAO.deletePayment(selected.getId());
            loadPayments();
        } else {
            showAlert("Error", "No payment selected.");
        }
    }

    // ✅ Ticket Methods
    private void loadTickets() {
        ticketList.clear();
        List<Ticket> tickets = ticketDAO.getAllTickets();
        ticketList.addAll(tickets);
        ticketTable.setItems(ticketList);
    }

    @FXML
    public void handleAddTicket() {
        try {
            int eventId = Integer.parseInt(eventField.getText());
            int userId = Integer.parseInt(userField.getText());
            Ticket ticket = new Ticket(0, eventId, userId, new Date(System.currentTimeMillis()));
            ticketDAO.addTicket(ticket);
            loadTickets();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input. Enter valid event and user IDs.");
        }
    }
    @FXML
    public void handleUpdateTicket() {
        Ticket selected = ticketTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int eventId = Integer.parseInt(eventField.getText());
                int userId = Integer.parseInt(userField.getText());
                ticketDAO.updateTicket(selected.getId(), new Date(System.currentTimeMillis()));
                loadTickets();
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid event/user ID.");
            }
        } else {
            showAlert("Error", "No ticket selected.");
        }
    }
    @FXML
    public void handleDeleteTicket() {
        Ticket selected = ticketTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ticketDAO.deleteTicket(selected.getId());
            loadTickets();
        } else {
            showAlert("Error", "No ticket selected.");
        }
    }

    // ✅ Feedback Methods
    private void loadFeedback() {
        feedbackList.clear();
        List<Feedback> feedbacks = feedbackDAO.getAllFeedback();
        feedbackList.addAll(feedbacks);
        feedbackTable.setItems(feedbackList);
    }

    @FXML
    public void handleAddFeedback() {
        try {
            int userId = Integer.parseInt(feedbackUserField.getText());
            int eventId = Integer.parseInt(feedbackEventField.getText());
            String comment = commentField.getText();
            int rating = Integer.parseInt(ratingField.getText());
            Feedback feedback = new Feedback(0, userId, eventId, comment, rating);
            feedbackDAO.addFeedback(feedback);
            loadFeedback();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input. Check fields.");
        }
    }
    @FXML
    public void handleUpdateFeedback() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String comment = commentField.getText();
                int rating = Integer.parseInt(ratingField.getText());

                feedbackDAO.updateFeedback(selected.getId(), comment, rating);
                loadFeedback();
                showAlert("Success", "Feedback updated successfully.");
            } catch (NumberFormatException e) {
                showAlert("Error", "Rating must be a number.");
            }
        } else {
            showAlert("Error", "No feedback selected.");
        }
    }

    @FXML
    public void handleDeleteFeedback() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            feedbackDAO.deleteFeedback(selected.getId());
            loadFeedback();
            showAlert("Success", "Feedback deleted successfully.");
        } else {
            showAlert("Error", "No feedback selected.");
        }
    }

    // ✅ Utility function to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


