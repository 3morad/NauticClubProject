package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.dao.EventDAO;
import org.example.models.Event;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventsCRUDView {

    private VBox root;                      // The main layout
    private TableView<Event> table;         // Table to display events
    private TextField txtName, txtDesc, txtPrice, txtDate, txtLocationId;
    private Button btnCreate, btnUpdate, btnDelete;
    private Label lblStatus;

    private EventDAO eventDAO = new EventDAO();

    public EventsCRUDView() {
        initializeUI();
        loadEvents();
    }

    /**
     * Builds the UI controls and layout.
     */
    private void initializeUI() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Form fields
        txtName = new TextField();
        txtName.setPromptText("Name");

        txtDesc = new TextField();
        txtDesc.setPromptText("Description");

        txtPrice = new TextField();
        txtPrice.setPromptText("Price (double)");

        txtDate = new TextField();
        txtDate.setPromptText("Date (YYYY-MM-DD)");

        txtLocationId = new TextField();
        txtLocationId.setPromptText("Location ID");

        // Buttons
        btnCreate = new Button("Create");
        btnUpdate = new Button("Update");
        btnDelete = new Button("Delete");

        lblStatus = new Label();

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Event, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Event, String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Event, Number> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()));

        TableColumn<Event, String> colDate = new TableColumn<>("Event Date");
        colDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEventDate().toString()));

        TableColumn<Event, Number> colLoc = new TableColumn<>("Location ID");
        colLoc.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getLocationId()));

        table.getColumns().addAll(colName, colDesc, colPrice, colDate, colLoc);

        // Layout
        VBox form = new VBox(5,
                txtName, txtDesc, txtPrice, txtDate, txtLocationId,
                btnCreate, btnUpdate, btnDelete,
                lblStatus
        );

        root.getChildren().addAll(form, table);

        // Event handlers
        btnCreate.setOnAction(e -> createEvent());
        btnUpdate.setOnAction(e -> updateEvent());
        btnDelete.setOnAction(e -> deleteEvent());

        // When a table row is selected, load data into form fields
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtName.setText(newVal.getName());
                txtDesc.setText(newVal.getDescription());
                txtPrice.setText(String.valueOf(newVal.getPrice()));
                txtDate.setText(newVal.getEventDate().toString());
                txtLocationId.setText(String.valueOf(newVal.getLocationId()));
            }
        });
    }

    private void createEvent() {
        try {
            String name = txtName.getText().trim();
            String desc = txtDesc.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            LocalDate date = LocalDate.parse(txtDate.getText().trim());
            int locationId = Integer.parseInt(txtLocationId.getText().trim());

            Event event = new Event(name, desc, price, date, locationId);
            eventDAO.createEvent(event);

            lblStatus.setText("Event created successfully!");
            clearFields();
            loadEvents();
        } catch (NumberFormatException | DateTimeParseException ex) {
            lblStatus.setText("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void updateEvent() {
        Event selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No event selected for update.");
            return;
        }
        try {
            selected.setName(txtName.getText().trim());
            selected.setDescription(txtDesc.getText().trim());
            selected.setPrice(Double.parseDouble(txtPrice.getText().trim()));
            selected.setEventDate(LocalDate.parse(txtDate.getText().trim()));
            selected.setLocationId(Integer.parseInt(txtLocationId.getText().trim()));

            eventDAO.updateEvent(selected);
            lblStatus.setText("Event updated successfully!");
            clearFields();
            loadEvents();
        } catch (NumberFormatException | DateTimeParseException ex) {
            lblStatus.setText("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void deleteEvent() {
        Event selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No event selected for delete.");
            return;
        }
        try {
            eventDAO.deleteEvent(selected.getId());
            lblStatus.setText("Event deleted successfully!");
            clearFields();
            loadEvents();
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void loadEvents() {
        List<Event> events = eventDAO.getAllEvents();
        ObservableList<Event> obsList = FXCollections.observableArrayList(events);
        table.setItems(obsList);
    }

    private void clearFields() {
        txtName.clear();
        txtDesc.clear();
        txtPrice.clear();
        txtDate.clear();
        txtLocationId.clear();
    }

    /**
     * This method returns the main layout (VBox) for this view,
     * so it can be placed in a Tab or another parent container.
     */
    public VBox getRoot() {
        return root;
    }
}
