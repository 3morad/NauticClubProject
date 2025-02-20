package ui.instructor;

import dao.PaymentDAO;
import entities.Payment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class InstructorController {
    @FXML private TableView<Payment> paidTicketsTable;
    @FXML private TableColumn<Payment, Integer> ticketIdColumn;
    @FXML private TableColumn<Payment, Double> amountColumn;
    @FXML private TableColumn<Payment, String> methodColumn;
    @FXML private TableColumn<Payment, Date> dateColumn; // Corrected type

    private PaymentDAO paymentDAO = new PaymentDAO();
    private ObservableList<Payment> paidTicketsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // ✅ Ensure Table Columns are properly linked to properties
        ticketIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTicketId()));
        amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
        methodColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMethod()));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));

        // ✅ Load paid tickets
        loadPaidTickets();
    }

    private void loadPaidTickets() {
        paidTicketsList.clear();
        List<Payment> payments = paymentDAO.getAllPayments();  // ✅ Correctly fetching payments
        paidTicketsList.addAll(payments);
        paidTicketsTable.setItems(paidTicketsList);
    }
}

