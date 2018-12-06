package com.example.dudu.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_04_Friend_Adapter extends RecyclerView.Adapter<Home_04_Friend_Adapter.home_04_friend_re> {

    Context context;
    List<Member_ArrayList> all_user_info;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;


    public Home_04_Friend_Adapter(Context context) {
        this.context = context;
        all_user_info = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                all_user_info.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Member_ArrayList member_arrayList = snapshot.getValue(Member_ArrayList.class);
                    if (member_arrayList.user_UID.equals(App.user_UID_get())) {
                        continue;
                    } else {
                        all_user_info.add(member_arrayList);
                    }
                }

                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //틀 생성
    @Override
    public home_04_friend_re onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_friendlist_re, parent, false);

        return new home_04_friend_re(view);

    }

    //묶어주기
    @Override
    public void onBindViewHolder(home_04_friend_re holder, final int position) {

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(context);

        mGlideRequestManager.load(all_user_info.get(position).user_profile).into(holder.user_profile);
        holder.user_nick.setText(all_user_info.get(position).getUser_nick());
        holder.user_id.setText(all_user_info.get(position).getMember_id());


        final String key = FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").push().getKey();

        //단체채팅 해쉬맵 세팅
        HashMap<String, String> chat_users_temp = new HashMap<>();
        chat_users_temp.put("room_key", key);
        chat_users_temp.put("user_1", App.user_UID_get());

        holder.click_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Home_04_Chatting.class);
                intent.putExtra("opponent_uid", all_user_info.get(position).user_UID);
                context.startActivity(intent);

            }
        });

    }


//        holder.user_invite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked) {
//
//                    Home_04_ChatRoom_Model temp = new Home_04_ChatRoom_Model(App.user_UID_get(), all_user_info.get(position).user_UID, key);
//
//                    //추가
//
//
//                    App.now_chat_users.add(temp);
//
//                    Log.d("채팅", "체크");
//
//                }else if(!isChecked){
//
//                    for(int i = 0; i < App.now_chat_users.size(); i++){
//
//                        if(App.now_chat_users.get(i).user_2.equals(all_user_info.get(position).user_UID)){
//
//                            Log.d("채팅", "체크 해제");
//
//                            App.now_chat_users.remove(i);
//                            break;
//
//                        }
//
//                    }
//
//                }else{
//                    MainActivity.showToast(context, "오류");
//                }
//            }
//        });


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

