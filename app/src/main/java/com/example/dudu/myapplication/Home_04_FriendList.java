package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_04_FriendList extends AppCompatActivity {


    ImageView home_04_friend_send; //채팅방 만들기
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼

    List<Member_ArrayList> all_user_info;
    Home_04_ChatRoom_Model chatRoom_model = new Home_04_ChatRoom_Model();

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    String roomkey;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_friendlist);


        home_04_friend_send = findViewById(R.id.home_04_friend_send);
        home_04_friendlist_back_B = findViewById(R.id.home_04_friendlist_back_B);

        //다중 사용자 채팅시
        home_04_friend_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatRoom_model.users.put(App.user_UID_get(), true);
                FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(roomkey).setValue(chatRoom_model);

                Intent intent = new Intent(view.getContext(), Home_04_Group_Chatting.class);
                intent.putExtra("chat_room_key", roomkey);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                onBackPressed();

            }
        });

        //뒤로가기
        home_04_friendlist_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //---------------------------리싸이클러뷰---------------------------------
        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_04_friend_re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new Home_04_Friend_Adapter());

    }

    public class Home_04_Friend_Adapter extends RecyclerView.Adapter<Home_04_Friend_Adapter.home_04_friend_re> {

        public Home_04_Friend_Adapter() {

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
        public Home_04_Friend_Adapter.home_04_friend_re onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_friendlist_re, parent, false);

            return new Home_04_Friend_Adapter.home_04_friend_re(view);

        }

        //묶어주기
        @Override
        public void onBindViewHolder(home_04_friend_re holder, final int position) {

            roomkey = null;

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_04_FriendList.this);

            mGlideRequestManager.load(all_user_info.get(position).user_profile).into(holder.user_profile);
            holder.user_nick.setText(all_user_info.get(position).getUser_nick());
            holder.user_id.setText(all_user_info.get(position).getMember_id());

            roomkey = FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").push().getKey();

            //채팅 상대 클릭 채팅 시작
            holder.click_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), Home_04_Chatting.class);
                    intent.putExtra("opponent_uid", all_user_info.get(position).user_UID);
                    startActivity(intent);

                }
            });

            //단체 채팅을 위한 체크
            holder.user_invite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        chatRoom_model.users.put(all_user_info.get(position).user_UID, true);
                        Log.d("체크", "된다");

                    } else if (!isChecked) {

                        chatRoom_model.users.remove(all_user_info.get(position));
                        Log.d("체크", "해제");

                    } else {
                        MainActivity.showToast(Home_04_FriendList.this, "오류");
                    }
                }
            });

        }


        //현재 위치
        @Override
        public int getItemCount() {
            return all_user_info.size();
        }

        public class home_04_friend_re extends RecyclerView.ViewHolder {

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



}
