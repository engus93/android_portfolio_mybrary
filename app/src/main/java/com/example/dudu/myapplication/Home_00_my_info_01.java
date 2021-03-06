package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home_00_my_info_01 extends AppCompatActivity {

    Button bt_01;
    ImageButton my_info_01_back_B;
    EditText my_info_nick;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info_01);

        my_info_nick = findViewById(R.id.my_info_01_nick_ET);

        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                my_info_nick.setText((CharSequence) dataSnapshot.child(App.user_UID_get()).child("user_nick").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //닉네임 수정 -> 닉네임 수정 완료
        bt_01 = findViewById(R.id.my_info_01_nick_B);
        bt_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //파이어베이스 데이터베이스 선언
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User_Info");

                //해당 UID 캐치
                FirebaseUser user;
                user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                //정보 삽입
                String user_nick = my_info_nick.getText().toString();

                //파이어베이스에 저장
                myRef.child(App.user_UID_get()).child("user_nick").setValue(user_nick);

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
