package com.example.dudu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

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

        final int position = intent1.getIntExtra("position", -1);

        if (!(position == -1)) {

            if(App.home_02_02_ArrayList.get(position).book.equals("null")){
                home_02_02_book_image.setImageResource(R.drawable.home_02_default);
            }else{  //비트맵일 경우
                Bitmap bitmap_pic = App.getBitmap(App.home_02_02_ArrayList.get(position).book);
                home_02_02_book_image.setImageBitmap(bitmap_pic);
            }

            home_02_02_book_name.setText(App.home_02_02_ArrayList.get(position).getName());
            home_02_02_book_author.setText(App.home_02_02_ArrayList.get(position).getAuthor());
            home_02_02_book_date.setText(App.home_02_02_ArrayList.get(position).getFinish());
            home_02_02_book_main.setText(App.home_02_02_ArrayList.get(position).getMain());

        } else {

            MainActivity.showToast(Home_02_02.this, "빈 페이지입니다.");
            finish();

        }

        //삭제하기
        home_02_02_remove_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_02.this);
                alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                App.home_02_02_ArrayList.remove(position);

                                App.mybrary_sort();

                                //쉐어드 생성
                                SharedPreferences saveMember_info = getSharedPreferences("mybrary", MODE_PRIVATE);
                                SharedPreferences.Editor save = saveMember_info.edit();

                                //해쉬맵 생성
                                HashMap<String, Home_02_02_ArrayList> mybrary_map = new HashMap<>();

                                mybrary_map.clear();

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

                                MainActivity.showToast(Home_02_02.this, "삭제 되었습니다.");

                                //정렬
                                App.mybrary_sort();

                                onBackPressed();

                                return;
                            }
                        });

                android.app.AlertDialog alert = alert_confirm.create();

                alert.show();

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

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(Home_02_02.this, Home_02.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }


}

