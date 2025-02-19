package org.example.models;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private String description;
    private double price;
    private LocalDate eventDate;
    private int locationId; // or store a Location object if you want a direct reference

    // No-arg constructor
    public Event() {
    }

    // Constructor for creating a new event (no ID)
    public Event(String name, String description, double price, LocalDate eventDate, int locationId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.eventDate = eventDate;
        this.locationId = locationId;
    }

    // Constructor with all fields
    public Event(int id, String name, String description, double price, LocalDate eventDate, int locationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.eventDate = eventDate;
        this.locationId = locationId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getLocationId() {
        return locationId;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    // toString
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", eventDate=" + eventDate +
                ", locationId=" + locationId +
                '}';
    }
}
