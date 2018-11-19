package com.example.dudu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Sign_up_01_Activity extends AppCompatActivity {

    ImageButton sign_up_01_back;
    Button sign_up_01_sign_up_02_B;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.sign_up_01);

        //뒤로가기
        sign_up_01_back = findViewById(R.id.search_back_B);
        sign_up_01_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }

        });

        //회원가입 1 -> 회원가입 2 버튼
        sign_up_01_sign_up_02_B = findViewById(R.id.sign_up_01_sign_up_02_B);
        sign_up_01_sign_up_02_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Sign_up_01_Activity.this, Sign_up_02_Activity.class);
                startActivity(intent1);
            }
        });
    }

    //회원 가입 도중 뒤로 오면 가입 취소 문구
    @Override
    protected void onRestart() {
        super.onRestart();
        MainActivity.showToast(Sign_up_01_Activity.this, "회원 가입 취소");
    }

}
