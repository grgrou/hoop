package com.isil.grgrou.hoop_app.entity;

/**
 * Created by Alumno-CT on 04/05/2018.
 */

public class ListUsers {
    private String id;
    private String url;
    private String username;
    private Boolean isFollow;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}
