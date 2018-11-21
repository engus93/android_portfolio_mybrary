package com.example.dudu.myapplication;

public class Search_01_ArrayList {
    public int drawableId;
    public String name;
    public String author;
    public String date;
    public double star;

    public Search_01_ArrayList(int drawableId, String name, String author, double star){
        this.drawableId = drawableId;
        this.name = name;
        this.author = author;
        this.star = star;
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

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }
}