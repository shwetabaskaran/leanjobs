package com.example.leanjobs;

public class Appln {
    private int jobID;
    private int applicationID;
    private String jobtitle;
    private String roledescription;
    private String jobreqts;
    private String wages;
    private String jobstatus;
    private String applicationstatus;

    public Appln(){}

    public int getJobIDAppl() {
        return jobID;
    }
    public void setJobIDAppl(int i) {
        jobID = i;
    }

    public int getApplicationID() { return applicationID; }
    public void setApplicationID(int i) { applicationID = i; }

    public String getJobTitle() { return jobtitle; }
    public void setJobTitle(String s) {
        jobtitle = s;
    }

    public String getRoleDescription() { return roledescription; }
    public void setRoleDescription(String s) {
        roledescription = s;
    }

    public String getJobRequirements() { return jobreqts; }
    public void setJobRequirements(String s) { jobreqts = s; }

    public String getWages() { return wages; }
    public void setWages(String s) { wages = s; }

    public String getJobStatus() { return jobstatus; }
    public void setJobStatus(String s) { jobstatus = s; }

    public String getApplicationStatus() { return applicationstatus; }
    public void setApplicationStatus(String s) { applicationstatus = s; }

}
