package com.example.dudu.myapplication;


public class NotificationModel {

    public String to;
    public Notification notification = new Notification();


    public static class Notification {

        public String tile;
        public String text;

    }
}
