package com.example.dudu.myapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Home_01_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    private ArrayList<Home_01_ArrayList> Best_book_Info_ArrayList;

    Context context;

    Home_01_Adapter(Context context, ArrayList<Home_01_ArrayList> ArrayList) {
        this.context = context;
        this.Best_book_Info_ArrayList = ArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_01_re, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        mGlideRequestManager = Glide.with(context);

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        mGlideRequestManager.load(Best_book_Info_ArrayList.get(position).drawableId).fitCenter().into(myViewHolder.book_image);
        myViewHolder.book_rank.setText(Best_book_Info_ArrayList.get(position).rank);
        myViewHolder.book_name.setText(Best_book_Info_ArrayList.get(position).name);
        myViewHolder.book_author.setText(Best_book_Info_ArrayList.get(position).author);
        myViewHolder.book_date.setText(Best_book_Info_ArrayList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return Best_book_Info_ArrayList.size();
    }

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
}
