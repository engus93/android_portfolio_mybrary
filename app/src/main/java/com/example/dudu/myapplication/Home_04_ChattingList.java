package com.example.dudu.myapplication;

public class Home_04_ChattingList {

    public String time;
    public String wright_uid;
    public String message;

    Home_04_ChattingList(String time, String wright_uid, String message){

        this.time = time;
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
