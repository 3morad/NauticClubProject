package org.example.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String userType;   // e.g. 'member', 'instructor', 'admin'
    private LocalDateTime createdAt;

    // No-arg constructor
    public User() {
    }

    // Constructor for inserting a new user (without ID, createdAt)
    public User(String username, String firstName, String lastName, String userType) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    // Constructor for reading from DB (includes ID, createdAt)
    public User(int id, String username, String firstName, String lastName, String userType, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType='" + userType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
