package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class Sign_up_02_Activity extends AppCompatActivity {

    ImageButton sign_up_02_back_IB;
    Button sign_up_02_sign_in_01_B;
    CheckBox user_sign_up_check;

    protected void onCreate(Bundle savedinstanesState) {

        super.onCreate(savedinstanesState);
        setContentView(R.layout.sign_up_02);

        //뒤로가기
        sign_up_02_back_IB = findViewById(R.id.search_back_B);
        sign_up_02_back_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //가입하기 -> 초기화면
        sign_up_02_sign_in_01_B = findViewById(R.id.sign_up_02_sign_in_01_B);
        sign_up_02_sign_in_01_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 내리기
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                //잠시대기
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //화면 이동
                MainActivity.showToast(Sign_up_02_Activity.this, "회원 가입 완료");
                Intent intent1 = new Intent(Sign_up_02_Activity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }

        });

        //약관 동의
        user_sign_up_check = findViewById(R.id.user_sign_up_check);
        user_sign_up_check.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_sign_up_check.isChecked()){
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vp.co.kr/home/agreement_03.html"));
                    startActivity(intent1);
                }else{

                }


            }
        });


    }
}


