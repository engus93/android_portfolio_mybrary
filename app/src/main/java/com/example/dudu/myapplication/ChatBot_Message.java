package com.example.dudu.myapplication;

public class ChatBot_Message {

    private String msgText;
    private String msgUser;

    public ChatBot_Message(String msgText, String msgUser) {
        this.msgText = msgText;
        this.msgUser = msgUser;

    }

    public ChatBot_Message() {

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
