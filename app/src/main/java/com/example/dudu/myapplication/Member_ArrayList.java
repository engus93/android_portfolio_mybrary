package com.example.dudu.myapplication;

public class Member_ArrayList {

    String member_id;
    String member_password;
    String member_name;
    String member_birthday;
    String member_sex;

    public Member_ArrayList(String member_id, String member_password,String member_name,String member_birthday, String member_sex){

        this.member_id = member_id;
        this.member_password = member_password;
        this.member_name = member_name;
        this.member_birthday= member_birthday;
        this.member_sex = member_sex;
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

}
