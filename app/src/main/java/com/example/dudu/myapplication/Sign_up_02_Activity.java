package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class Sign_up_02_Activity extends AppCompatActivity {

    //회원정보 받는 뷰
    EditText user_id;
    EditText user_password_01;
    EditText user_password_02;
    EditText user_e_mail;
    EditText user_name;
    EditText user_birth_day;

    //아이디 체크 불린
    boolean id_check_boolean;

    ImageButton sign_up_02_back_IB;
    Button sign_up_02_sign_in_01_B;
    CheckBox user_sign_up_check;

    protected void onCreate(Bundle savedinstanesState) {

        super.onCreate(savedinstanesState);
        setContentView(R.layout.sign_up_02);

        user_id = findViewById(R.id.user_id_in);
        user_password_01 = findViewById(R.id.user_password_in_01);
        user_password_02 = findViewById(R.id.user_password_in_02);
        user_e_mail = findViewById(R.id.user_e_mail_in);
        user_name = findViewById(R.id.user_name_in);
        user_birth_day = findViewById(R.id.user_date_in);

        //뒤로가기
        sign_up_02_back_IB = findViewById(R.id.search_back_B);
        sign_up_02_back_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //키보드 내리기
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //잠시대기
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (user_id.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "아이디를 입력해주세요.");

                } else if (!id_check_boolean) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "아이디 중복 체크를 해주세요.");

                } else if (user_password_01.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (user_password_02.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (!(user_password_01.getText().toString().equals(user_password_02.getText().toString()))) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (user_e_mail.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "이메일을 입력해주세요.");

                } else if (user_birth_day.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "생일을 입력해주세요.");

                } else {

                    //쉐어드 생성
                    SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);
                    SharedPreferences.Editor save = saveMember_info.edit();

                    //해쉬맵 생성
                    HashMap<String, Member_ArrayList> member_map = new HashMap<>();

                    //정보 삽입
                    Member_ArrayList user_info = new Member_ArrayList(user_id.getText().toString(), user_password_01.getText().toString(), user_e_mail.getText().toString(),
                            user_name.getText().toString(), user_birth_day.getText().toString());
                    //정보 -> 해쉬맵에 삽입
                    member_map.put(user_info.getMember_id(), user_info);

                    //해쉬맵(Gson 변환) -> 쉐어드 삽입
                    save.putString(user_info.getMember_id(), App.gson.toJson(member_map));

                    //저장
                    save.apply();

                    //화면 이동
                    MainActivity.showToast(Sign_up_02_Activity.this, "회원 가입 완료");
                    Intent intent1 = new Intent(Sign_up_02_Activity.this, MainActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent1);

                }
            }

        });

        //아이디 중복 체크
        findViewById(R.id.user_id_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //쉐어드 생성
                SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);

                //쉐어드 안에 있는 정보 가져오기
                String id_check = saveMember_info.getString(user_id.getText().toString(), "");

                if(id_check.equals("")){

                    id_check_boolean = true;

                    MainActivity.showToast(Sign_up_02_Activity.this, "사용 가능한 아이디 입니다.");

                }else{

                    id_check_boolean = false;

                    MainActivity.showToast(Sign_up_02_Activity.this, "사용 불가능한 아이디 입니다.");

                }

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
                    //
                }else{

                }


            }
        });


    }
}


