package com.example.dudu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mozilla.javascript.tools.jsc.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Home_04_FriendList extends AppCompatActivity {


    ImageView home_04_friend_send; //채팅방 만들기
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    EditText home_04_friend_search;
    Button home_04_friend_search_B;

    List<Member_ArrayList> all_user_info;
    List<Member_ArrayList> search_user_info = new ArrayList<>();
    Home_04_ChatRoom_Model chatRoom_model = new Home_04_ChatRoom_Model();

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    String roomkey;
    String room_name = "";

    Home_04_Friend_Adapter home_04_friend_adapter = new Home_04_Friend_Adapter();

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_friendlist);


        home_04_friend_send = findViewById(R.id.home_04_friend_send);
        home_04_friendlist_back_B = findViewById(R.id.home_04_friendlist_back_B);
        home_04_friend_search = findViewById(R.id.home_04_friend_search_ET);
        home_04_friend_search_B = findViewById(R.id.home_04_friend_search_B);

        //다중 사용자 채팅시
        home_04_friend_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (chatRoom_model.users.size() > 1) {

                    chatRoom_model.users.put(App.user_UID_get(), true);
                    chatRoom_model.now_login.put(App.user_UID_get(), true);
                    FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(roomkey).setValue(chatRoom_model);

                    final Iterator<Map.Entry<String, Boolean>> entries = chatRoom_model.users.entrySet().iterator();

                    final String[] zz = new String[chatRoom_model.users.size() - 1];

                    int i = 0;

                    while (entries.hasNext()) {

                        final Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) entries.next();

                        System.out.println("key : " + entry.getKey() + " , value : " + entry.getValue());

                        if (!(entry.getKey().equals(App.user_UID_get()))) {
                            zz[i] = entry.getKey();
                            i += 1;
                        }

                    }

                    FirebaseDatabase.getInstance().getReference("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i = 0; i < zz.length; i++) {

                                String temp = zz[i];

                                if (i == chatRoom_model.users.size() - 2) {
                                    room_name += dataSnapshot.child(temp).child("user_nick").getValue() + " (" + chatRoom_model.users.size() + "명)";
                                    Log.d("체크", "4번 타자");
                                } else {
                                    room_name += dataSnapshot.child(temp).child("user_nick").getValue() + ", ";
                                    Log.d("체크", "1번 타자");
                                }
                            }

                            Intent intent = new Intent(view.getContext(), Home_04_Group_Chatting.class);
                            intent.putExtra("chat_room_key", roomkey);
                            intent.putExtra("chat_room_name", room_name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            onBackPressed();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }else{

                    MainActivity.showToast(Home_04_FriendList.this, "단체 채팅은 2명 이상의 친구를 초대해주세요.");

                }
            }
        });

        home_04_friend_search_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.showToast(Home_04_FriendList.this, "검색");

            }
        });

        home_04_friend_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String searchText = home_04_friend_search.getText().toString();
                home_04_friend_adapter.filter(searchText);

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

        mRecyclerView.setAdapter(home_04_friend_adapter);



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
                public void onClick(final View v) {

                    FirebaseDatabase.getInstance().getReference().child("Chatting_Room").orderByChild("users/" + App.user_UID_get()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Boolean skip = true;

                            for (DataSnapshot item : dataSnapshot.getChildren()) {

                                Home_04_ChatRoom_Model chatRoom_model = item.getValue(Home_04_ChatRoom_Model.class);

                                if (chatRoom_model.users.containsKey(all_user_info.get(position).user_UID) && chatRoom_model.users.size() == 2) {

                                    roomkey = item.getKey();

                                    Log.d("체크", "넌 되냐");

                                    skip = false;

                                }

                            }

                            if (skip) {
                                chatRoom_model.users.put(App.user_UID_get(), true);
                                chatRoom_model.users.put(all_user_info.get(position).user_UID, true);
                                chatRoom_model.now_login.put(App.user_UID_get(), true);
                                chatRoom_model.now_login.put(all_user_info.get(position).user_UID, true);

                                FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(roomkey).setValue(chatRoom_model);

                                Log.d("체크", "뭐지");

                            }

                            Intent intent = new Intent(v.getContext(), Home_04_Chatting.class);
                            intent.putExtra("opponent_uid", all_user_info.get(position).user_UID);
                            intent.putExtra("chat_room_key", roomkey);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            //단체 채팅을 위한 체크
            holder.user_invite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        chatRoom_model.users.put(all_user_info.get(position).user_UID, true);
                        chatRoom_model.now_login.put(all_user_info.get(position).user_UID, true);

                    } else if (!isChecked) {

                        chatRoom_model.users.remove(all_user_info.get(position));
                        chatRoom_model.now_login.remove(all_user_info.get(position));

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

        //검색 아이템
        void filter(String searchText) {
            search_user_info.clear();

            if(searchText.length() == 0) {
                search_user_info.addAll(all_user_info);
            }
            else {

                for(Member_ArrayList item : all_user_info) {
                    if(item.getUser_nick().contains(searchText)) {

                        search_user_info.add(item);

                    }
                }
            }

            notifyDataSetChanged();

        }


    }



}
