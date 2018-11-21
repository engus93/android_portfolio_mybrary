package com.example.dudu.myapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_05_Adapter extends RecyclerView.Adapter<Home_05_Adapter.Home_Heart_ViewHolder>  {

    Context context;

    private ArrayList<Home_05_ArrayList> heart_book_ArrayList;

    Home_05_Adapter(Context context,ArrayList<Home_05_ArrayList> heartInfoArrayList){
        this.context = context;
        this.heart_book_ArrayList = heartInfoArrayList;
    }

    @Override
    public Home_Heart_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_05_re, parent, false);

        return new Home_Heart_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Home_Heart_ViewHolder holder, final int position) {

        final String selItem = heart_book_ArrayList.get(position).heart_name;

        holder.heart_book_image.setImageResource(heart_book_ArrayList.get(position).heart_book);
        holder.heart_book_name.setText(heart_book_ArrayList.get(position).heart_name);
        holder.heart_book_author.setText(heart_book_ArrayList.get(position).heart_author);
        holder.heart_book_price.setText(heart_book_ArrayList.get(position).heart_price);
        holder.heart_book_date.setText(heart_book_ArrayList.get(position).heart_date);

        holder.click_heart_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.heart_heart:
                        holder.click_heart_heart.setSelected(true);
                        break;
                }

                //어댑터에서 삭제
                heart_book_ArrayList.remove(position);

                // 어댑터에서 RecyclerView에 반영하도록 합니다.
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, heart_book_ArrayList.size());

                Toast.makeText(context, selItem + "가 찜목록에 삭제 되었습니다.", Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return heart_book_ArrayList.size();
    }

    public static class Home_Heart_ViewHolder extends RecyclerView.ViewHolder {
        ImageView heart_book_image;
        TextView heart_book_name;
        TextView heart_book_author;
        TextView heart_book_price;
        TextView heart_book_date;

        //클릭 변수
        CardView click_item;
        ImageButton click_heart_heart;

        Home_Heart_ViewHolder(View view){
            super(view);
            heart_book_image = view.findViewById(R.id.heart_book);
            heart_book_name = view.findViewById(R.id.heart_name);
            heart_book_author = view.findViewById(R.id.heart_author);
            heart_book_price = view.findViewById(R.id.heart_price);
            heart_book_date = view.findViewById(R.id.heart_date);
            click_heart_heart = view.findViewById(R.id.heart_heart);
        }
    }

}
