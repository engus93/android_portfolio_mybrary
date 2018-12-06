//package com.example.dudu.myapplication;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//public class Home_04_Chatting_Adapter extends RecyclerView.Adapter<Home_04_Chatting_Adapter.home_04_chatting_re> {
//
//    Context context;
//    ArrayList<Home_04_ChatRoom_Model.Message> contents = new ArrayList<>();
//
//    //글라이드 오류 방지
//    public RequestManager mGlideRequestManager;
//
//    public Home_04_Chatting_Adapter(Context context, ArrayList<Home_04_ChatRoom_Model.Message> contents) {
//        this.context = context;
//        this.contents = contents;
//    }
//
//    //틀 생성
//    @Override
//    public home_04_chatting_re onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_chatting_re, parent, false);
//            return new home_04_chatting_re(v1);
//    }
//
//    //묶어주기
//    @Override
//    public void onBindViewHolder(final home_04_chatting_re holder, final int position) {
//
//        String msg = App.now_chat_Contents.get(position).message;
//        String time = App.now_chat_Contents.get(position).time;
//        String now_uid = App.now_chat_Contents.get(position).wright_uid;
//
//        holder.user_contents.setVisibility(View.VISIBLE);
//        holder.getUser_contents_me.setVisibility(View.VISIBLE);
//        holder.time_me.setVisibility(View.VISIBLE);
//        holder.user_profile.setVisibility(View.VISIBLE);
//        holder.user_nick.setVisibility(View.VISIBLE);
//        holder.time_you.setVisibility(View.VISIBLE);
//        holder.read_you.setVisibility(View.VISIBLE);
//        holder.read_me.setVisibility(View.VISIBLE);
//
//        //글라이드 오류 방지
//        mGlideRequestManager = Glide.with(context);
//
//        if (App.now_chat_Contents.get(position).wright_uid.equals("")) {
//
//            holder.user_profile.setVisibility(View.GONE);
//            holder.user_nick.setVisibility(View.GONE);
//            holder.user_contents.setVisibility(View.GONE);
//            holder.getUser_contents_me.setVisibility(View.GONE);
//            holder.time_you.setVisibility(View.GONE);
//            holder.time_me.setVisibility(View.GONE);
//            holder.read_you.setVisibility(View.GONE);
//            holder.read_me.setVisibility(View.GONE);
//
//
//        } else if (!(App.user_UID_get().equals(now_uid))) {
//
//            //나
//            holder.getUser_contents_me.setVisibility(View.GONE);
//            holder.time_me.setVisibility(View.GONE);
//            holder.read_me.setVisibility(View.GONE);
//
//            //상대방
//            //닉네임
//            holder.user_nick.setText(App.opponent.opponent_nick);
//            //사진
//            mGlideRequestManager.load(App.opponent.opponent_profile).into(holder.user_profile);
//            //시간
//            holder.time_you.setText(time);
//
//            //사진 없을 때
//            if (App.now_chat_Contents.get(position).picture.equals("")) {
//                holder.user_contents.setText(msg);
//            } else {
//                Glide.with(context)
//                        .load(App.now_chat_Contents.get(position).picture)
//                        .fitCenter()
//                        .override(700, 1000)
//                        .error(null)
//                        .into(new SimpleTarget<GlideDrawable>() {
//                            @Override
//                            public void onResourceReady(GlideDrawable glideDrawable
//
//                                    , GlideAnimation<? super GlideDrawable> glideAnimation) {
//                                holder.user_contents.setText("");
//                                holder.user_contents.setBackground(glideDrawable.getCurrent());
//                            }
//                        });
//
//            }
//
//            //읽음표시
////            read_user_count(position, holder.read_you);
//
//        } else {
//            //상대방
//            holder.user_profile.setVisibility(View.GONE);
//            holder.user_nick.setVisibility(View.GONE);
//            holder.user_contents.setVisibility(View.GONE);
//            holder.time_you.setVisibility(View.GONE);
//            holder.read_you.setVisibility(View.GONE);
//
//            //이미지 없을 때
//            if (App.now_chat_Contents.get(position).picture.equals("")) {
//                holder.getUser_contents_me.setText(msg);
//            } else {
//                Glide.with(context)
//                        .load(App.now_chat_Contents.get(position).picture)
//                        .fitCenter()
//                        .override(700, 1000)
//                        .error(null)
//                        .into(new SimpleTarget<GlideDrawable>() {
//                            @Override
//                            public void onResourceReady(GlideDrawable glideDrawable
//
//                                    , GlideAnimation<? super GlideDrawable> glideAnimation) {
//                                holder.getUser_contents_me.setText("");
//                                holder.getUser_contents_me.setBackground(glideDrawable.getCurrent());
//                            }
//                        });
//
//            }
//
//            //나
//            holder.time_me.setText(time);
//            //읽음표시
////            read_user_count(position, holder.read_me);
//
//        }
//
//
//    }
//
////    void read_user_count(final int position, final TextView textView){
////
////        FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").child(App.now_chat_user.room_key).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////
////                Map<String, String> chat_user = (Map<String, String>) dataSnapshot.getValue();
////
////                System.out.println(App.now_chat_Contents.get(position).read.size());
////
////                int count = (chat_user.size() - 1) - App.now_chat_Contents.get(position).read.size();
////                if(count > 0){
////                    textView.setVisibility((View.VISIBLE));
////                    textView.setText(String.valueOf(count));
////                }else{
////                    textView.setVisibility(View.INVISIBLE);
////                }
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
////
////    }
//
//
//    //현재 위치
//    @Override
//    public int getItemCount() { return App.now_chat_Contents.size(); }
//
//    public static class home_04_chatting_re extends RecyclerView.ViewHolder {
//
//        ImageView user_profile;
//        TextView user_nick;
//        TextView user_contents;
//        TextView getUser_contents_me;
//        TextView time_you;
//        TextView time_me;
//        TextView read_you;
//        TextView read_me;
//
//        home_04_chatting_re(View view) {
//            super(view);
//            user_profile = view.findViewById(R.id.home_04_chat_profile);
//            user_nick = view.findViewById(R.id.home_04_chat_nick);
//            user_contents = view.findViewById(R.id.home_04_chat_main);
//            getUser_contents_me = view.findViewById(R.id.home_04_chat_main_me);
//            time_you = view.findViewById(R.id.time_you);
//            time_me = view.findViewById(R.id.time_me);
//            read_you = view.findViewById(R.id.home_04_chat_read_you);
//            read_me = view.findViewById(R.id.home_04_chat_read_me);
//
//        }
//
//    }
//
//}
//
