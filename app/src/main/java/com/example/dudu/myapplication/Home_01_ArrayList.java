package com.example.dudu.myapplication;

public class Home_01_ArrayList {
    public int drawableId;
    public String name;
    public String rank;
    public String author;
    public String date;

    public Home_01_ArrayList(int drawableId, String rank, String name, String author, String date){
        this.drawableId = drawableId;
        this.name = name;
        this.rank = rank;
        this.author = author;
        this.date = date;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}