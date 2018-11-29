package com.example.dudu.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Home_00_my_info_03 extends AppCompatActivity {

    Button bt_01;
    ImageButton my_info_03_talk_B;
    EditText my_info_talk;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info_03);

        my_info_talk = (EditText) findViewById(R.id.my_info_03_talk_ET);

        //닉네임 수정 -> 대화명 수정 완료
        bt_01 = findViewById(R.id.my_info_03_talk_B);
        bt_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


//                //쉐어드 생성
//                SharedPreferences savenick_info = getSharedPreferences("member_info_03", MODE_PRIVATE);
//                SharedPreferences.Editor save = savenick_info.edit();
//
//                //해쉬맵 생성
//                HashMap<String, String> user_map = new HashMap<>();
//
//                //정보 삽입
//                String user_talk = my_info_talk.getText().toString();
//
//                //정보 -> 해쉬맵에 삽입
//                user_map.put(App.User_ID + "_user_talk", user_talk);
//
//                //해쉬맵(Gson 변환) -> 쉐어드 삽입
//                save.putString(App.User_ID + "_user_talk", App.gson.toJson(user_map));
//
//                //저장
//                save.apply();

                //파이어베이스 데이터베이스 선언
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User_Info");

                //해당 UID 캐치
                FirebaseUser user;
                user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                //정보 삽입
                String user_talk = my_info_talk.getText().toString();

                //파이어베이스에 저장
                myRef.child(uid).child("user_info").child("user_talk").setValue(user_talk);

                Intent intent1 = new Intent(Home_00_my_info_03.this, Home_00_my_info.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //뒤로가기
        my_info_03_talk_B = findViewById(R.id.my_info_03_back_B);
        my_info_03_talk_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(Home_00_my_info_03.this, Home_00_my_info.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }
}
