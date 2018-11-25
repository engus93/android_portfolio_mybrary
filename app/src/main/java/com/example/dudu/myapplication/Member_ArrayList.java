package com.example.dudu.myapplication;

public class Member_ArrayList {

    String member_id;
    String member_password_01;
    String member_e_mail;
    String member_name;
    String member_birthday;
//    String member_sex;

    public Member_ArrayList(String member_id, String member_password_01, String member_e_mail,String member_name,String member_birthday){

        this.member_id = member_id;
        this.member_password_01 = member_password_01;
        this.member_e_mail = member_e_mail;
        this.member_name = member_name;
        this.member_birthday= member_birthday;
//        this.member_sex = member_sex;

    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_password_01() {
        return member_password_01;
    }

    public void setMember_password_01(String member_password_01) {
        this.member_password_01 = member_password_01;
    }

    public String getMember_e_mail() {
        return member_e_mail;
    }

    public void setMember_e_mail(String member_e_mail) {
        this.member_e_mail = member_e_mail;
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

//    public String getMember_sex() {
//        return member_sex;
//    }
//
//    public void setMember_sex(String member_sex) {
//        this.member_sex = member_sex;
//    }

}
