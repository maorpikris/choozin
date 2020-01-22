package com.choozin.models;

public class User {


    private int userId;
    private String email;
    private String password;
    private String username;
    private String imageUrl;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int userId, String email, String username, String imageUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public User(int userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
