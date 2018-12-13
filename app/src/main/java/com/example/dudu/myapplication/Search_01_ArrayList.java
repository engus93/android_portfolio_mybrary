package com.example.dudu.myapplication;

import android.graphics.drawable.Drawable;

public class Search_01_ArrayList {
    public String drawableId;
    public String name;
    public String author;
    public String price;
    public double star;
    public String book_main;
    public String book_link;

    public Search_01_ArrayList(String drawableId, String name, String author, String price, double star, String book_main, String book_link){
        this.drawableId = drawableId;
        this.name = name;
        this.author = author;
        this.price = price;
        this.star = star;
        this.book_main = book_main;
        this.book_link = book_link;
    }

    public String getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(String drawableId) {

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

}