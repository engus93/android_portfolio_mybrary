package com.example.dudu.myapplication;


import android.app.Activity;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Home_04_Chatting_Adapter extends RecyclerView.Adapter<Home_04_Chatting_Adapter.home_04_chatting_re> {

    Context context;
    ArrayList<Home_04_ChattingList> chatlist = new ArrayList<>();

    int user_count;

    //글라이드 오류 방지
    RequestManager mGlideRequestManager;

    public Home_04_Chatting_Adapter(Context context, ArrayList<Home_04_ChattingList> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }

    //틀 생성
    @Override
    public home_04_chatting_re onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_chatting_re, parent, false);
        return new home_04_chatting_re(v1);
    }

    //묶어주기
    @Override
    public void onBindViewHolder(final home_04_chatting_re holder, int position) {

        String msg = App.now_chat_Contents.get(position).message;
        String time = App.now_chat_Contents.get(position).time;
        String now_uid = App.now_chat_Contents.get(position).wright_uid;

        holder.user_profile.setVisibility(View.GONE);
        holder.user_nick.setVisibility(View.GONE);
        holder.user_contents.setVisibility(View.GONE);
        holder.getUser_contents_me.setVisibility(View.GONE);
        holder.time_you.setVisibility(View.GONE);
        holder.time_me.setVisibility(View.GONE);
        holder.read_you.setVisibility(View.GONE);
        holder.read_me.setVisibility(View.GONE);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(context);

        holder.getUser_contents_me.setText("");
        holder.user_contents.setText("");

        if (App.now_chat_Contents.get(position).wright_uid.equals("Null")) {

            //사진이 없을 경우
        } else if (App.now_chat_Contents.get(position).picture.equals("")) {

            //내가 아닌 사람
            if (!(App.user_UID_get().equals(now_uid))) {

                holder.user_contents.setVisibility(View.VISIBLE);
                holder.user_profile.setVisibility(View.VISIBLE);
                holder.user_nick.setVisibility(View.VISIBLE);
                holder.time_you.setVisibility(View.VISIBLE);
                holder.read_you.setVisibility(View.VISIBLE);

                //상대방
                //닉네임
                holder.user_nick.setText(App.opponent.opponent_nick);
                //사진
                mGlideRequestManager.load(App.opponent.opponent_profile).into(holder.user_profile);
                //읽음표시
                read_user_count(position, holder.read_you);
                //시간
                holder.time_you.setText(time);
                //메세지
                holder.user_contents.setText(msg);

                //나
            } else {

                holder.getUser_contents_me.setVisibility(View.VISIBLE);
                holder.time_me.setVisibility(View.VISIBLE);
                holder.read_me.setVisibility(View.VISIBLE);

                //나
                holder.getUser_contents_me.setText(msg);
                holder.time_me.setText(time);
                //읽음표시
                read_user_count(position, holder.read_me);

            }

        } else { //사진이 있는 경우

            if (!(App.user_UID_get().equals(now_uid))) {

                holder.user_contents.setVisibility(View.VISIBLE);
                holder.user_profile.setVisibility(View.VISIBLE);
                holder.user_nick.setVisibility(View.VISIBLE);
                holder.time_you.setVisibility(View.VISIBLE);
                holder.read_you.setVisibility(View.VISIBLE);

                //닉네임
                holder.user_nick.setText(App.opponent.opponent_nick);
                //사진
                mGlideRequestManager.load(App.opponent.opponent_profile).into(holder.user_profile);
                //읽음표시
                read_user_count(position, holder.read_you);
                //시간
                holder.time_you.setText(time);

//                setGlideView(App.now_chat_Contents.get(position).picture, holder.user_contents);

            } else {

                holder.getUser_contents_me.setVisibility(View.VISIBLE);
                holder.time_me.setVisibility(View.VISIBLE);
                holder.read_me.setVisibility(View.VISIBLE);

                //나
                holder.time_me.setText(time);
                //읽음표시
                read_user_count(position, holder.read_me);

//                setGlideView(App.now_chat_Contents.get(position).picture, holder.getUser_contents_me);

            }

        }

        System.out.println(position);
        System.out.println(App.now_chat_Contents.get(position).picture);

    }

    void read_user_count(final int position, final TextView textView) {

        if (user_count == 0) {

            FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").child(App.now_chat_user.room_key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String, String> chat_user = (Map<String, String>) dataSnapshot.getValue();
                    user_count = chat_user.size() - 1;

                    int count = user_count - App.now_chat_Contents.get(position).read.size();
                    if (count > 0) {
                        textView.setVisibility((View.VISIBLE));
                        textView.setText(String.valueOf(count));
                    } else {
                        textView.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{

            int count = user_count - App.now_chat_Contents.get(position).read.size();
            if (count > 0) {
                textView.setVisibility((View.VISIBLE));
                textView.setText(String.valueOf(count));
            } else {
                textView.setVisibility(View.INVISIBLE);
            }

        }

    }


    private void setGlideView(String path, final TextView iv) {
        Glide.clear(iv);
        Glide.with(context).load(path).fitCenter().override(700,1000).signature(new StringSignature(UUID.randomUUID().toString())).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable
                    , GlideAnimation<? super GlideDrawable> glideAnimation) {
                iv.setText("");
                iv.setBackground(glideDrawable.getCurrent());
            }
        });
    }


    //현재 위치
    @Override
    public int getItemCount() { return App.now_chat_Contents.size(); }

    public static class home_04_chatting_re extends RecyclerView.ViewHolder {

        ImageView user_profile;
        TextView user_nick;
        TextView user_contents;
        TextView getUser_contents_me;
        TextView time_you;
        TextView time_me;
        TextView read_you;
        TextView read_me;

        home_04_chatting_re(View view) {
            super(view);
            user_profile = view.findViewById(R.id.home_04_chat_profile);
            user_nick = view.findViewById(R.id.home_04_chat_nick);
            user_contents = view.findViewById(R.id.home_04_chat_main);
            getUser_contents_me = view.findViewById(R.id.home_04_chat_main_me);
            time_you = view.findViewById(R.id.time_you);
            time_me = view.findViewById(R.id.time_me);
            read_you = view.findViewById(R.id.home_04_chat_read_you);
            read_me = view.findViewById(R.id.home_04_chat_read_me);

        }

    }

}

