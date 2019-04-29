package com.example.leanjobs;

import java.io.Serializable;

public class UserJob implements Serializable {
    private String userID;
    private int jobID;

    public UserJob(){}

    public int getJobID() {
        return jobID;
    }
    public void setJobID(int i) {
        jobID = i;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String i) {
        userID = i;
    }

}

