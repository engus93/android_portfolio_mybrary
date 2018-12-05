package com.example.dudu.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Home_04_Single_Chatting {

    public String user_1;
    public String user_2;
    public String room_key;

    Home_04_Single_Chatting(String user_1, String user_2, String room_key){

        this.user_1 = user_1;
        this.user_2 = user_2;
        this.room_key = room_key;

    }

    Home_04_Single_Chatting(){

    }

    public String getUser_1() {
        return user_1;
    }

    public void setUser_1(String user_1) {
        this.user_1 = user_1;
    }

    public String getUser_2() {
        return user_2;
    }

    public void setUser_2(String user_2) {
        this.user_2 = user_2;
    }

    public String getRoom_key() {
        return room_key;
    }

    public void setRoom_key(String room_key) {
        this.room_key = room_key;
    }
}
