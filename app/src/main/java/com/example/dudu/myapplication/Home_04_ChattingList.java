package com.example.dudu.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Home_04_ChattingList {

    public String time;
    public String wright_uid;
    public String message;
    public Map<String,Object> read = new HashMap<>();

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
