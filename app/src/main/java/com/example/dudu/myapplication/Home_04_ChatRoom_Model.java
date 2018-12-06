package com.example.dudu.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Home_04_ChatRoom_Model {

    public Map<String, Boolean> users = new HashMap<>();    //채팅방에 속한 사람들
    public Map<String, Message> messages = new HashMap<>(); //채팅방 안에 내용들

    public String uid;
    public String opponent_uid;

    public static class Message {

        String wright_user;
        String contents;

    }

    Home_04_ChatRoom_Model() {

    }


    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
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