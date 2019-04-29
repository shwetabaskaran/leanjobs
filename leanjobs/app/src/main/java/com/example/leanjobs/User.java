package com.example.leanjobs;

public class User {
    private String userID;
    private String fullname;
    private String phonenum;
    private String email;

    public User(){}

    public String getUserID() {
        return userID;
    }
    public void setUserID(String i) {
        userID = i;
    }

    public String getfullname() {
        return fullname;
    }
    public void setfullname(String s) {
        fullname = s;
    }

    public String getPhone() {
        return phonenum;
    }
    public void setPhone(String s) {
        phonenum = s;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String s) {
        email = s;
    }

}

