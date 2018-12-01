package com.example.dudu.myapplication;

public class Home_02_02_ArrayList {
    public String book;
    public String name;
    public String author;
    public String finish;
    public String main;
    public String user_key;
    public String user_uid;

    public Home_02_02_ArrayList(String book, String name, String author, String finish, String main, String user_key, String user_uid) {
        this.book = book;
        this.name = name;
        this.author = author;
        this.finish = finish;
        this.main = main;
        this.user_key = user_key;
        this.user_uid = user_uid;
    }

    public Home_02_02_ArrayList(){

    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
}