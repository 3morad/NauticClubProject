package org.example.models;

public class Location {
    private int id;
    private String name;
    private String address;
    private String file; // path or URL for an image or map

    // No-arg constructor
    public Location() {
    }

    // Constructor for creating a new Location (no ID)
    public Location(String name, String address, String file) {
        this.name = name;
        this.address = address;
        this.file = file;
    }

    // Constructor with all fields
    public Location(int id, String name, String address, String file) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.file = file;
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }

    // toString
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
