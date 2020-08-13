package com.example.edcansumr;

public class MemoModel {

    private String text, time, email;

    public MemoModel(){}

    public MemoModel(String name, String time, String email) {
        this.text = name;
        this.time = time;
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
