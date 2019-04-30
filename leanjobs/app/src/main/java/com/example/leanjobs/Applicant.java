package com.example.leanjobs;

public class Applicant {



    private int applicantID;
    private int jobID;
    private String applicantName;
    private String applicantemail;
    private String applicantphone;
    private String applicationstatus;
    private  String applicationResumeURL;

    public Applicant(){}

    public int getApplicantID() { return applicantID; }
    public void setApplicantID(int i) { applicantID = i; }

    public int getJobID() {
        return jobID;
    }
    public void setJobID(int i) {
        jobID = i;
    }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String s) {
        applicantName = s;
    }

    public String getApplicantemail() {
        return applicantemail;
    }
    public void setApplicantemail(String i) {
        applicantemail = i;
    }

    public String getApplicantPhone() { return applicantphone; }
    public void setApplicantPhone(String s) {
        applicantphone = s;
    }

    public String getApplicantStatus() { return applicationstatus; }
    public void setApplicantStatus(String s) {
        applicationstatus = s;
    }

    public String getApplicationResumeURL() {
        return applicationResumeURL;
    }
    public void setApplicationResumeURL(String applicationResumeURL) {
        this.applicationResumeURL = applicationResumeURL;
    }
}
