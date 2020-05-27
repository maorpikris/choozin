package com.choozin.models;

import java.util.List;

// The post model, all the posts that are coming from the server being transformed to this model.
public class PostItem {
    private String title;
    private String rImage;
    private String lImage;
    private String _id;
    private List<String> rVoters;
    private List<String> lVoters;
    private List<String> favs;
    private User creator;
    private String createdAt;

    public PostItem(String title, String rImage, String lImage, int rVotes, int lVotes, String _id, List<String> rVoters, List<String> lVoters, List<String> favs, User creator, String createdAt) {
        this.title = title;
        this.rImage = rImage;
        this.lImage = lImage;
        this._id = _id;
        this.rVoters = rVoters;
        this.lVoters = lVoters;
        this.favs = favs;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    public PostItem(String title, String rImage, String lImage) {
        this.title = title;
        this.rImage = rImage;
        this.lImage = lImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getrImage() {
        return rImage;
    }

    public void setrImage(String rImageUrl) {
        this.rImage = rImageUrl;
    }

    public String getlImage() {
        return lImage;
    }

    public void setlImage(String lImageUrl) {
        this.lImage = lImageUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getrVoters() {
        return rVoters;
    }

    public void setrVoters(List<String> rVoters) {
        this.rVoters = rVoters;
    }

    public List<String> getlVoters() {
        return lVoters;
    }

    public void setlVoters(List<String> lVoters) {
        this.lVoters = lVoters;
    }

    public List<String> getFavs() {
        return favs;
    }

    public void setFavs(List<String> favs) {
        this.favs = favs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
