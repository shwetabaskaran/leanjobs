package com.example.leanjobs;

public class QuestionAnswer {
    private int questionID;
    private String questiondesc;
    private String answer;
    private int jobID;

    public QuestionAnswer(){}

    public int getQuestionID() {
        return questionID;
    }
    public void setquestionID(int i) {
        questionID = i;
    }

    public String getquestiondesc() {
        return questiondesc;
    }
    public void setquestiondesc(String s) {
        questiondesc = s;
    }

    public String getanswer() {
        return answer;
    }
    public void setanswer(String s) {
        answer = s;
    }

    public int getJobID() {
        return jobID;
    }
    public void setjobID(int i) {
        jobID = i;
    }


}
