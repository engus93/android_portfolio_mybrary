package com.example.dudu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

            home_02_03_book_image.setImageURI(Home_02_01.home_02_02_ArrayList.get(position).getBook());
            home_02_03_book_name.setText(Home_02_01.home_02_02_ArrayList.get(position).getName());
            home_02_03_book_author.setText(Home_02_01.home_02_02_ArrayList.get(position).getAuthor());
            home_02_03_book_date.setText(Home_02_01.home_02_02_ArrayList.get(position).getFinish());
            home_02_03_book_main.setText(Home_02_01.home_02_02_ArrayList.get(position).getMain());

        }else{

            MainActivity.showToast(Home_02_03.this, "빈 페이지입니다.");
            finish();

        }

        home_02_03_set_B.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
                Home_02_01.home_02_02_ArrayList.set(position,new Home_02_02_ArrayList(Home_02_01.home_02_02_ArrayList.get(position).getBook(), home_02_03_book_name.getText().toString(), home_02_03_book_author.getText().toString(), home_02_03_book_date.getText().toString(), home_02_03_book_main.getText().toString()));
                MainActivity.showToast(Home_02_03.this, "수정 되었습니다.");

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

                        finish();

                        return;
                    }
                });

        android.app.AlertDialog alert = alert_confirm.create();

        alert.show();

    }




}