package com.example.dudu.myapplication;

public class Home_04_ChattingList {

    public String wright_uid;
    public String message;

    Home_04_ChattingList(String wright_uid, String message){

        this.wright_uid = wright_uid;
        this.message = message;

    }

    Home_04_ChattingList(){

    }

    public String getWright_uid() {
        return wright_uid;
    }

    public void setWright_uid(String wright_uid) {
        this.wright_uid = wright_uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
