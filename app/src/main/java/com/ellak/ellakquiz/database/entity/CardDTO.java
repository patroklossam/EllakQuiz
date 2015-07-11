package com.ellak.ellakquiz.database.entity;

/**
 * Created by Patroklos on 7/11/15.
 */
public class CardDTO {
    private int c_id;
    private String question;
    private String ans1,ans2,ans3,ans4;

    private Boolean flag1,flag2,flag3,flag4;

    public CardDTO() {
    }

    public CardDTO(int c_id, String question, String ans1, String ans2, String ans3, String ans4, Boolean flag1, Boolean flag2, Boolean flag3, Boolean flag4) {
        this.c_id = c_id;
        this.question = question;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.flag3 = flag3;
        this.flag4 = flag4;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public Boolean getFlag1() {
        return flag1;
    }

    public void setFlag1(Boolean flag1) {
        this.flag1 = flag1;
    }

    public Boolean getFlag2() {
        return flag2;
    }

    public void setFlag2(Boolean flag2) {
        this.flag2 = flag2;
    }

    public Boolean getFlag3() {
        return flag3;
    }

    public void setFlag3(Boolean flag3) {
        this.flag3 = flag3;
    }

    public Boolean getFlag4() {
        return flag4;
    }

    public void setFlag4(Boolean flag4) {
        this.flag4 = flag4;
    }
}
