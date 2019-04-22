package com.example.leanjobs;

import java.util.Calendar;

public class Job {

    private int jobID;
    private String jobTitle;
    private String jobRoleDesc;
    private String jobReqs;
    private String jobWages;
    private int jobIsActive;
    private String jobCreatedAt;

    public Job(){}

    public int getJobID() {
        return jobID;
    }
    public void setJobID(int i) {
        jobID = i;
    }

    public String getjobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String s) {
        jobTitle = s;
    }

    public String getJobRoleDesc() {
        return jobRoleDesc;
    }
    public void setJobRoleDesc(String s) {
        jobRoleDesc = s;
    }

    public String getjobReqs() {
        return jobReqs;
    }
    public void setJobReqs(String s) {
        jobReqs = s;
    }

    public String getJobWages() {
        return jobWages;
    }
    public void setJobWages(String s) {
        jobWages = s;
    }

    public int getJobIsActive() {
        return jobIsActive;
    }
    public void setJobIsActive(int s) {
        jobIsActive = s;
    }

    public String getJobCreatedAt() {
        return jobCreatedAt;
    }
    public void setJobCreatedAt(String s) {
        jobWages = s;
    }

}
