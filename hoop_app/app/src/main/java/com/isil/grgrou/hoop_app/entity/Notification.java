package com.isil.grgrou.hoop_app.entity;

public class Notification {
    private int type;
    private String url;
    private String username;
    private String msg;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg() {
        if (getType() == 0) {
            this.msg = getUsername() + " empezó a seguirte.";
        } else {
            this.msg = "A " + getUsername() + " le gustó tu publicación.";
        }
    }
}
