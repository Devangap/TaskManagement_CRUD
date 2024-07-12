package Model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String picture;
    private Date lastLogin;
    private int loginAttempts;
    private String status;

    public User() {
        this.lastLogin = new Date();
        this.loginAttempts = 0;
        this.status = "active";
    }

    public User(int id, String username, String email, String password, String picture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.lastLogin = new Date();
        this.loginAttempts = 0;
        this.status = "active";
    }

    public User(String username, String email, String password, String picture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.lastLogin = new Date();
        this.loginAttempts = 0;
        this.status = "active";
    }

    // Getters and setters
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
