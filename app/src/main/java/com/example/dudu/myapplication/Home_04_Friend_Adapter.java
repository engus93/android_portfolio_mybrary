package com.example.dudu.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Home_04_Friend_Adapter extends RecyclerView.Adapter<Home_04_Friend_Adapter.home_04_friend_re> {

    Context context;
    ArrayList<Member_ArrayList> all_user_info;

    boolean overlabe;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    public Home_04_Friend_Adapter(Context context, ArrayList<Member_ArrayList> all_user_info) {
        this.context = context;
        this.all_user_info = all_user_info;
    }

    //틀 생성
    @Override
    public home_04_friend_re onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_friendlist_re, parent, false);
        return new home_04_friend_re(v1);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(home_04_friend_re holder, final int position) {

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(context);

        //프사가 있을 경우 경우
        mGlideRequestManager.load(all_user_info.get(position).user_profile).into(holder.user_profile);
        holder.user_nick.setText(all_user_info.get(position).user_nick);
        holder.user_id.setText(all_user_info.get(position).member_id);

        holder.click_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                //파이어베이스 데이터베이스 선언
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("User_Message").child("User_Room");

                final String key = myRef.push().getKey();

                for(int i = 0; i < App.user_chat_room.size(); i++){

                    if (App.user_chat_room.get(i).user_1.equals(App.user_UID_get()) && App.user_chat_room.get(i).user_2.equals(all_user_info.get(position).user_UID)) {

                        overlabe = false;
                        break;

                    } else if (App.user_chat_room.get(i).user_2.equals(App.user_UID_get()) && App.user_chat_room.get(i).user_1.equals(all_user_info.get(position).user_UID)) {

                        overlabe = false;
                        break;

                    } else {

                        overlabe = true;

                    }
                }

                if (overlabe) {
                    Home_04_Single_Chatting chatroom = new Home_04_Single_Chatting(App.user_UID_get(), all_user_info.get(position).user_UID, key);

                    //파이어베이스에 저장
                    myRef.child(key).setValue(chatroom);

                    overlabe = false;
                }

                Intent intent1 = new Intent(context, Home_04_Chatting.class);
                intent1.putExtra("position", position);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent1);
            }

        });

    }

    //현재 위치
    @Override
    public int getItemCount() {
        return all_user_info.size();
    }

    public static class home_04_friend_re extends RecyclerView.ViewHolder {

        ImageView user_profile;
        TextView user_nick;
        TextView user_id;
        CheckBox user_invite;
        CardView click_item;

        home_04_friend_re(View view) {
            super(view);
            user_profile = view.findViewById(R.id.home_04_friend_profile);
            user_nick = view.findViewById(R.id.home_04_friend_nick);
            user_id = view.findViewById(R.id.home_04_friend_id);
            user_invite = view.findViewById(R.id.home_04_friend_invite);
            click_item = view.findViewById(R.id.home_04_friend_cardview);


        }

    }

}

