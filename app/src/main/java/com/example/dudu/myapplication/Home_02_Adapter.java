package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_02_Adapter extends RecyclerView.Adapter<Home_02_Adapter.home_02_re_02> {

    Context context;
    ArrayList<Home_02_02_ArrayList> home_02_02_Array;

    public Home_02_Adapter(Context context, ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList) {
        this.context = context;
        this.home_02_02_Array = home_02_02_ArrayList;
    }

    //틀 생성
    @Override
    public home_02_re_02 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_02, parent, false);
        return new home_02_re_02(v1);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(home_02_re_02 holder, final int position) {

//        String selItem = App.home_02_02_ArrayList.get(position).name;

        Log.d("체크", home_02_02_Array.get(position).book);

        //기본이미지일 경우
        if(home_02_02_Array.get(position).book.equals("null")){
            holder.book_image.setImageResource(R.drawable.home_02_default);
        }else{  //비트맵일 경우
            Bitmap bitmap_pic = App.getBitmap(home_02_02_Array.get(position).book);
            holder.book_image.setImageBitmap(bitmap_pic);
        }

        holder.book_name.setText(home_02_02_Array.get(position).name);
        holder.book_author.setText(home_02_02_Array.get(position).author);
        holder.book_finish.setText(home_02_02_Array.get(position).finish);

        holder.click_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Toast.makeText(context, selItem, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, Home_02_02.class);
                intent1.putExtra("position", position);
                context.startActivity(intent1);
            }

        });

        holder.click_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


//                Toast.makeText(context, selItem+"롱", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, Home_02_03.class);
                intent1.putExtra("position", position);
                context.startActivity(intent1);

                return true; // 다음 이벤트 계속 진행 false, 이벤트 완료 true
            }
        });

    }

    //현재 위치
    @Override
    public int getItemCount() {
        return home_02_02_Array.size();
    }

    public static class home_02_re_02 extends RecyclerView.ViewHolder {

        //2번
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView book_finish;
        CardView click_item;

        home_02_re_02(View view) {
            super(view);
            book_image = view.findViewById(R.id.home_02_re_book_I);
            book_name = view.findViewById(R.id.home_02_re_book_name_T);
            book_author = view.findViewById(R.id.home_02_re_book_author_T);
            book_finish = view.findViewById(R.id.home_02_re_book_finish_T);
            click_item = view.findViewById(R.id.home_02_cardview);


        }

    }

    //스트링 -> 비트맵 전환
    static Bitmap getBitmap(String zz) {

        byte[] decodedByteArray = Base64.decode(zz, Base64.NO_WRAP);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

        return decodedBitmap;
    }

}
