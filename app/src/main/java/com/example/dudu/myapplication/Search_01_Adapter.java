package com.example.dudu.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Search_01_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        RatingBar book_star;


        MyViewHolder(View view){
            super(view);
            book_image = view.findViewById(R.id.search_book);
            book_name = view.findViewById(R.id.search_name);
            book_author = view.findViewById(R.id.search_author);
            book_star = view.findViewById(R.id.search_star);
        }
    }

    private ArrayList<Search_01_ArrayList> search_book_ArrayList;
    Search_01_Adapter(ArrayList<Search_01_ArrayList> searchInfoArrayList){this.search_book_ArrayList = searchInfoArrayList; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_01_re, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.book_image.setImageResource(search_book_ArrayList.get(position).drawableId);
        myViewHolder.book_name.setText(search_book_ArrayList.get(position).name);
        myViewHolder.book_author.setText(search_book_ArrayList.get(position).author);
        myViewHolder.book_star.setRating((float) search_book_ArrayList.get(position).star);
    }

    @Override
    public int getItemCount() {
        return search_book_ArrayList.size();
    }
}
