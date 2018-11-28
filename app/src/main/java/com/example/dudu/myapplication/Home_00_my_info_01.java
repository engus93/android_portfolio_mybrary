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
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Home_00_my_info_01 extends AppCompatActivity {

    Button bt_01;
    ImageButton my_info_01_back_B;
    EditText my_info_nick;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info_01);

        my_info_nick = findViewById(R.id.my_info_01_nick_ET);

        //닉네임 수정 -> 닉네임 수정 완료
        bt_01 = findViewById(R.id.my_info_01_nick_B);
        bt_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //쉐어드 생성
                SharedPreferences savenick_info = getSharedPreferences("member_info_01", MODE_PRIVATE);
                SharedPreferences.Editor save = savenick_info.edit();

                //해쉬맵 생성
                HashMap<String, String> nick_map = new HashMap<>();

                //정보 삽입
                String user_nick = my_info_nick.getText().toString();

                //정보 -> 해쉬맵에 삽입
                nick_map.put(App.User_ID + "_user_nick", user_nick);

                //해쉬맵(Gson 변환) -> 쉐어드 삽입
                save.putString(App.User_ID + "_user_nick", App.gson.toJson(nick_map));

                //저장
                save.apply();

                Intent intent1 = new Intent(Home_00_my_info_01.this, Home_00_my_info.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //뒤로가기
        my_info_01_back_B = findViewById(R.id.my_info_01_back_B);
        my_info_01_back_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(Home_00_my_info_01.this, Home_00_my_info.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }




}
