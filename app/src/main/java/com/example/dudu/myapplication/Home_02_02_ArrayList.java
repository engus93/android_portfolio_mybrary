package com.example.dudu.myapplication;

import android.net.Uri;

public class Home_02_02_ArrayList {
    public Uri book;
    public String name;
    public String author;
    public String finish;
    public String main;

    public Home_02_02_ArrayList(Uri book, String name, String author, String finish, String main) {
        this.book = book;
        this.name = name;
        this.author = author;
        this.finish = finish;
        this.main = main;
    }

    public Uri getBook() {
        return book;
    }

    public void setBook(Uri book) {
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
}