package com.mobilesutra.feetrackerclass.model;

/**
 * Created by Rasika on 5/21/2016.
 */
public class DTOfaq {

    String str_question = "", str_answer = "";

    public DTOfaq(String str_question, String str_answer) {
        this.str_question = str_question;
        this.str_answer = str_answer;

    }

    public String getStr_answer() {
        return str_answer;
    }

    public String getStr_question() {
        return str_question;
    }

    public void setStr_answer(String str_answer) {
        this.str_answer = str_answer;
    }

    public void setStr_question(String str_question) {
        this.str_question = str_question;
    }
}
