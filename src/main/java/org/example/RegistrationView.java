package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.dao.RegistrationDAO;
import org.example.models.Registration;

import java.util.List;

public class RegistrationView {

    private VBox root;               // Main layout
    private TextField txtMemberId, txtEventId;
    private Button btnRegister;
    private Label lblStatus;
    private TableView<Registration> table;

    private RegistrationDAO regDAO = new RegistrationDAO();

    public RegistrationView() {
        initializeUI();
        loadRegistrations();
    }

    /**
     * Builds the UI and layout.
     */
    private void initializeUI() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Form fields for registration
        txtMemberId = new TextField();
        txtMemberId.setPromptText("Member ID");

        txtEventId = new TextField();
        txtEventId.setPromptText("Event ID");

        btnRegister = new Button("Register");
        lblStatus = new Label();

        // Table to display registrations
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Example columns: Member ID, Event ID
        // If your Registration model has an 'id' or 'registrationDate', add more columns.
        TableColumn<Registration, Number> colMember = new TableColumn<>("Member ID");
        colMember.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getMemberId()));

        TableColumn<Registration, Number> colEvent = new TableColumn<>("Event ID");
        colEvent.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEventId()));

        // If you have more fields in Registration, add columns here
        // e.g., date column, status, etc.

        table.getColumns().addAll(colMember, colEvent);

        // Layout
        VBox form = new VBox(5, txtMemberId, txtEventId, btnRegister, lblStatus);
        root.getChildren().addAll(form, table);

        // Handler for registration
        btnRegister.setOnAction(e -> registerMember());
    }

    /**
     * Calls the DAO to register a member for an event.
     */
    private void registerMember() {
        try {
            int memberId = Integer.parseInt(txtMemberId.getText().trim());
            int eventId = Integer.parseInt(txtEventId.getText().trim());

            regDAO.registerMember(memberId, eventId);
            lblStatus.setText("Member " + memberId + " registered for event " + eventId + "!");
            clearFields();
            loadRegistrations();
        } catch (NumberFormatException ex) {
            lblStatus.setText("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Loads registrations from the DAO and updates the table.
     */
    private void loadRegistrations() {
        List<Registration> registrations = regDAO.getAllRegistrations();
        ObservableList<Registration> obsList = FXCollections.observableArrayList(registrations);
        table.setItems(obsList);
    }

    private void clearFields() {
        txtMemberId.clear();
        txtEventId.clear();
    }

    /**
     * Returns the main layout so we can embed this view in a Tab or other parent.
     */
    public VBox getRoot() {
        return root;
    }
}
