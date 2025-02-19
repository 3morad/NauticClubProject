package org.example.models;

import java.time.LocalDateTime;

public class Registration {
    private int id;
    private int memberId;       // references a user with userType='member'
    private int eventId;        // references an event
    private LocalDateTime registrationDate;

    // No-arg constructor
    public Registration() {
    }

    // Constructor for creating a new registration
    public Registration(int memberId, int eventId) {
        this.memberId = memberId;
        this.eventId = eventId;
    }

    // Constructor with all fields
    public Registration(int id, int memberId, int eventId, LocalDateTime registrationDate) {
        this.id = id;
        this.memberId = memberId;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    // toString
    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", eventId=" + eventId +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
