package com.ms.demo.siyu.mymessenger.entity;

public class User {
    String uid;
    String userName;
    String profileImg;
    public User() {

    }
    public User(String uid, String userName, String profileImg) {
        this.uid = uid;
        this.userName = userName;
        this.profileImg = profileImg;
    }
    public String getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImg() {
        return profileImg;
    }
}