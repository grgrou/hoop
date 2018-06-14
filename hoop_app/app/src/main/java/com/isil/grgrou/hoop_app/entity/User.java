package com.isil.grgrou.hoop_app.entity;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable{
    private String id;
    private String url;
    private String name;
    private String bio;
    private String username;
    private String password;
    private HashMap<String, Boolean> followers;
    private HashMap<String, Boolean> following;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public HashMap<String, Boolean> getFollowers() {
        return followers;
    }

    public void setFollowers(HashMap<String, Boolean> followers) {
        this.followers = followers;
    }

    public HashMap<String, Boolean> getFollowing() {
        return following;
    }

    public void setFollowing(HashMap<String, Boolean> following) {
        this.following = following;
    }
}
