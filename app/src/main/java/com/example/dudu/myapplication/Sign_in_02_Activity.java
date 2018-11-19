package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Sign_in_02_Activity extends AppCompatActivity {

    Button sign_in_02_home; //로그인 ->  버튼
    ImageButton sign_in_02_back;    // 뒤로가기 버튼

    protected void onCreate (Bundle savedInstancesState){

        super.onCreate(savedInstancesState);
        setContentView(R.layout.sign_in_02);

        //뒤로가기 버튼
        sign_in_02_back = findViewById(R.id.search_back_B);
        sign_in_02_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                finish();
            }

        });

        //로그인 -> 메인화면 버튼
        sign_in_02_home = findViewById(R.id.sign_in_02_home_B);
        sign_in_02_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //키보드 내리기
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //잠시대기
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent1 = new Intent(Sign_in_02_Activity.this, Home_01.class);
                MainActivity.showToast(Sign_in_02_Activity.this, "로그인");
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            }

        });



    }


}
