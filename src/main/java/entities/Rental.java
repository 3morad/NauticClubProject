package entities;
import java.sql.CallableStatement;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class Rental {
    private int id;
    private int equipmentId;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    // Constructor
    public Rental(int id, int equipmentId, int userId, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
