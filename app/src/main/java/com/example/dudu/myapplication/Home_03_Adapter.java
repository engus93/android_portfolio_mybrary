package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Home_03_Adapter extends RecyclerView.Adapter<Home_03_Adapter.home_03_re> {

    Context context;
    ArrayList<Home_02_02_ArrayList> home_02_02_Array;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    public Home_03_Adapter(Context context, ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList) {
        this.context = context;
        this.home_02_02_Array = home_02_02_ArrayList;
    }

    //틀 생성
    @Override
    public home_03_re onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_02, parent, false);
        return new home_03_re(v1);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(home_03_re holder, final int position) {

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(context);

        Log.d("체크", home_02_02_Array.get(position).book);

        //기본이미지일 경우
        if(home_02_02_Array.get(position).book.equals("null")){
            holder.book_image.setImageResource(R.drawable.home_02_default);
        }else{  //비트맵일 경우
            mGlideRequestManager.load(home_02_02_Array.get(position).book).into(holder.book_image);
        }

        holder.book_name.setText(home_02_02_Array.get(position).name);
        holder.book_author.setText(home_02_02_Array.get(position).author);
        holder.book_finish.setText(home_02_02_Array.get(position).finish);

        holder.click_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Toast.makeText(context, selItem, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, Home_03_View.class);
                intent1.putExtra("position", position);
                intent1.putExtra("mybrary_key", home_02_02_Array.get(position).user_key);
                intent1.putExtra("now_mybrary_uid", home_02_02_Array.get(position).user_uid);
                context.startActivity(intent1);
            }

        });

//        holder.click_item.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
////                Toast.makeText(context, selItem+"롱", Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(context, Home_02_03.class);
//                intent1.putExtra("position", position);
//                context.startActivity(intent1);
//
//                return true; // 다음 이벤트 계속 진행 false, 이벤트 완료 true
//            }
//        });

    }

    //현재 위치
    @Override
    public int getItemCount() {
        return home_02_02_Array.size();
    }

    public static class home_03_re extends RecyclerView.ViewHolder {

        //2번
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView book_finish;
        CardView click_item;

        home_03_re(View view) {
            super(view);
            book_image = view.findViewById(R.id.home_02_re_book_I);
            book_name = view.findViewById(R.id.home_02_re_book_name_T);
            book_author = view.findViewById(R.id.home_02_re_book_author_T);
            book_finish = view.findViewById(R.id.home_02_re_book_finish_T);
            click_item = view.findViewById(R.id.home_02_cardview);


        }

    }

}

