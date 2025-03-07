package ui;

import api.DynamicPricingAPI;
import api.RentalBookingAPI;
import dao.RentalDAO;
import dao.EquipmentDAO;
import entities.Rental;
import entities.Equipment;
import database.MyDatabase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RentalController {
    private MainApp mainApp;  // Reference to MainApp

    @FXML
    private TableView<Rental> rentalTable;
    @FXML
    private TableColumn<Rental, Integer> colRentalId;
    @FXML
    private TableColumn<Rental, String> colEquipment;
    @FXML
    private TableColumn<Rental, LocalDate> colStartDate;
    @FXML
    private TableColumn<Rental, LocalDate> colEndDate;

    @FXML
    private ChoiceBox<Equipment> choiceEquipment;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;

    private RentalDAO rentalDAO;
    private EquipmentDAO equipmentDAO;
    private ObservableList<Rental> rentalList = FXCollections.observableArrayList();
    private ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    // API instances
    private RentalBookingAPI rentalAPI;
    private DynamicPricingAPI dynamicPricingAPI;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        try {
            Connection conn = MyDatabase.getConnection();
            rentalDAO = new RentalDAO(conn);
            equipmentDAO = new EquipmentDAO(conn);
            rentalAPI = new RentalBookingAPI();  // Assuming a no-arg constructor exists
            dynamicPricingAPI = new DynamicPricingAPI();

            setupTableColumns();
            loadInitialData();
            loadUserRentals(getCurrentUserId());
        } catch (SQLException e) {
            showAlert("Database Error", "Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        colRentalId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEquipment.setCellValueFactory(new PropertyValueFactory<>("equipmentName")); // Ensure Rental has a getter for equipmentName
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        rentalTable.setItems(rentalList);
        choiceEquipment.setItems(equipmentList);
    }

    private void loadInitialData() {
        Platform.runLater(() -> {
            try {
                rentalList.setAll(rentalDAO.getAllRentals());
                equipmentList.setAll(equipmentDAO.getAvailableEquipment());
            } catch (SQLException e) {
                showAlert("Database Error", "Could not load data: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void loadUserRentals(int userId) {
        try {
            List<Rental> userRentals = rentalDAO.getRentalsByUser(userId);
            rentalTable.setItems(FXCollections.observableArrayList(userRentals));
        } catch (SQLException e) {
            showAlert("Database Error", "Could not load rentals: " + e.getMessage());
        }
    }

    @FXML
    private void addRental() {
        Equipment selectedEquipment = choiceEquipment.getSelectionModel().getSelectedItem();
        LocalDate startDate = dateStart.getValue();
        LocalDate endDate = dateEnd.getValue();

        if (selectedEquipment == null || startDate == null || endDate == null ||
                startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
            showAlert("Input Error", "Please select valid equipment and future dates where start date is before end date.");
            return;
        }

        // Calculate dynamic price using DynamicPricingAPI (assumes such a method exists)
        int dynamicPrice = (int) dynamicPricingAPI.calculateDynamicPrice(selectedEquipment, calculateDemandFactor(startDate, endDate));
        showAlert(Alert.AlertType.INFORMATION, "Dynamic Price", "The total price for this rental is: $" + dynamicPrice);

        int userId = getCurrentUserId(); // Replace with your authentication logic
        Rental newRental = new Rental(0, selectedEquipment.getId(), userId, startDate, endDate, "ACTIVE");
        try {
            rentalDAO.addRental(newRental);
            refreshData();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not add rental: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Dummy demand factor calculator; adjust based on your requirements
    private int calculateDemandFactor(LocalDate startDate, LocalDate endDate) {
        // For example, return a factor based on rental duration
        return (int) (endDate.toEpochDay() - startDate.toEpochDay()) / 2;
    }

    @FXML
    private void updateRental() {
        Rental selected = rentalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Selection Error", "Please select a rental to update.");
            return;
        }

        selected.setStartDate(dateStart.getValue());
        selected.setEndDate(dateEnd.getValue());

        try {
            rentalDAO.updateRental(selected);
            refreshData();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not update rental: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRental() {
        Rental selected = rentalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Selection Error", "Please select a rental to delete.");
            return;
        }
        Platform.runLater(() -> {
            try {
                rentalDAO.deleteRental(selected.getId());
                refreshData();
            } catch (SQLException e) {
                showAlert("Database Error", "Could not delete rental: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void refreshData() {
        Platform.runLater(() -> {
            try {
                rentalList.setAll(rentalDAO.getAllRentals());
                equipmentList.setAll(equipmentDAO.getAvailableEquipment());
            } catch (SQLException e) {
                showAlert("Database Error", "Could not refresh data: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private int getCurrentUserId() {
        return 1; // Placeholder; implement your user authentication logic here.
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Overloaded showAlert for information messages
    private void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
