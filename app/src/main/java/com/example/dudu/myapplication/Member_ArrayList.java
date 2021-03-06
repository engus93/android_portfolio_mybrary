package com.example.dudu.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Member_ArrayList {

    public String member_birthday;
    public String member_id;
    public String member_name;
    public String member_password;
    public String member_sex;
    public String user_UID;
    public String user_like;
    public String user_nick;
    public String user_profile;
    public String user_talk;
    public String user_token;

    public Map<String, Boolean> user_follower = new HashMap<>();
    public Map<String, Boolean> user_following = new HashMap<>();

    public Member_ArrayList(String member_id, String member_password,String member_name,String member_birthday, String member_sex,String user_UID){

        this.member_id = member_id;
        this.member_password = member_password;
        this.member_name = member_name;
        this.member_birthday= member_birthday;
        this.member_sex = member_sex;
        this.user_nick = "";
        this.user_talk = "";
        this.user_like = "";
        this.user_profile = "https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2FUser_Default_Profile.png?alt=media&token=9c0e3cd1-f0d1-4df7-a98e-3fa64c6312df";
        this.user_UID = user_UID;
        
    }

    public Member_ArrayList(){

    }

    public String getMember_birthday() {
        return member_birthday;
    }

    public void setMember_birthday(String member_birthday) {
        this.member_birthday = member_birthday;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getUser_UID() {
        return user_UID;
    }

    public void setUser_UID(String user_UID) {
        this.user_UID = user_UID;
    }

    public String getUser_like() {
        return user_like;
    }

    public void setUser_like(String user_like) {
        this.user_like = user_like;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getUser_talk() {
        return user_talk;
    }

    public void setUser_talk(String user_talk) {
        this.user_talk = user_talk;
    }
}
