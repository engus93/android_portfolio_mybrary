package com.example.dudu.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Home_04_ChatRoom_Model {

    public Map<String, Message> message = new HashMap<>(); //채팅방 안에 내용들
    public Map<String, Boolean> users = new HashMap<>();    //채팅방에 속한 사람들
    public Map<String, Boolean> now_login = new HashMap<>();    //채팅방에 있는 사람들

    public String uid;
    public String opponent_uid;

    public static class Message {

        String wright_user;
        String contents;
        String picture = "";
        Object time;
        public Map<String,Object> read = new HashMap<>();

    }

    Home_04_ChatRoom_Model() {

    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public Map<String, Message> getMessage() {
        return message;
    }

    public void setMessage(Map<String, Message> message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpponent_uid() {
        return opponent_uid;
    }

    public void setOpponent_uid(String opponent_uid) {
        this.opponent_uid = opponent_uid;
    }



}