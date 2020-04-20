package com.choozin.models;

import java.util.List;

public class PostItem {
    private String title;
    private String rImage;
    private String lImage;
    private int rVotes;
    private int lVotes;
    private String _id;
    private List<String> rVoters;
    private List<String> lVoters;
    private int fav;
    private String creator;

    public PostItem(String title, String rImage, String lImage, int rVotes, int lVotes, String _id, List<String> rVoters, List<String> lVoters, int fav, String creator) {
        this.title = title;
        this.rImage = rImage;
        this.lImage = lImage;
        this.rVotes = rVotes;
        this.lVotes = lVotes;
        this._id = _id;
        this.rVoters = rVoters;
        this.lVoters = lVoters;
        this.fav = fav;
        this.creator = creator;
    }

    public PostItem(String title, String rImage, String lImage) {
        this.title = title;
        this.rImage = rImage;
        this.lImage = lImage;
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

    public int getrVotes() {
        return rVotes;
    }

    public void setrVotes(int rVotes) {
        this.rVotes = rVotes;
    }

    public int getlVotes() {
        return lVotes;
    }

    public void setlVotes(int lVotes) {
        this.lVotes = lVotes;
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

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
