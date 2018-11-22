package com.example.dudu.myapplication;

public class Home_02_03_ArrayList {

    public int book;
    public String name;
    public String author;
    public String finish;
    public String main;

    public Home_02_03_ArrayList(int book, String name, String author, String finish, String main) {
        this.book = book;
        this.name = name;
        this.author = author;
        this.finish = finish;
        this.main = main;
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}


