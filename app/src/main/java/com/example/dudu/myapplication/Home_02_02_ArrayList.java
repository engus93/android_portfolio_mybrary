package com.example.dudu.myapplication;

public class Home_02_02_ArrayList {
    public String book;
    public String name;
    public String author;
    public String finish;
    public String main;
    public long date;

    public Home_02_02_ArrayList(String book, String name, String author, String finish, String main) {
        this.book = book;
        this.name = name;
        this.author = author;
        this.finish = finish;
        this.main = main;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}