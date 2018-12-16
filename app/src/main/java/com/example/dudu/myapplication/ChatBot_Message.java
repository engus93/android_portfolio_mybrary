package com.example.dudu.myapplication;

public class ChatBot_Message {

    private String msgText;
    private String msgUser;
    private String send_time;

    public ChatBot_Message(String msgText, String msgUser, String send_time) {
        this.msgText = msgText;
        this.msgUser = msgUser;
        this.send_time = send_time;

    }

    public ChatBot_Message() {

    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }
}
