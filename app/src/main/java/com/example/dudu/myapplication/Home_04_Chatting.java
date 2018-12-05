package com.example.dudu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mozilla.javascript.tools.jsc.Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Home_04_Chatting extends AppCompatActivity {

    ImageView home_04_chatting_joinlist; //채팅방 메뉴
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    TextView home_04_chatting_nick; //유저 닉네임 (방제)
    EditText home_04_chatting_ET;   //텍스트 적기
    ImageView home_04_chatting_send;    //보내기
    ImageView home_04_chatting_camera;   //카메라

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_chatting);

        home_04_chatting_joinlist = findViewById(R.id.home_04_chatting_people_list);
        home_04_friendlist_back_B = findViewById(R.id.home_04_chatting_back_B);
        home_04_chatting_nick = findViewById(R.id.home_04_chatting_nick);
        home_04_chatting_ET = findViewById(R.id.home_04_chatting_ET);
        home_04_chatting_send = findViewById(R.id.home_04_chatting_send);
        home_04_chatting_camera = findViewById(R.id.home_04_chatting_camera);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(this);

        Intent intent1 = getIntent();

        int position = intent1.getIntExtra("position", -1);

        //상대방 UID 추출
        if (!(App.now_chat_user.user_1.equals(App.user_UID_get()))) {
            App.opponent.opponent_uid = App.now_chat_user.user_1;
        } else {
            App.opponent.opponent_uid = App.now_chat_user.user_2;
        }

        //유저 정보
        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                App.opponent.opponent_nick = (String) dataSnapshot.child(App.opponent.opponent_uid).child("user_nick").getValue();
                App.opponent.opponent_profile = (String) dataSnapshot.child(App.opponent.opponent_uid).child("user_profile").getValue();

                home_04_chatting_nick.setText(App.opponent.opponent_nick);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //---------------------------리싸이클러뷰---------------------------------
        final RecyclerView mRecyclerView;
        final RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_04_chatting_re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //채팅 내용
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Message").child("User_Chat").child(App.now_chat_user.room_key);
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                App.now_chat_Contents.clear();
                Map<String, Object> read = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    Home_04_ChattingList contents;
                    contents = snapshot.getValue(Home_04_ChattingList.class);
                    contents.read.put(App.user_UID_get(), true);

                    read.put(key,contents);
                    App.now_chat_Contents.add(contents);
                }

                FirebaseDatabase.getInstance().getReference().child("User_Message").child("User_Chat").child(App.now_chat_user.room_key).updateChildren(read).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Home_04_Chatting_Adapter myAdapter = new Home_04_Chatting_Adapter(getApplicationContext(), App.now_chat_Contents);

                        mRecyclerView.setAdapter(myAdapter);

                        mRecyclerView.scrollToPosition(App.now_chat_Contents.size() - 1);

                        System.out.println(App.now_chat_Contents.size());

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //모든 유저 리스트 불러오기
        FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Home_04_Single_Chatting single_chatting = new Home_04_Single_Chatting();

                App.user_chat_room.clear();

                //모든 유저 리스트 불러오기
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    single_chatting = snapshot.getValue(Home_04_Single_Chatting.class);

                    App.user_chat_room.add(single_chatting);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        home_04_chatting_joinlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.showToast(Home_04_Chatting.this, "메뉴 창!");

            }
        });

        home_04_friendlist_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        home_04_chatting_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.showToast(Home_04_Chatting.this, "사진!");

            }
        });

        home_04_chatting_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (home_04_chatting_ET.getText().length() > 0) {

                    //서재 등록 날짜 세팅
                    Date date = new Date();
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분", java.util.Locale.getDefault());
                    String set_date = dateFormat.format(date);

                    Home_04_ChattingList chat_contents = new Home_04_ChattingList(set_date, App.user_UID_get(), home_04_chatting_ET.getText().toString());
                    FirebaseDatabase.getInstance().getReference("User_Message").child("User_Chat").child(App.now_chat_user.room_key).push().setValue(chat_contents);
                    home_04_chatting_ET.setText(null);

                }

            }
        });

    }



    @Override
    public void onBackPressed() {

        databaseReference.removeEventListener(valueEventListener);

        finish();

        Intent intent1 = new Intent(this, Home_04.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }
}
