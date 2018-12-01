package com.example.dudu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_03_View extends AppCompatActivity {

    String key;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    //보기
    ImageView home_02_02_book_image;
    TextView home_02_02_book_name;
    TextView home_02_02_book_author;
    TextView home_02_02_book_date;
    TextView home_02_02_book_main;
    ImageButton home_02_02_back_B;
    Button home_02_02_remove_B;

    boolean remove_like;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02_02);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(this);

        home_02_02_book_image = findViewById(R.id.home_02_02_book_image);
        home_02_02_book_name = findViewById(R.id.home_02_02_book_name);
        home_02_02_book_author = findViewById(R.id.home_02_02_author);
        home_02_02_book_date = findViewById(R.id.home_02_02_date);
        home_02_02_book_main = findViewById(R.id.home_02_02_main);
        home_02_02_remove_B = findViewById(R.id.home_02_02_remove_B);

        Intent intent1 = getIntent();

        final int position = intent1.getIntExtra("position", -1);

        if (!(position == -1)) {

            if(App.home_03_ArrayList.get(position).book.equals("null")){
                home_02_02_book_image.setImageResource(R.drawable.home_02_default);
            }else{  //비트맵일 경우
                mGlideRequestManager.load(App.home_03_ArrayList.get(position).book).into(home_02_02_book_image);

            }

            home_02_02_book_name.setText(App.home_03_ArrayList.get(position).getName());
            home_02_02_book_author.setText(App.home_03_ArrayList.get(position).getAuthor());
            home_02_02_book_date.setText(App.home_03_ArrayList.get(position).getFinish());
            home_02_02_book_main.setText(App.home_03_ArrayList.get(position).getMain());

        } else {

            MainActivity.showToast(Home_03_View.this, "빈 페이지입니다.");
            finish();

        }

        if (!(App.home_03_ArrayList.get(position).getUser_uid().equals(App.user_UID_get()))) {

            home_02_02_remove_B.setText("좋아요");

            remove_like = false;

        }else{

            remove_like = true;

        }

        //삭제하기
        home_02_02_remove_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (remove_like) {
                    android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_03_View.this);
                    alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    }).setNegativeButton("네",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    key = App.home_03_ArrayList.get(position).getUser_key();

                                    //파이어베이스 데이터베이스 선언
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef = database.getReference("Users_MyBrary");

                                    //리싸이클러뷰 파이어베이스 업데이트
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                                Home_02_02_ArrayList home_02_02_arrayList_00 = new Home_02_02_ArrayList();
                                                home_02_02_arrayList_00 = snapshot.getValue(Home_02_02_ArrayList.class);

                                                Log.d("체크", "삭제 전");

                                                if (home_02_02_arrayList_00.getUser_key().equals(key)) {

                                                    Log.d("체크", "삭제 진입");

                                                    myRef.child(home_02_02_arrayList_00.getUser_key()).removeValue();

                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    onBackPressed();

                                    return;
                                }
                            });

                    android.app.AlertDialog alert = alert_confirm.create();

                    alert.show();

                }else {

                    MainActivity.showToast(Home_03_View.this, "좋아요! 꾸욱!");

                }
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

        Intent intent1 = new Intent(Home_03_View.this, Home_03.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }


}

