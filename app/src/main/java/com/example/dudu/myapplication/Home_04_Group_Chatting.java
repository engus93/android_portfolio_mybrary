package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Home_04_Group_Chatting extends AppCompatActivity {
    Map<String, Member_ArrayList> all_user_info = new HashMap<>();
    String chat_room_key;

    //채팅방 아이템
    ImageView home_04_chatting_joinlist; //채팅방 메뉴
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    TextView home_04_chatting_nick; //유저 닉네임 (방제)
    EditText home_04_chatting_ET;   //텍스트 적기
    ImageView home_04_chatting_send;    //보내기
    ImageView home_04_chatting_camera;   //카메라

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_04_chatting);

        //파인드 뷰
        home_04_chatting_joinlist = findViewById(R.id.home_04_chatting_people_list);
        home_04_friendlist_back_B = findViewById(R.id.home_04_chatting_back_B);
        home_04_chatting_nick = findViewById(R.id.home_04_chatting_nick);
        home_04_chatting_ET = findViewById(R.id.home_04_chatting_ET);
        home_04_chatting_send = findViewById(R.id.home_04_chatting_send);
        home_04_chatting_camera = findViewById(R.id.home_04_chatting_camera);

        chat_room_key = getIntent().getStringExtra("chat_room_key");

        FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                all_user_info = (Map<String, Member_ArrayList>) dataSnapshot.getValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //보내는 버튼
        home_04_chatting_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Home_04_ChatRoom_Model.Message message = new Home_04_ChatRoom_Model.Message();
                        message.wright_user = App.user_UID_get();
                        message.contents = home_04_chatting_ET.getText().toString();
                        message.time = ServerValue.TIMESTAMP;

                        FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chat_room_key).child("message").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //텍스트 창 초기화
                                home_04_chatting_ET.setText(null);

                            }
                        });


            }
        });

    }
}
