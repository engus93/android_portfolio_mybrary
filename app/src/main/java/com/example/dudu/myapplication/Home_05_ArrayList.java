package com.example.dudu.myapplication;

import java.util.Comparator;

public class Home_05_ArrayList {
    public int heart_book;
    public String heart_name;
    public String heart_author;
    public String heart_price;
    public double heart_rank;
    public int heart_heart;
    public String User_key;
    public String User_UID;


    public Home_05_ArrayList(int book, String name, String author, String price, double rank, int heart_heart, String User_key, String User_UID) {
        this.heart_book = book;
        this.heart_name = name;
        this.heart_author = author;
        this.heart_price = price;
        this.heart_rank = rank;
        this.heart_heart = heart_heart;
        this.User_key = User_key;
        this.User_UID = User_UID;
    }

    public Home_05_ArrayList(){

    }

    public int getHeart_book() {
        return heart_book;
    }

    public void setHeart_book(int heart_book) {
        this.heart_book = heart_book;
    }

    public String getHeart_name() {
        return heart_name;
    }

    public void setHeart_name(String heart_name) {
        this.heart_name = heart_name;
    }

    public String getHeart_author() {
        return heart_author;
    }

    public void setHeart_author(String heart_author) {
        this.heart_author = heart_author;
    }

    public String getHeart_price() {
        return heart_price;
    }

    public void setHeart_price(String heart_price) {
        this.heart_price = heart_price;
    }

    public double getHeart_rank() {
        return heart_rank;
    }

    public void setHeart_rank(double heart_rank) {
        this.heart_rank = heart_rank;
    }

    public int getHeart_heart() {
        return heart_heart;
    }

    public void setHeart_heart(int heart_heart) {
        this.heart_heart = heart_heart;
    }

    public String getUser_key() {
        return User_key;
    }

    public void setUser_key(String user_key) {
        User_key = user_key;
    }

    public String getUser_UID() {
        return User_UID;
    }

    public void setUser_UID(String user_UID) {
        User_UID = user_UID;
    }
}