package com.example.dudu.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class Home_01_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView book_image;
        TextView book_rank;
        TextView book_name;
        TextView book_author;
        TextView book_date;

        MyViewHolder(View view){
            super(view);
            book_image = view.findViewById(R.id.book_image_B);
            book_rank = view.findViewById(R.id.book_rank);
            book_name = view.findViewById(R.id.book_name);
            book_author = view.findViewById(R.id.book_author);
            book_date = view.findViewById(R.id.book_date);
        }
    }

    private ArrayList<Home_01_ArrayList> Best_book_Info_ArrayList;
    Home_01_Adapter(ArrayList<Home_01_ArrayList> ArrayList){this.Best_book_Info_ArrayList = ArrayList; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_01_re, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.book_image.setImageResource(Best_book_Info_ArrayList.get(position).drawableId);
        myViewHolder.book_rank.setText(Best_book_Info_ArrayList.get(position).rank);
        myViewHolder.book_name.setText(Best_book_Info_ArrayList.get(position).name);
        myViewHolder.book_author.setText(Best_book_Info_ArrayList.get(position).author);
        myViewHolder.book_date.setText(Best_book_Info_ArrayList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return Best_book_Info_ArrayList.size();
    }
}
