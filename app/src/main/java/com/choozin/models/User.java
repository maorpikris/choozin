package com.choozin.models;

import java.util.List;

public class User {

    private int userId;
    private String email;
    private String password;
    private String username;
    private String profileUrl;
    private String description;
    private List<String> posts;
    private List<String> followers;
    private List<String> following;
    private List<String> favorites;

    public User(int userId, String email, String password, String username, String profileUrl, String description, List<String> posts, List<String> followers, List<String> following, List<String> favorites) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profileUrl = profileUrl;
        this.description = description;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
        this.favorites = favorites;
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

    public User(int userId, String email, String username, String imageUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profileUrl = imageUrl;
    }

    public User(int userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
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

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
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
        return profileUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.profileUrl = imageUrl;
    }
}
