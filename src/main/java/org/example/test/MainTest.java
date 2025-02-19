package org.example.test;

import org.example.dao.UserDAO;
import org.example.dao.EventDAO;
import org.example.dao.RegistrationDAO;
import org.example.models.User;
import org.example.models.Event;
import org.example.models.Registration;
import org.example.dao.LocationDAO;
import org.example.models.Location;
import java.time.LocalDate;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        // =========== USER DAO TESTS ===========
        UserDAO userDAO = new UserDAO();

        // 1) CREATE a new user
        User newUser = new User("john_doe", "John", "Doe", "member");
        userDAO.createUser(newUser);
        System.out.println("Created new user: " + newUser);

        // 2) READ all users
        List<User> allUsers = userDAO.getAllUsers();
        System.out.println("\nAll Users (after creation):");
        for (User u : allUsers) {
            System.out.println(u);
        }

        // 3) If we have at least one user, READ BY ID, UPDATE, DELETE as a demo
        if (!allUsers.isEmpty()) {
            // READ BY ID
            int firstUserId = allUsers.get(0).getId();
            User foundUser = userDAO.getUserById(firstUserId);
            System.out.println("\nFound user by ID " + firstUserId + ": " + foundUser);

            // UPDATE (change userType)
            foundUser.setUserType("instructor");
            userDAO.updateUser(foundUser);
            System.out.println("Updated user to instructor: " + foundUser);

            // READ ALL again
            allUsers = userDAO.getAllUsers();
            System.out.println("\nAll Users (after update):");
            for (User u : allUsers) {
                System.out.println(u);
            }

            // Optionally, DELETE the user
            // userDAO.deleteUser(firstUserId);
            // System.out.println("Deleted user with ID: " + firstUserId);
        }

        // =========== EVENT DAO TESTS ===========
        EventDAO eventDAO = new EventDAO();

        // 1) CREATE a new event
        Event newEvent = new Event("Kayaking Adventure", "Fun trip", 25.0,
                LocalDate.of(2025, 6, 1), 1 /* locationId */);
        eventDAO.createEvent(newEvent);
        System.out.println("\nCreated new event: " + newEvent);

        // 2) READ all events
        List<Event> allEvents = eventDAO.getAllEvents();
        System.out.println("\nAll Events (after creation):");
        for (Event e : allEvents) {
            System.out.println(e);
        }

        // 3) If we have at least one event, READ BY ID, UPDATE, DELETE as a demo
        if (!allEvents.isEmpty()) {
            int firstEventId = allEvents.get(0).getId();
            Event foundEvent = eventDAO.getEventById(firstEventId);
            System.out.println("\nFound event by ID " + firstEventId + ": " + foundEvent);

            // UPDATE (change the price)
            foundEvent.setPrice(30.0);
            eventDAO.updateEvent(foundEvent);
            System.out.println("Updated event price: " + foundEvent);

            // READ ALL again
            allEvents = eventDAO.getAllEvents();
            System.out.println("\nAll Events (after update):");
            for (Event e : allEvents) {
                System.out.println(e);
            }

            // Optionally, DELETE the event
            // eventDAO.deleteEvent(firstEventId);
            // System.out.println("Deleted event with ID: " + firstEventId);
        }

        // 4) ASSIGN an instructor to an event (assuming user with ID=2 is instructor)
        // Make sure that user with ID=2 actually exists and userType='instructor'
        eventDAO.assignInstructor(1, 2);
        System.out.println("\nAssigned instructor with ID=2 to event with ID=1");

        // =========== REGISTRATION DAO TESTS ===========
        RegistrationDAO regDAO = new RegistrationDAO();

        // REGISTER a member (ID=3) for an event (ID=1)
        // Make sure user with ID=3 is 'member' and event with ID=1 exists
        regDAO.registerMember(3, 1);
        System.out.println("Registered member with ID=3 for event with ID=1");

        // READ all registrations
        List<Registration> allRegs = regDAO.getAllRegistrations();
        System.out.println("\nAll Registrations (after creation):");
        for (Registration r : allRegs) {
            System.out.println(r);
        }

        // Optionally test update/delete on Registration
        // ...

        System.out.println("\n=== TEST COMPLETE ===");
    }
}
