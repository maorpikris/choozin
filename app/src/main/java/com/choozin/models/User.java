package com.choozin.models;

import java.util.List;

public class User {

    private String _id;
    private String email;
    private String password;
    private String username;
    private String profileUrl;
    private String description;
    private List<String> posts;
    private int stars;
    private List<String> favorites;
    private List<String> wait;
    private List<String> waiting;
    private boolean accountprivate;

    public User(String _id, String email, String password, String username, String profileUrl, String description, List<String> posts, int stars, List<String> favorites, List<String> wait, List<String> waiting, boolean accountprivate) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profileUrl = profileUrl;
        this.description = description;
        this.posts = posts;
        this.stars = stars;
        this.favorites = favorites;
        this.wait = wait;
        this.waiting = waiting;
        this.accountprivate = accountprivate;
    }

    public User(String _id, String email, String username, String profileUrl) {
        this._id = _id;
        this.email = email;
        this.username = username;
        this.profileUrl = profileUrl;
    }

    public List<String> getWait() {
        return wait;
    }

    public void setWait(List<String> wait) {
        this.wait = wait;
    }

    public List<String> getWaiting() {
        return waiting;
    }

    public void setWaiting(List<String> waiting) {
        this.waiting = waiting;
    }

    public boolean isAccountprivate() {
        return accountprivate;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setAccountprivate(boolean accountprivate) {
        this.accountprivate = accountprivate;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getprofileUrl() {
        return profileUrl;
    }

    public void setprofileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
