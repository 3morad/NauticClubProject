package entities;

import java.time.LocalDateTime;


public class Rental {
    private String status;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Equipment equipment;

    public Rental(String status, LocalDateTime start_time, LocalDateTime end_time, Equipment equipment) {
        this.status = status;
        this.start_time = start_time;
        this.end_time = end_time;
        this.equipment = equipment;
    }
    // Constructor without status (default status: "Pending")
    public Rental(LocalDateTime start_time, LocalDateTime end_time, Equipment equipment) {
        this.status = "Pending"; // Default status
        this.start_time = start_time;
        this.end_time = end_time;
        this.equipment = equipment;
    }

    public Rental() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getstart_time() {
        return start_time;
    }

    public void setstart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "status='" + status + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", equipment=" + equipment +
                '}';
    }
}
