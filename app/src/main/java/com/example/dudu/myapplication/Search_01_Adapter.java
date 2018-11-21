package com.example.dudu.myapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search_01_Adapter extends RecyclerView.Adapter<Search_01_Adapter.MyViewHolder>  {

    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        RatingBar book_star;
        CardView click_item;


        MyViewHolder(View view){
            super(view);
            book_image = view.findViewById(R.id.search_book);
            book_name = view.findViewById(R.id.search_name);
            book_author = view.findViewById(R.id.search_author);
            book_star = view.findViewById(R.id.search_star);
            click_item = view.findViewById(R.id.search_cardview);
        }
    }

    private ArrayList<Search_01_ArrayList> search_book_ArrayList;
    private CardView click_item;

    Search_01_Adapter(Context context,ArrayList<Search_01_ArrayList> searchInfoArrayList){
        this.context = context;
        this.search_book_ArrayList = searchInfoArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_01_re, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final String selItem = search_book_ArrayList.get(position).name;

//        MyViewHolder myViewHolder = (MyViewHolder) holder;

        holder.book_image.setImageResource(search_book_ArrayList.get(position).drawableId);
        holder.book_name.setText(search_book_ArrayList.get(position).name);
        holder.book_author.setText(search_book_ArrayList.get(position).author);
        holder.book_star.setRating((float) search_book_ArrayList.get(position).star);


        holder.click_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,selItem,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return search_book_ArrayList.size();
    }
}
