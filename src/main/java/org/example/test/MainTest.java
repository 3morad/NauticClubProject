package org.example.test;

import org.example.dao.EventDAO;
import org.example.models.Event;

import java.time.LocalDate;

public class MainTest {
    public static void main(String[] args) {
        EventDAO eventDAO = new EventDAO();

        // 1) CREATE
        Event e = new Event("TestEvent", "TestDesc", 10.0, LocalDate.of(2025, 2, 20), 1);
        eventDAO.createEvent(e);
        System.out.println("Created event: " + e);

        // 2) READ ALL
        System.out.println("All events:");
        eventDAO.getAllEvents().forEach(System.out::println);

        // 3) READ BY ID
        // If you know an event ID, you can do:
        // Event ev = eventDAO.getEventById(1);
        // System.out.println("Found: " + ev);

        // 4) UPDATE
        // Suppose we update the first event in the list
        // e.setName("UpdatedEvent");
        // eventDAO.updateEvent(e);

        // 5) DELETE
        // eventDAO.deleteEvent(e.getId());
    }
}
