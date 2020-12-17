package sample;

import javafx.beans.property.StringProperty;

public class Login {
    String Id;
    String username;
    String password;
    String fullName;
    String role;

    public Login(String id, String username, String password, String fullName, String role) {
        Id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}