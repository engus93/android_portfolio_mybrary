package com.example.dudu.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Home_02_02 extends AppCompatActivity {

    //보기

    ImageView home_02_02_book_image;
    TextView home_02_02_book_name;
    TextView home_02_02_book_author;
    TextView home_02_02_book_date;
    TextView home_02_02_book_main;
    ImageButton home_02_02_back_B;
    Button home_02_02_remove_B;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02_02);

        home_02_02_book_image = findViewById(R.id.home_02_02_book_image);
        home_02_02_book_name = findViewById(R.id.home_02_02_book_name);
        home_02_02_book_author = findViewById(R.id.home_02_02_author);
        home_02_02_book_date = findViewById(R.id.home_02_02_date);
        home_02_02_book_main = findViewById(R.id.home_02_02_main);
        home_02_02_remove_B = findViewById(R.id.home_02_02_remove_B);

        Intent intent1 = getIntent();

        final int position = intent1.getIntExtra("position",-1);

        if(!(position == -1)){

            home_02_02_book_image.setImageURI(Uri.parse(Home_02_01.home_02_02_ArrayList.get(position).getBook()));
            home_02_02_book_name.setText(Home_02_01.home_02_02_ArrayList.get(position).getName());
            home_02_02_book_author.setText(Home_02_01.home_02_02_ArrayList.get(position).getAuthor());
            home_02_02_book_date.setText(Home_02_01.home_02_02_ArrayList.get(position).getFinish());
            home_02_02_book_main.setText(Home_02_01.home_02_02_ArrayList.get(position).getMain());

        }else{

            MainActivity.showToast(Home_02_02.this, "빈 페이지입니다.");
            finish();

        }


        home_02_02_remove_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Home_02_01.home_02_02_ArrayList.remove(position);

            }
        });


        //뒤로가기
        findViewById(R.id.home_02_02_back_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });




    }


}
