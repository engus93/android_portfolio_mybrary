package com.example.dudu.myapplication;

public class opponent_uid {

    public String opponent_nick;
    public String opponent_uid;
    public String opponent_profile;

    opponent_uid(String opponent_nick, String opponent_uid, String opponent_profile){

        this.opponent_nick = opponent_uid;
        this.opponent_uid = opponent_uid;
        this.opponent_profile = opponent_profile;

    }

    opponent_uid(){

    }

    public String getOpponent_nick() {
        return opponent_nick;
    }

    public void setOpponent_nick(String opponent_nick) {
        this.opponent_nick = opponent_nick;
    }

    public String getOpponent_uid() {
        return opponent_uid;
    }

    public void setOpponent_uid(String opponent_uid) {
        this.opponent_uid = opponent_uid;
    }

    public String getOpponent_profile() {
        return opponent_profile;
    }

    public void setOpponent_profile(String opponent_profile) {
        this.opponent_profile = opponent_profile;
    }
}
