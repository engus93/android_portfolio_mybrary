package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class Sign_in_02_Activity extends AppCompatActivity{

    Button sign_in_02_home; //로그인 ->  버튼
    ImageButton sign_in_02_back;    // 뒤로가기 버튼
    EditText sign_in_02_id; //아이디
    EditText sign_in_02_password;   //패스워드

    protected void onCreate (Bundle savedInstancesState){

        super.onCreate(savedInstancesState);
        setContentView(R.layout.sign_in_02);

        //로그인 입력 값
        sign_in_02_id = findViewById(R.id.sign_in_02_id);
        sign_in_02_password = findViewById(R.id.sign_in_02_password);

        //뒤로가기 버튼
        sign_in_02_back = findViewById(R.id.search_back_B);
        sign_in_02_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                finish();
            }

        });

        //구글 로그인 버튼
        findViewById(R.id.sign_in_02_google_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //로그인 넣기


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

                if (sign_in_02_id.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_in_02_Activity.this, "아이디를 입력해주세요");

                } else if (sign_in_02_password.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_in_02_Activity.this, "비밀번호를 입력해주세요");

                } else {

                    //쉐어드 생성
                    SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);

                    //쉐어드 안에 있는 정보 가져오기
                    String check = saveMember_info.getString(sign_in_02_id.getText().toString(), "");

                    if (!(check.equals(""))) {

                        Log.d("체크", "아이디 일치");

                        //해쉬맵 생성
                        HashMap<String, Member_ArrayList> member_map = new HashMap<>();

                        //해쉬맵에 삽입
                        member_map = App.gson.fromJson(check, App.collectionTypeMember);

                        //일치
                        if (member_map.get(sign_in_02_id.getText().toString()).member_password_01.equals(sign_in_02_password.getText().toString())) {

                            Log.d("체크", "비번 일치");

                            App.User_ID = sign_in_02_id.getText().toString();

                            Intent intent1 = new Intent(Sign_in_02_Activity.this, Home_01.class);
                            MainActivity.showToast(Sign_in_02_Activity.this, "로그인");
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);

                        } else {    //비번 불일치

                            Log.d("체크", "비번 불일치");

                            MainActivity.showToast(Sign_in_02_Activity.this, "패스워드가 일치하지 않습니다.");

                        }

                    } else { //아이디 불일치

                        Log.d("체크", "아이디 불일치");

                        MainActivity.showToast(Sign_in_02_Activity.this, "아이디가 일치하지 않습니다.");

                    }

                }

            }

        });

    }



}
