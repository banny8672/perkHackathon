package com.example.bansi.perkhackathon.Models;

/**
 * Created by bansi on 20-Dec-15.
 */
public class Question {

    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String ans;
    String category;
    int ansStatus = 0;
    String checkedAns;

    public String getCheckedAns() {
        return checkedAns;
    }

    public void setCheckedAns(String checkedAns) {
        this.checkedAns = checkedAns;
    }

    public int getAnsStatus() {
        return ansStatus;
    }

    public void setAnsStatus(int ansStatus) {
        this.ansStatus = ansStatus;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
