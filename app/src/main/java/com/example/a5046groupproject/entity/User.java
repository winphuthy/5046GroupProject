package com.example.a5046groupproject.entity;

import androidx.annotation.NonNull;

public class User {
    private String uid;
    private String email;
    private String name;
    private String description;

    public User(String uid, String email, String name, String description) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.description = description;
    }

    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @NonNull
    @Override
    public String toString(){
        return super.toString() + "Uid: " + uid + "\n"
                + "Email: " + email + "\n"
                + "name: " + name + "\n"
                + "description: " + description + "\n";
    }
}
