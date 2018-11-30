package com.example.dudu.myapplication;

public class Member_ArrayList {

    String member_id;
    String member_password;
    String member_name;
    String member_birthday;
    String member_sex;
    String user_nick;
    String user_talk;
    String user_like;
    String user_profile;

    public Member_ArrayList(String member_id, String member_password,String member_name,String member_birthday, String member_sex){

        this.member_id = member_id;
        this.member_password = member_password;
        this.member_name = member_name;
        this.member_birthday= member_birthday;
        this.member_sex = member_sex;
        this.user_nick = "";
        this.user_talk = "";
        this.user_like = "";
        this.user_profile = "";
    }

    public Member_ArrayList(){

    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_birthday() {
        return member_birthday;
    }

    public void setMember_birthday(String member_birthday) {
        this.member_birthday = member_birthday;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_talk() {
        return user_talk;
    }

    public void setUser_talk(String user_talk) {
        this.user_talk = user_talk;
    }

    public String getUser_like() {
        return user_like;
    }

    public void setUser_like(String user_like) {
        this.user_like = user_like;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }
}
