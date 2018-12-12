package com.example.dudu.myapplication;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Home_04_ChatRoom_Model implements Comparable<Home_04_ChatRoom_Model> {

    public Map<String, Message> message = new HashMap<>(); //채팅방 안에 내용들
    public Map<String, Boolean> users = new HashMap<>();    //채팅방에 속한 사람들
    public Map<String, Boolean> now_login = new HashMap<>();    //채팅방에 있는 사람들
    public Map<String, Integer> message_count = new HashMap<>();    //채팅방에 있는 사람들

    public Long lasttime;
    public String chat_medel_room_key;

    public static class Message {

        String wright_user;
        String contents;
        String picture = "";
        Object time;
        public Map<String,Object> read = new HashMap<>();

    }

    @Override
    public int compareTo(Home_04_ChatRoom_Model o) {
        if(lasttime > o.lasttime){
            return -1;
        }else if(lasttime < o.lasttime){
            return 1;
        }
            return 0;
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




}