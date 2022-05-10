package com.example.a5046groupproject.entity;

import androidx.room.Entity;


public class User {
    private String uid;
    private String email;
    private String pwd;
    private float totalConsume;

    public User(String uid, String email, String pwd) {
        this.uid = uid;
        this.email = email;
        this.pwd = pwd;
        this.totalConsume = totalConsume;
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public float getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(float totalConsume) {
        this.totalConsume = totalConsume;
    }
}
