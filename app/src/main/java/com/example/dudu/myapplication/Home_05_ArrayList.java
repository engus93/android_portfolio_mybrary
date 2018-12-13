package com.example.dudu.myapplication;

import java.util.Comparator;

public class Home_05_ArrayList {
    public String heart_book;
    public String heart_name;
    public String heart_author;
    public String heart_price;
    public double heart_rank;
    public int heart_heart;
    public String user_key;
    public String login_user_uid;
    public String book_main;
    public String book_link;


    public Home_05_ArrayList(String book, String name, String author, String price, double rank, int heart_heart, String user_key, String login_user_uid,String book_main, String book_link) {
        this.heart_book = book;
        this.heart_name = name;
        this.heart_author = author;
        this.heart_price = price;
        this.heart_rank = rank;
        this.heart_heart = heart_heart;
        this.user_key = user_key;
        this.login_user_uid = login_user_uid;
        this.book_main = book_main;
        this.book_link = book_link;
    }

    public Home_05_ArrayList(){

    }

    public String getHeart_book() {
        return heart_book;
    }

    public void setHeart_book(String heart_book) {
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
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getLogin_user_uid() {
        return login_user_uid;
    }

    public void setLogin_user_uid(String login_user_uid) {
        this.login_user_uid = login_user_uid;
    }
}
