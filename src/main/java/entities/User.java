package entities;

import java.awt.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Date;

public class User {
    private int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    private Type user_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }






    public void setUser_type(Type user_type) {
        this.user_type = user_type;
    }

    public User(String username, String first_name, String last_name, String email, String password, String location, Date date_of_birth, Image profile_image, URL website, Type user_type, String CIN) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

}

