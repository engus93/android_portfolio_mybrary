package com.example.dudu.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_02_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private ArrayList<Home_02_01_ArrayList> home_02_01_ArrayList;

    public static class home_02_re_01 extends RecyclerView.ViewHolder {
        //1번
        ImageView user_fropile;
        TextView user_mini_01;
        TextView user_mini_02;
        TextView user_mini_03;
        TextView user_screen;

        home_02_re_01(View view){
            super(view);
            user_fropile = view.findViewById(R.id.home_02_re_fropile_I);
            user_mini_01 = view.findViewById(R.id.home_02_re_mini_1_T);
            user_mini_02 = view.findViewById(R.id.home_02_re_mini_2_T);
            user_mini_03 = view.findViewById(R.id.home_02_re_mini_3_T);
            user_screen = view.findViewById(R.id.home_02_re_screen_T);
        }

    }

    public static class home_02_re_02 extends RecyclerView.ViewHolder {

        //2번
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView book_finish;

        home_02_re_02(View view){
            super(view);
            book_image = view.findViewById(R.id.home_02_re_book_I);
            book_name = view.findViewById(R.id.home_02_re_book_name_T);
            book_author = view.findViewById(R.id.home_02_re_book_author_T);
            book_finish = view.findViewById(R.id.home_02_re_book_finish_T);
        }

    }

    @Override
    public int getItemViewType(int first) {
        return first;
    }


    public Home_02_Adapter(ArrayList<Home_02_01_ArrayList> home_02_01_ArrayList){
        this.home_02_01_ArrayList = home_02_01_ArrayList;
    }

    //틀 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0 : {
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_01, parent, false);
                return new home_02_re_01(v1);
            }
            default: {
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_02, parent, false);
                return new home_02_re_01(v2);
            }
        }



    }

    //묶어주기
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        home_02_re_01 myViewHolder = (home_02_re_01) holder;

        myViewHolder.user_fropile.setImageResource(home_02_01_ArrayList.get(position).fropile);
        myViewHolder.user_mini_01.setText(home_02_01_ArrayList.get(position).mini_01);
        myViewHolder.user_mini_02.setText(home_02_01_ArrayList.get(position).mini_02);
        myViewHolder.user_mini_03.setText(home_02_01_ArrayList.get(position).mini_03);
        myViewHolder.user_screen.setText(home_02_01_ArrayList.get(position).screen);
    }

    //현재 위치
    @Override
    public int getItemCount() {
        return home_02_01_ArrayList.size();
    }
}
