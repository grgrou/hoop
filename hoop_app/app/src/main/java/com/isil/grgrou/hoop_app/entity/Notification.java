package com.isil.grgrou.hoop_app.entity;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

public class Notification {
    private String msg;
    private byte[] bytes;

    public String getMsg() {
        return msg;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) { this.bytes = bytes; }

    public void setMsg(String status, String username) {
        if (status.equals("0")) {
            msg = username + " comenzó a seguirte.";
        } else {
            msg = username + " compartió tu publicación.";
        }
    }
}
