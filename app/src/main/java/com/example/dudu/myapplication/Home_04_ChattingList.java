package com.example.dudu.myapplication;

public class Home_04_ChattingList {

    public String time;
    public String wright_uid;
    public String message;
    private String read_user;

    Home_04_ChattingList(String time, String wright_uid, String message){

        this.time = time;
        this.wright_uid = wright_uid;
        this.message = message;
        this.read_user = "false";

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead_user() {
        return read_user;
    }

    public void setRead_user(String read_user) {
        this.read_user = read_user;
    }
}
