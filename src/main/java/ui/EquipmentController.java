package ui;

import api.EquipmentManagementAPI;
import dao.EquipmentDAO;
import entities.Equipment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class EquipmentController {
    private MainApp mainApp;  // Reference to MainApp
    private final EquipmentManagementAPI equipmentAPI;
    private EquipmentDAO equipmentDAO;
    private ObservableList<Equipment> equipmentList;

    // FXML elements for the TableView view
    @FXML
    private AnchorPane anchorPane; // Used for styling
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    private TableColumn<Equipment, String> colName;
    @FXML
    private TableColumn<Equipment, String> colType;
    @FXML
    private TableColumn<Equipment, Boolean> colStatus;
    @FXML
    private TableColumn<Equipment, Integer> colPrice;

    // FXML elements for equipment editing (optional)
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;

    public EquipmentController() {
        // Initialize the API; note that EquipmentDAO is also used inside the API methods
        this.equipmentAPI = new EquipmentManagementAPI();
    }

    @FXML
    public void initialize() {
        try {
            equipmentDAO = new EquipmentDAO();
            // Load all equipment from the database and initialize the table
            List<Equipment> equipmentFromDB = equipmentDAO.getAllEquipment();
            equipmentList = FXCollections.observableArrayList(equipmentFromDB);
            equipmentTable.setItems(equipmentList);

            // Bind table columns to Equipment properties
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("available"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            // Apply CSS manually
            Platform.runLater(() -> {
                Scene scene = anchorPane.getScene(); // Get the scene from anchorPane
                if (scene != null) {
                    scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load equipment data.");
        }
    }

    @FXML
    private void addEquipment() {
        // Validate input fields
        String name = txtName.getText();
        String type = txtType.getText();
        String priceText = txtPrice.getText().trim();
        if (name.isEmpty() || type.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a valid numeric price.");
            return;
        }

        // Create a new Equipment object (assuming your Equipment constructor is: Equipment(int id, String name, boolean available, String type, int price))
        Equipment newEquipment = new Equipment(0, name, true, type, price);

        // Use the API to add equipment. Here, we assume equipmentAPI.addEquipment() returns a boolean on success.
        try {
            if (equipmentAPI.addEquipment(newEquipment)) {
                // Refresh the TableView by reloading from the DAO/API
                equipmentList.setAll(equipmentDAO.getAllEquipment());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Equipment added successfully.");
                txtName.clear();
                txtType.clear();
                txtPrice.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Could not add equipment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add equipment due to a database error.");
        }
    }

    @FXML
    private void deleteEquipment() {
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();
        if (selectedEquipment == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an equipment to delete.");
            return;
        }

        try {
            if (equipmentAPI.deleteEquipment(selectedEquipment.getId())) {
                equipmentList.setAll(equipmentDAO.getAllEquipment());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Equipment deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Could not delete equipment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not delete equipment due to a database error.");
        }
    }

    @FXML
    private void updateEquipment() {
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();
        if (selectedEquipment == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an equipment to update.");
            return;
        }

        // Get the updated details from the text fields
        String newName = txtName.getText();
        String newType = txtType.getText();
        int newPrice;
        try {
            newPrice = Integer.parseInt(txtPrice.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a valid numeric price.");
            return;
        }

        // Update the equipment's details
        selectedEquipment.setName(newName);
        selectedEquipment.setType(newType);
        selectedEquipment.setPrice(newPrice);

        try {
            equipmentDAO.updateEquipment(selectedEquipment.getId(), selectedEquipment);
            equipmentList.setAll(equipmentDAO.getAllEquipment());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Equipment updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update equipment.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // This method is called by MainApp to pass a reference of itself.
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
