package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatBot_Search extends AppCompatActivity {

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    Boolean book_check = false;

    Context context;

    String key;

    //북 세팅
    ImageView search_02_book;
    TextView search_02_name;
    TextView search_02_author;
    TextView search_02_publisher;
    TextView search_02_date;
    TextView search_02_price;
    TextView search_02_summary;

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
        search_02_summary = findViewById(R.id.search_02_summary);
        search_02_publisher = findViewById(R.id.search_02_publisher);
        search_02_date = findViewById(R.id.search_02_date);
        search_02_name = findViewById(R.id.search_02_name);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(ChatBot_Search.this);

        if (App.chat_bot_search_book != null) {

            mGlideRequestManager.load(App.chat_bot_search_book.drawableId).fitCenter().into(search_02_book);
            search_02_name.setText(App.chat_bot_search_book.getName());
            search_02_author.setText(App.chat_bot_search_book.getAuthor());
            search_02_price.setText(App.chat_bot_search_book.getPrice());
            search_02_publisher.setText(App.chat_bot_search_book.book_publisher);
            search_02_date.setText("출간일 : " + App.chat_bot_search_book.date);
            search_02_summary.setText(App.chat_bot_search_book.book_main);

        }

        //찜목록 추가
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

                    Log.d("체크", "ㅋㅋ");

                    //키보드 내리기
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    //키보드 내리기
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    //파이어베이스 데이터베이스 선언
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users_Like_Book");

                    //랜덤 키 생성
                    key = myRef.push().getKey();

                    Home_05_ArrayList like_plus = new Home_05_ArrayList(App.chat_bot_search_book.drawableId, App.chat_bot_search_book.name,
                    App.chat_bot_search_book.author, App.chat_bot_search_book.price, (float) App.chat_bot_search_book.star,
                            R.drawable.home_05_heart_02, key, App.user_UID_get(), App.chat_bot_search_book.book_main, App.chat_bot_search_book.book_link);

                    //파이어베이스에 저장
                    myRef.child(App.user_UID_get()).child(key).setValue(like_plus);

                    Toast.makeText(ChatBot_Search.this, search_02_name.getText().toString() + "가 찜목록에 추가 되었습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("체크", "체크 false 02");
                    //키보드 내리기
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(ChatBot_Search.this, search_02_name.getText().toString() + "가 이미 찜목록에 있습니다.", Toast.LENGTH_SHORT).show();

                    book_check = true;

                }

            }
        });

        //MyBrary로 가져가서 쓰기
        findViewById(R.id.search_02_mybrary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChatBot_Search.this, Home_02_01.class);
                intent.putExtra("search_book_image", App.chat_bot_search_book.getDrawableId());
                intent.putExtra("search_book_name", App.chat_bot_search_book.getName());
                intent.putExtra("search_book_author", App.chat_bot_search_book.getAuthor());
                startActivity(intent);

            }
        });

        //찜목록 추가
        findViewById(R.id.search_02_Go_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(App.chat_bot_search_book.book_link));
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

        FirebaseDatabase.getInstance().getReference().child("Users_Like_Book").child(App.user_UID_get()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                App.heart_book_ArrayList.clear();

                for(DataSnapshot item : dataSnapshot.getChildren()){

                    App.heart_book_ArrayList.add(item.getValue(Home_05_ArrayList.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}