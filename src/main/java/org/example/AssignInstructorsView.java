package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.models.Event;
import org.example.models.User;

import java.util.List;

public class AssignInstructorsView {

    private VBox root;
    private TableView<Event> tableEvents;
    private TableView<User> tableInstructors;
    private Button btnAssign;
    private Label lblStatus;

    private EventDAO eventDAO = new EventDAO();
    private UserDAO userDAO = new UserDAO(); // If you have a method for instructors

    public AssignInstructorsView() {
        initializeUI();
        loadEvents();
        loadInstructors();
    }

    private void initializeUI() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        tableEvents = new TableView<>();
        tableEvents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Event, String> colEName = new TableColumn<>("Event Name");
        colEName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        tableEvents.getColumns().add(colEName);

        tableInstructors = new TableView<>();
        tableInstructors.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> colUName = new TableColumn<>("Instructor Username");
        colUName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        tableInstructors.getColumns().add(colUName);

        btnAssign = new Button("Assign Instructor");
        lblStatus = new Label();

        HBox tablesBox = new HBox(10, tableEvents, tableInstructors);
        root.getChildren().addAll(tablesBox, btnAssign, lblStatus);

        btnAssign.setOnAction(e -> assignInstructor());
    }

    private void loadEvents() {
        List<Event> events = eventDAO.getAllEvents();
        ObservableList<Event> obsList = FXCollections.observableArrayList(events);
        tableEvents.setItems(obsList);
    }

    private void loadInstructors() {
        // If you have userDAO.getInstructorsOnly(), use it. Otherwise, getAllUsers() & filter
        List<User> instructors = userDAO.getInstructorsOnly();
        ObservableList<User> obsList = FXCollections.observableArrayList(instructors);
        tableInstructors.setItems(obsList);
    }

    private void assignInstructor() {
        Event selectedEvent = tableEvents.getSelectionModel().getSelectedItem();
        User selectedInstructor = tableInstructors.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            lblStatus.setText("No event selected.");
            return;
        }
        if (selectedInstructor == null) {
            lblStatus.setText("No instructor selected.");
            return;
        }

        try {
            eventDAO.assignInstructor(selectedEvent.getId(), selectedInstructor.getId());
            lblStatus.setText("Instructor assigned successfully!");
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    public VBox getRoot() {
        return root;
    }
}
