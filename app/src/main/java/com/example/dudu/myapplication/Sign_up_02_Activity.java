package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sign_up_02_Activity extends AppCompatActivity {

    //회원정보 받는 뷰
    EditText user_id;
    EditText user_password_01;
    EditText user_password_02;
    EditText user_name;
    EditText user_birth_day;

    //회원 가입 부가적 필요
    boolean check;  //아이디 체크 불린
    String user_sex;
    Button user_id_check;

    ImageButton sign_up_02_back_IB;
    Button sign_up_02_sign_in_01_B;
    CheckBox user_sign_up_check;
    RadioButton user_male;
    RadioButton user_female;

    ArrayList<Member_ArrayList> all_user_info = new ArrayList<>();

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedinstanesState) {

        super.onCreate(savedinstanesState);
        setContentView(R.layout.sign_up_02);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        //Find
        user_id = findViewById(R.id.user_id_in);
        user_password_01 = findViewById(R.id.user_password_in_01);
        user_password_02 = findViewById(R.id.user_password_in_02);
        user_name = findViewById(R.id.user_name_in);
        user_birth_day = findViewById(R.id.user_date_in);
        user_male = findViewById(R.id.user_male_radio);
        user_female = findViewById(R.id.user_female_radio);
        user_id_check = findViewById(R.id.user_id_check);

        FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    all_user_info.add(item.getValue(Member_ArrayList.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        user_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //키보드 내리기
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


                for (int i = 0; i < all_user_info.size(); i++) {

                    if (!user_id.getText().toString().equals(all_user_info.get(i).member_id) && user_id.getText().length() > 9) {

                        check = true;
                        MainActivity.showToast(Sign_up_02_Activity.this, "사용 가능한 아이디입니다.");

                    } else {

                        check = false;
                        MainActivity.showToast(Sign_up_02_Activity.this, "사용 불가능한 아이디입니다.");
                    }

                }

            }
        });

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

                if (user_id.getText().toString().length() <= 0 && Patterns.EMAIL_ADDRESS.matcher(user_id.getText().toString()).matches()) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "아이디를 입력해주세요.");

                } else if (!check) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "아이디 중복 체크를 해주세요.");

                } else if (user_password_01.getText().toString().length() <= 0 && App.PASSWORD_PATTERN.matcher(user_password_01.getText().toString()).matches()) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (user_password_02.getText().toString().length() <= 0) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (!(user_password_01.getText().toString().equals(user_password_02.getText().toString()))) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "패스워드를 확인해주세요.");

                } else if (!(user_birth_day.getText().toString().length() == 8)) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "생년월일 8자리를 입력해주세요.");

                } else if (!(user_male.isChecked()) && !(user_female.isChecked())) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "성별을 체크해주세요.");

                } else if (!(user_sign_up_check.isChecked())) {

                    MainActivity.showToast(Sign_up_02_Activity.this, "약관에 동의 해주세요.");

                } else {

                    if (user_male.isChecked()) {
                        user_sex = "남자";
                    } else {
                        user_sex = "여자";
                    }

//                    //쉐어드 생성
//                    SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);
//                    SharedPreferences.Editor save = saveMember_info.edit();
//
//                    //해쉬맵 생성
//                    HashMap<String, Member_ArrayList> member_map = new HashMap<>();
//
//                    //정보 삽입
//                    Member_ArrayList user_info = new Member_ArrayList(user_id.getText().toString(), user_password_01.getText().toString(),
//                            user_name.getText().toString(), user_birth_day.getText().toString());
//
//                    //정보 -> 해쉬맵에 삽입
//                    member_map.put(user_info.getMember_id(), user_info);
//
//                    //해쉬맵(Gson 변환) -> 쉐어드 삽입
//                    save.putString(user_info.getMember_id(), App.gson.toJson(member_map));
//
//                    //저장
//                    save.apply();

                    //파이어베이스 계정 생성
                    createUser(user_id.getText().toString(), user_password_01.getText().toString());

                }
            }

        });

//        //아이디 중복 체크
//        findViewById(R.id.user_id_check).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //쉐어드 생성
//                SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);
//
//                //쉐어드 안에 있는 정보 가져오기
//                String id_check = saveMember_info.getString(user_id.getText().toString(), "");
//
//                if(id_check.equals("")){
//
//                    id_check_boolean = true;
//
//                    MainActivity.showToast(Sign_up_02_Activity.this, "사용 가능한 아이디 입니다.");
//
//                }else{
//
//                    id_check_boolean = false;
//
//                    MainActivity.showToast(Sign_up_02_Activity.this, "사용 불가능한 아이디 입니다.");
//
//                }
//
//            }
//        });

        //약관 동의
        user_sign_up_check = findViewById(R.id.user_sign_up_check);
        user_sign_up_check.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_sign_up_check.isChecked()) {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vp.co.kr/home/agreement_03.html"));
                    startActivity(intent1);
                    //
                } else {

                }


            }
        });


    }

    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //파이어베이스 데이터베이스 선언
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("User_Info");

                            //정보 삽입
                            Member_ArrayList user_info = new Member_ArrayList(user_id.getText().toString(), user_password_01.getText().toString(),
                                    user_name.getText().toString(), user_birth_day.getText().toString(), user_sex, App.user_UID_get());

                            //파이어베이스에 저장
                            myRef.child(App.user_UID_get()).setValue(user_info);

                            //화면 이동
                            MainActivity.showToast(Sign_up_02_Activity.this, "회원 가입 완료");
                            Intent intent1 = new Intent(Sign_up_02_Activity.this, MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent1);

                        } else {
                            // 회원가입 실패
                            Toast.makeText(Sign_up_02_Activity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                    }
                });
    }

}


