package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Search_02 extends AppCompatActivity {

    Boolean book_check = false;

    Context context;

    //북 세팅
    ImageView search_02_book;
    TextView search_02_name;
    TextView search_02_author;
    TextView search_02_publisher;
    TextView search_02_date;
    TextView search_02_price;

    protected void onCreate(Bundle savedInstancState) {
        super.onCreate(savedInstancState);
        setContentView(R.layout.search_02);

        //북 세팅
        search_02_book = findViewById(R.id.search_02_book);
        search_02_name = findViewById(R.id.search_02_name);
        search_02_author = findViewById(R.id.search_02_author);
        search_02_publisher = findViewById(R.id.search_02_publisher);
        search_02_date = findViewById(R.id.search_02_date);
        search_02_price = findViewById(R.id.search_02_price);

        Intent intent1 = getIntent();

        final int position = intent1.getIntExtra("position",-1);

        search_02_name = findViewById(R.id.search_02_name);

        System.out.println(position);

        if(!(position == -1)) {
            search_02_book.setImageResource(App.search_book_ArrayList.get(position).getDrawableId());
            search_02_name.setText(App.search_book_ArrayList.get(position).getName());
            search_02_author.setText(App.search_book_ArrayList.get(position).getAuthor());
            search_02_publisher = findViewById(R.id.search_02_publisher);
            search_02_date = findViewById(R.id.search_02_date);
            search_02_price.setText(App.search_book_ArrayList.get(position).getPrice());
        }

        findViewById(R.id.search_02_heart_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < App.heart_book_ArrayList.size(); i++) {

                    String name = App.heart_book_ArrayList.get(i).heart_name;

                    if (!(name.equals(search_02_name.getText().toString()))) {
                        book_check = true;

                        Log.d("체크", "체크 true");

                    } else {
                        book_check = false;

                        Log.d("체크", "체크 false");

                        break;
                    }

                    Log.d("체크", "break");

                }

                if (book_check || App.heart_book_ArrayList.size() == 0) {

                    //키보드 내리기
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d("체크", "찜");

                    App.heart_book_ArrayList.add(new Home_05_ArrayList(App.search_book_ArrayList.get(position).drawableId, App.search_book_ArrayList.get(position).name, App.search_book_ArrayList.get(position).author, App.search_book_ArrayList.get(position).price, (float) App.search_book_ArrayList.get(position).star, R.drawable.home_05_heart_02));

//                    ------------------------------------------------쉐어드---------------------------------------

                    //쉐어드 생성
                    SharedPreferences saveMember_info = getSharedPreferences("Heart", MODE_PRIVATE);
                    SharedPreferences.Editor save = saveMember_info.edit();

                    //해쉬맵 생성
                    HashMap<String, Home_05_ArrayList> heart_map = new HashMap<>();

                    //정보 -> 해쉬맵에 삽입
                    for(int i = 0; i < App.heart_book_ArrayList.size(); i++){

                        heart_map.put(App.User_ID + "_Heart_" + i , App.heart_book_ArrayList.get(i));

                    }

                    save.clear();

                    //해쉬맵(Gson 변환) -> 쉐어드 삽입
                    save.putString(App.User_ID + "_Heart", App.gson.toJson(heart_map));

                    //저장
                    save.apply();

                    Toast.makeText(Search_02.this, search_02_name.getText().toString() + "가 찜목록에 추가 되었습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("체크", "체크 false 02");
                    //키보드 내리기
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(Search_02.this, search_02_name.getText().toString() + "가 이미 찜목록에 있습니다.", Toast.LENGTH_SHORT).show();

                    book_check = true;

                }

            }
        });




        findViewById(R.id.search_02_Go_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://book.naver.com/search/search.nhn?sm=sta_hty.book&sug=&where=nexearch&query=" + App.search_book_ArrayList.get(position).name));
//                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://book.naver.com/search/search.nhn?sm=sta_hty.book&sug=&where=nexearch&query=" + App.search_book_ArrayList.get(position).name));
                startActivity(intent1);
            }
        });



        //뒤로 가기
        findViewById(R.id.search_02_back_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


    }



}