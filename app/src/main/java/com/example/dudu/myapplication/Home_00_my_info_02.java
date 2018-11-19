package com.example.dudu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Home_00_my_info_02 extends AppCompatActivity {

    Button bt_01;
    ImageButton my_info_02_genre_B;
    EditText my_info_genre;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info_02);

        //닉네임 수정 -> 닉네임 수정 완료
        bt_01 = findViewById(R.id.my_info_02_genre_B);
        bt_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                my_info_genre = (EditText) findViewById(R.id.my_info_02_genre_ET);
                Intent intent1 = new Intent(Home_00_my_info_02.this, Home_00_my_info.class);
                intent1.putExtra("genre", my_info_genre.getText().toString());
                intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                finish();
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
}
