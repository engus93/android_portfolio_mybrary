package com.example.dudu.myapplication;

public class Home_02_02_ArrayList {
    public int book;
    public String name;
    public String author;
    public String finish;

    public Home_02_02_ArrayList(int book, String name, String author, String finish) {
        this.book = book;
        this.name = name;
        this.author = author;
        this.finish = finish;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
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
}