package com.example.dudu.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public class Home_04_Chatting_Adapter extends RecyclerView.Adapter<Home_04_Chatting_Adapter.home_04_friend_re> {

    Context context;
    ArrayList<Home_04_ChattingList> chatlist;

    boolean overlabe;

    String uid = App.user_UID_get();

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    public Home_04_Chatting_Adapter(Context context, ArrayList<Home_04_ChattingList> all_user_info) {
        this.context = context;
        this.chatlist = all_user_info;
    }

    //틀 생성
    @Override
    public home_04_friend_re onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_chatting_re, parent, false);
        return new home_04_friend_re(v1);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(final home_04_friend_re holder, final int position) {

        if (App.now_chat_Contents.get(position).wright_uid.equals("Null")) {

            holder.user_profile.setVisibility(View.GONE);
            holder.user_nick.setVisibility(View.GONE);
            holder.user_contents.setVisibility(View.GONE);
            holder.getUser_contents_me.setVisibility(View.GONE);
            holder.time_you.setVisibility(View.GONE);
            holder.time_me.setVisibility(View.GONE);


        } else if (!(uid.equals(App.now_chat_Contents.get(position).wright_uid))) {

            holder.getUser_contents_me.setVisibility(View.GONE);
            holder.time_me.setVisibility(View.GONE);

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(context);

            //유저 정보
            FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    holder.user_nick.setText((CharSequence) dataSnapshot.child(App.opponent_uid).child("user_nick").getValue());

                    //프사가 있을 경우 경우
                    mGlideRequestManager.load((CharSequence) dataSnapshot.child(App.opponent_uid).child("user_profile").getValue()).into(holder.user_profile);

                    //시간
                    holder.time_you.setText(App.now_chat_Contents.get(position).time);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.user_contents.setText(App.now_chat_Contents.get(position).message);

        } else {

            holder.user_profile.setVisibility(View.GONE);
            holder.user_nick.setVisibility(View.GONE);
            holder.user_contents.setVisibility(View.GONE);
            holder.time_you.setVisibility(View.GONE);

            holder.getUser_contents_me.setText(App.now_chat_Contents.get(position).message);
            holder.time_me.setText(App.now_chat_Contents.get(position).time);

        }
    }


    //현재 위치
    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public static class home_04_friend_re extends RecyclerView.ViewHolder {

        ImageView user_profile;
        TextView user_nick;
        TextView user_contents;
        TextView getUser_contents_me;
        TextView time_you;
        TextView time_me;

        home_04_friend_re(View view) {
            super(view);
            user_profile = view.findViewById(R.id.home_04_chat_profile);
            user_nick = view.findViewById(R.id.home_04_chat_nick);
            user_contents = view.findViewById(R.id.home_04_chat_main);
            getUser_contents_me = view.findViewById(R.id.home_04_chat_main_me);
            time_you = view.findViewById(R.id.time_you);
            time_me = view.findViewById(R.id.time_me);

        }

    }

}

