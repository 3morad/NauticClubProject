package main;

import dao.EquipmentDAO;
import dao.RentalDAO;
import entities.Equipment;
import entities.Rental;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        RentalDAO rentalDAO = new RentalDAO();

        // Add Equipment
        Equipment equipment = new Equipment(0, "Kayak", "Water Sport", true, 50.00);
        equipmentDAO.addEquipment(equipment);

        // Add Rental
        Rental rental = new Rental(  "rented", LocalDateTime.now(), null);
        rentalDAO.addRental(rental);

        // Display all rentals
        rentalDAO.getAllRentals().forEach(r -> System.out.println("Rental ID: " + r.getId()));
    }
}

