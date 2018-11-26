package com.example.dudu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.HashMap;

public class Home_02_03 extends AppCompatActivity {

    //수정하기

    TextView home_02_03_title;
    ImageView home_02_03_book_image;
    TextView home_02_03_book_name;
    TextView home_02_03_book_author;
    TextView home_02_03_book_date;
    TextView home_02_03_book_main;
    Button home_02_03_set_B;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02_01);

        home_02_03_title = findViewById(R.id.home_02_01_title);
        home_02_03_book_image = findViewById(R.id.home_02_01_book_image);
        home_02_03_book_name = findViewById(R.id.home_02_01_book_name);
        home_02_03_book_author = findViewById(R.id.home_02_01_author);
        home_02_03_book_date = findViewById(R.id.home_02_01_date);
        home_02_03_book_main = findViewById(R.id.home_02_01_main);
        home_02_03_set_B = findViewById(R.id.home_02_01_plus_B);

        home_02_03_set_B.setText("수정하기");
        home_02_03_title.setText("서재 수정하기");

        Intent intent1 = getIntent();

        final int position = intent1.getIntExtra("position",-1);

        if(!(position == -1)){

            home_02_03_book_image.setImageURI(Uri.parse(App.home_02_02_ArrayList.get(position).getBook()));
            home_02_03_book_name.setText(App.home_02_02_ArrayList.get(position).getName());
            home_02_03_book_author.setText(App.home_02_02_ArrayList.get(position).getAuthor());
            home_02_03_book_date.setText(App.home_02_02_ArrayList.get(position).getFinish());
            home_02_03_book_main.setText(App.home_02_02_ArrayList.get(position).getMain());

        }else{

            MainActivity.showToast(Home_02_03.this, "빈 페이지입니다.");
            finish();

        }

        home_02_03_set_B.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                App.home_02_02_ArrayList.set(position,new Home_02_02_ArrayList(App.home_02_02_ArrayList.get(position).getBook(), home_02_03_book_name.getText().toString(), home_02_03_book_author.getText().toString(), home_02_03_book_date.getText().toString(), home_02_03_book_main.getText().toString()));

                App.mybrary_sort();

                //쉐어드 생성
                SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);
                SharedPreferences.Editor save = saveMember_info.edit();

                //해쉬맵 생성
                HashMap<String, Home_02_02_ArrayList> mybrary_map = new HashMap<>();

                //정보 -> 해쉬맵에 삽입
                for(int i = 0; i < App.home_02_02_ArrayList.size(); i++){

                    mybrary_map.put(App.User_ID + "_MyBrary_" + i , App.home_02_02_ArrayList.get(i));

                }

                //쉐어드 초기화
                save.clear();

                //해쉬맵(Gson 변환) -> 쉐어드 삽입
                save.putString(App.User_ID + "_MyBrary", App.gson.toJson(mybrary_map));

                //저장
                save.apply();

                MainActivity.showToast(Home_02_03.this, "수정 되었습니다.");

                //종료
                Intent intent1 = new Intent(Home_02_03.this, Home_02.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //뒤로가기
        findViewById(R.id.home_02_01_back_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


    }

    //수정 종료 방지
    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_03.this);
        alert_confirm.setMessage("수정을 종료 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }

        }).setNegativeButton("네",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent1 = new Intent(Home_02_03.this, Home_02.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);

                        return;
                    }
                });

        android.app.AlertDialog alert = alert_confirm.create();

        alert.show();

    }




}