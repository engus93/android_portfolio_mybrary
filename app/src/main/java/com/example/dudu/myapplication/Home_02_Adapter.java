package com.example.dudu.myapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_02_Adapter extends RecyclerView.Adapter<Home_02_Adapter.home_02_re_02>  {

    Context context;

    private ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList;

    public Home_02_Adapter(Context context,ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList){
        this.context = context;
        this.home_02_02_ArrayList = home_02_02_ArrayList;
    }

    //틀 생성
    @Override
    public home_02_re_02 onCreateViewHolder(ViewGroup parent, int viewType) {

        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_02, parent, false);

        return new home_02_re_02(v1);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(home_02_re_02 holder, int position) {

        final String selItem = home_02_02_ArrayList.get(position).name;

        holder.book_image.setImageResource(home_02_02_ArrayList.get(position).book);
        holder.book_name.setText(home_02_02_ArrayList.get(position).name);
        holder.book_author.setText(home_02_02_ArrayList.get(position).author);
        holder.book_finish.setText(home_02_02_ArrayList.get(position).finish);

        holder.click_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,selItem,Toast.LENGTH_SHORT).show();
            }
        });

    }

    //현재 위치
    @Override
    public int getItemCount() {
        return home_02_02_ArrayList.size();
    }

    public static class home_02_re_02 extends RecyclerView.ViewHolder {

        //2번
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView book_finish;
        CardView click_item;

        home_02_re_02(View view){
            super(view);
            book_image = view.findViewById(R.id.home_02_re_book_I);
            book_name = view.findViewById(R.id.home_02_re_book_name_T);
            book_author = view.findViewById(R.id.home_02_re_book_author_T);
            book_finish = view.findViewById(R.id.home_02_re_book_finish_T);
            click_item = view.findViewById(R.id.home_02_cardview);

        }

    }

}
