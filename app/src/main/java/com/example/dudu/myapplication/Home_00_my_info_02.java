package com.example.dudu.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class Home_00_my_info_02 extends AppCompatActivity {

    Button bt_01;
    ImageButton my_info_02_genre_B;
    EditText my_info_genre;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info_02);

        my_info_genre = (EditText) findViewById(R.id.my_info_02_genre_ET);

        //닉네임 수정 -> 닉네임 수정 완료
        bt_01 = findViewById(R.id.my_info_02_genre_B);
        bt_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                //쉐어드 생성
                SharedPreferences savenick_info = getSharedPreferences("member_info", MODE_PRIVATE);
                SharedPreferences.Editor save = savenick_info.edit();

                //해쉬맵 생성
                HashMap<String, String> user_map = new HashMap<>();

                //정보 삽입
                String user_like = my_info_genre.getText().toString();

                //정보 -> 해쉬맵에 삽입
                user_map.put(App.User_ID + "_user_like", user_like);

                //해쉬맵(Gson 변환) -> 쉐어드 삽입
                save.putString(App.User_ID + "_user_like", App.gson.toJson(user_map));

                //저장
                save.apply();

                Intent intent1 = new Intent(Home_00_my_info_02.this, Home_00_my_info.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
            }
        });

        //뒤로가기
        my_info_02_genre_B = findViewById(R.id.my_info_02_back_B);
        my_info_02_genre_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(Home_00_my_info_02.this, Home_00_my_info.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }
}
