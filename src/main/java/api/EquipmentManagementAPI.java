
package api;

import dao.EquipmentDAO;
import entities.Equipment;

import java.sql.SQLException;
import java.util.List;

public class EquipmentManagementAPI {
    private final EquipmentDAO equipmentDAO;

    public EquipmentManagementAPI() {
        this.equipmentDAO = new EquipmentDAO();
    }

    public List<Equipment> getAvailableEquipment() {
        try {
            return equipmentDAO.getAvailableEquipment();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Handle error case (return empty list or log)
        }
    }

    public boolean addEquipment(Equipment equipment) {
        try {
            return equipmentDAO.addEquipment(equipment);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEquipment(int equipmentId, Equipment equipment) {
        try {
            return equipmentDAO.updateEquipment(equipmentId, equipment);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEquipment(int equipmentId) {
        try {
            return equipmentDAO.deleteEquipment(equipmentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
