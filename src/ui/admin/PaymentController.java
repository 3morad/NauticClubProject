package ui.admin;

import dao.PaymentDAO;
import entities.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class PaymentController {
    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> idColumn;
    @FXML private TableColumn<Payment, Double> amountColumn;
    @FXML private TableColumn<Payment, String> methodColumn;
    @FXML private TextField amountField, methodField;

    private PaymentDAO paymentDAO = new PaymentDAO();
    private ObservableList<Payment> paymentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        methodColumn.setCellValueFactory(cellData -> cellData.getValue().methodProperty());

        loadPayments();
    }

    private void loadPayments() {
        paymentList.clear();
        List<Payment> payments = paymentDAO.getAllPayments();
        paymentList.addAll(payments);
        paymentTable.setItems(paymentList);
    }

    @FXML
    private void addPayment() {
        double amount = Double.parseDouble(amountField.getText());
        String method = methodField.getText();
        Payment newPayment = new Payment(0, 1, amount, method, new Date(System.currentTimeMillis()));
        paymentDAO.addPayment(newPayment);
        loadPayments();
    }

    @FXML
    private void updatePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            double amount = Double.parseDouble(amountField.getText());
            String method = methodField.getText();
            paymentDAO.updatePayment(selected.getId(), amount, method);
            loadPayments();
        }
    }

    @FXML
    private void deletePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            paymentDAO.deletePayment(selected.getId());
            loadPayments();
        }
    }
}
