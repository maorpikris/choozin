package com.choozin.models;

import java.util.Date;
import java.util.List;

public class PostItem {
    private String title;
    private String rImageUrl;
    private String lImageUrl;
    private int rVotes;
    private int lVotes;
    private String postId;
    private List<String> rVoters;
    private List<String> lVoters;
    private int fav;
    private User creator;
    private Date createdAt;

    public PostItem(String title, String rImageUrl, String lImageUrl, int rVotes, int lVotes, int fav, User creator, Date createdAt) {
        this.title = title;
        this.rImageUrl = rImageUrl;
        this.lImageUrl = lImageUrl;
        this.rVotes = rVotes;
        this.lVotes = lVotes;
        this.fav = fav;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public String getTitle() {
        return title;
    }

    public String getrImageUrl() {
        return rImageUrl;
    }

    public String getlImageUrl() {
        return lImageUrl;
    }

    public int getrVotes() {
        return rVotes;
    }

    public int getlVotes() {
        return lVotes;
    }

    public int getFav() {
        return fav;
    }

    public User getCreator() {
        return creator;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
