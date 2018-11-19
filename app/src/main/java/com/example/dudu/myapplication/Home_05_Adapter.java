package com.example.dudu.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_05_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class Home_Heart_ViewHolder extends RecyclerView.ViewHolder {
        ImageView heart_book_image;
        TextView heart_book_name;
        TextView heart_book_author;
        TextView heart_book_price;
        TextView heart_book_date;
        ImageView heart_heart_image;


        Home_Heart_ViewHolder(View view){
            super(view);
            heart_book_image = view.findViewById(R.id.heart_book);
            heart_book_name = view.findViewById(R.id.heart_name);
            heart_book_author = view.findViewById(R.id.heart_author);
            heart_book_price = view.findViewById(R.id.heart_price);
            heart_book_date = view.findViewById(R.id.heart_date);
            heart_heart_image = view.findViewById(R.id.heart_heart);
        }
    }

    private ArrayList<Home_05_ArrayList> heart_book_ArrayList;
    Home_05_Adapter(ArrayList<Home_05_ArrayList> heartInfoArrayList){this.heart_book_ArrayList = heartInfoArrayList; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_05_re, parent, false);

        return new Home_Heart_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Home_Heart_ViewHolder myViewHolder = (Home_Heart_ViewHolder) holder;

        myViewHolder.heart_book_image.setImageResource(heart_book_ArrayList.get(position).heart_book);
        myViewHolder.heart_book_name.setText(heart_book_ArrayList.get(position).heart_name);
        myViewHolder.heart_book_author.setText(heart_book_ArrayList.get(position).heart_author);
        myViewHolder.heart_book_price.setText(heart_book_ArrayList.get(position).heart_price);
        myViewHolder.heart_book_date.setText(heart_book_ArrayList.get(position).heart_date);
        myViewHolder.heart_heart_image.setImageResource(heart_book_ArrayList.get(position).heart_heart);
    }

    @Override
    public int getItemCount() {
        return heart_book_ArrayList.size();
    }
}
