package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button sign_in_01_sign_up_01_button;    //로그인 버튼
    Button sign_in_01_sign_in_02_button;    //회원 가입 버튼
    int back = 0;   //뒤로가기 버튼 카운트
    private long backPressedTime = 0;   //뒤로가기 2번 구현
    static Toast toast; //토스트 메세지 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showToast(this, "처음이라면 회원 가입을 해주세요. :)");

        setContentView(R.layout.sign_in_01);

        //초기화면 -> 회원가입 버튼
        sign_in_01_sign_up_01_button = findViewById(R.id.sign_in_01_sign_up_01_B);
        sign_in_01_sign_up_01_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Sign_up_01_Activity.class);
                startActivity(intent1);

            }
        });

        //초기화면 -> 로그인 버튼
        sign_in_01_sign_in_02_button = findViewById(R.id.sign_in_01_sign_in_02_B);
        sign_in_01_sign_in_02_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Sign_in_02_Activity.class);

                startActivity(intent1);

            }

        });

    }


    //뒤로가기 버튼 카운트 초기화
    @Override
    protected void onResume(){
        super.onResume();
        back = 0;
    }

    //뒤로가기 버튼 막기 (2번 누르면 종료)
    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && (long) 2000 >= intervalTime) {
            toast.cancel();
            finishAffinity();
        } else {
            backPressedTime = tempTime;
            showToast(this, "한번 더 누르시면 종료가 됩니다.");
        }

    }


    //토스트 메세지 중복 방지
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.show();
    }






}
