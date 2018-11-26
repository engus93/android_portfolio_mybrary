package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Search_01_Adapter extends RecyclerView.Adapter<Search_01_Adapter.MyViewHolder>  {

    Context context;
    boolean book_check;

    static ArrayList<Search_01_ArrayList> search_book_ArrayList;


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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String selItem = search_book_ArrayList.get(position).name;

        holder.book_image.setImageResource(search_book_ArrayList.get(position).drawableId);
        holder.book_name.setText(search_book_ArrayList.get(position).name);
        holder.book_author.setText(search_book_ArrayList.get(position).author);
        holder.book_price.setText(search_book_ArrayList.get(position).price);
        holder.book_star.setRating((float) search_book_ArrayList.get(position).star);


        holder.click_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,selItem,Toast.LENGTH_SHORT).show();
            }
        });

        holder.heart_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < App.heart_book_ArrayList.size(); i++) {

                    String name = App.heart_book_ArrayList.get(i).heart_name;

                    if (!name.equals(selItem)) {
                        book_check = true;

                        Log.d("체크", "체크 true");

                    } else {
                        book_check = false;

                        Log.d("체크", "체크 false");

                        break;
                    }

                    Log.d("체크", "break");

                }


                    if (book_check || App.heart_book_ArrayList.size() == 0) {

                        //키보드 내리기
                        if (v != null) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Log.d("체크", "찜");

                        App.heart_book_ArrayList.add(new Home_05_ArrayList(search_book_ArrayList.get(position).drawableId, search_book_ArrayList.get(position).name, search_book_ArrayList.get(position).author, search_book_ArrayList.get(position).price, (float) search_book_ArrayList.get(position).star, R.drawable.home_05_heart_02));

                            Toast.makeText(context, selItem + "가 찜목록에 추가 되었습니다.", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("체크", "체크 false 02");
                        //키보드 내리기
                        if (v != null) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, selItem + "가 이미 찜목록에 있습니다.", Toast.LENGTH_SHORT).show();

                        book_check = true;

                    }

                }
        });

        holder.click_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
//                Toast.makeText(context, selItem, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, Search_02.class);
                intent1.putExtra("position", position);
                context.startActivity(intent1);

            }
        });


    }

    @Override
    public int getItemCount() {
        return search_book_ArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView book_price;
        RatingBar book_star;
        //클릭 변수
        CardView click_item;
        ImageButton heart_bt;

        MyViewHolder(View view) {
            super(view);
            book_image = view.findViewById(R.id.search_book);
            book_name = view.findViewById(R.id.search_name);
            book_author = view.findViewById(R.id.search_author);
            book_price = view.findViewById(R.id.search_price);
            book_star = view.findViewById(R.id.search_star);
            click_item = view.findViewById(R.id.search_cardview);
            heart_bt = view.findViewById(R.id.search_heart_B);
        }
    }

}



