//package com.example.dudu.myapplication;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestManager;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.MutableData;
//import com.google.firebase.database.Transaction;
//import com.google.firebase.database.ValueEventListener;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class Home_03_View extends AppCompatActivity {
//
//    String key;
//
//    //글라이드 오류 방지
//    public RequestManager mGlideRequestManager;
//
//    //보기
//    ImageView home_02_02_book_image;
//    TextView home_02_02_book_name;
//    TextView home_02_02_book_author;
//    TextView home_02_02_book_date;
//    TextView home_02_02_book_main;
//    ImageButton home_02_02_back_B;
//    Button home_02_02_remove_B;
//    CircleImageView home_03_wright_user_profile;
//    TextView home_03_wright_user_nick;
//    Button home_03_view_follow_B;
//    ImageView home_03_like_image;
//    TextView home_03_like_text;
//
//    String opponent_uid;
//    String my_uid;
//
//    Context context;
//
//    @Override
//    protected void onCreate(Bundle savedInstancesState) {
//        super.onCreate(savedInstancesState);
//        setContentView(R.layout.home_02_02);
//
//        //글라이드 오류 방지
//        mGlideRequestManager = Glide.with(this);
//
//        home_02_02_book_image = findViewById(R.id.home_02_02_book_image);
//        home_02_02_book_name = findViewById(R.id.home_02_02_book_name);
//        home_02_02_book_author = findViewById(R.id.home_02_02_author);
//        home_02_02_book_date = findViewById(R.id.home_02_02_date);
//        home_02_02_book_main = findViewById(R.id.home_02_02_main);
//        home_02_02_remove_B = findViewById(R.id.home_02_02_remove_B);
//        home_03_wright_user_profile = findViewById(R.id.wright_user_profile);
//        home_03_wright_user_nick = findViewById(R.id.wright_user_nick);
//        home_03_view_follow_B = findViewById(R.id.home_03_view_Follow);
//        home_03_like_image = findViewById(R.id.home_03_like_image);
//        home_03_like_text = findViewById(R.id.home_03_like_text);
//
//        Intent intent1 = getIntent();
//
//        final int position_10 = intent1.getIntExtra("position", -1);
//        String mybrary_key = intent1.getStringExtra("mybrary_key");
//
//        my_uid = App.user_UID_get();
//
//        if (!(position_10 == -1)) {
//
//            if(App.home_03_ArrayList.get(position_10).book.equals("null")){
//                home_02_02_book_image.setImageResource(R.drawable.home_02_default);
//            }else{  //비트맵일 경우
//                mGlideRequestManager.load(App.home_03_ArrayList.get(position_10).book).into(home_02_02_book_image);
//            }
//
//            home_02_02_book_name.setText(App.home_03_ArrayList.get(position_10).getName());
//            home_02_02_book_author.setText(App.home_03_ArrayList.get(position_10).getAuthor());
//            home_02_02_book_date.setText(App.home_03_ArrayList.get(position_10).getFinish());
//            home_02_02_book_main.setText(App.home_03_ArrayList.get(position_10).getMain());
//
//        } else {
//
//            MainActivity.showToast(Home_03_View.this, "빈 페이지입니다.");
//            finish();
//
//        }
//
//        //나의 게시물인지 확인 (내가 아니다)
//        if (!(App.home_03_ArrayList.get(position_10).getUser_uid().equals(App.user_UID_get()))) {
//            home_03_view_follow_B.setVisibility(View.VISIBLE);
//            home_02_02_remove_B.setVisibility(View.INVISIBLE);
//            home_03_like_text.setVisibility(View.VISIBLE);
//
//        }else{
//            //나다
//            home_03_view_follow_B.setVisibility(View.GONE);
//            home_02_02_remove_B.setVisibility(View.VISIBLE);
////            home_03_like_text.setVisibility(View.GONE);
//
//        }
//
//        if (App.home_03_ArrayList.get(position_10).user_mybrary_like.containsKey(App.user_UID_get())) {
//            home_03_like_image.setSelected(true);
//        }else{
//            home_03_like_image.setSelected(false);
//        }
//
//        //작성자 UID 가져오기
//        opponent_uid = App.home_03_ArrayList.get(position_10).getUser_uid();
//
//        //리싸이클러뷰 글쓴이 정보 파이어베이스에서 가져오기
//        FirebaseDatabase.getInstance().getReference("User_Info").child(App.home_03_ArrayList.get(position_10).getUser_uid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Member_ArrayList temp = new Member_ArrayList();
//
//                temp = dataSnapshot.getValue(Member_ArrayList.class);
//
//                home_03_wright_user_nick.setText(temp.user_nick);
//                mGlideRequestManager.load(temp.user_profile).fitCenter().into(home_03_wright_user_profile);
//
//                if (temp.user_follower.containsKey(my_uid)) {
//
//                    home_03_view_follow_B.setSelected(true);
//                    home_03_view_follow_B.setTextColor(Color.parseColor("#e47700"));
//                    home_03_view_follow_B.setText("팔로잉");
//
//                } else {
//
//                    home_03_view_follow_B.setSelected(false);
//                    home_03_view_follow_B.setTextColor(Color.parseColor("#FFFFFF"));
//                    home_03_view_follow_B.setText("팔로우");
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        //좋아요 트랜젝션
//        FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").child(App.home_03_ArrayList.get(position_10).user_key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Home_02_02_ArrayList temp = dataSnapshot.getValue(Home_02_02_ArrayList.class);
//
//                home_03_like_text.setText("좋아요 " + temp.user_mybrary_like.size() + "개");
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        //팔로우 버튼 트렌잭션
//        home_03_view_follow_B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //팔로우
//                onFollowerClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.home_03_ArrayList.get(position_10).getUser_uid()));
//
//                //팔로잉
//                onFollowingClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()));
//
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Member_ArrayList temp = new Member_ArrayList();
//
//                temp = dataSnapshot.getValue(Member_ArrayList.class);
//
//                if (!(temp.user_following.containsKey(App.home_03_ArrayList.get(position_10).getUser_uid()))) {
//                    // Unstar the post and remove self from stars
//                    home_03_view_follow_B.setSelected(false);
//                    home_03_view_follow_B.setTextColor(Color.parseColor("#FFFFFF"));
//                    home_03_view_follow_B.setText("팔로우");
//                } else {
//                    // Star the post and add self to stars
//                    home_03_view_follow_B.setSelected(true);
//                    home_03_view_follow_B.setTextColor(Color.parseColor("#e47700"));
//                    home_03_view_follow_B.setText("팔로잉");
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        home_03_like_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (App.home_03_ArrayList.get(position_10).user_mybrary_like.containsKey(App.user_UID_get())) {
//                    MainActivity.showToast(Home_03_View.this, "취소하셨습니다.");
//                } else {
//                    MainActivity.showToast(Home_03_View.this, "좋아요를 눌렀습니다.");
//                }
//
//                onStarClicked(FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").child(App.home_03_ArrayList.get(position_10).user_key));
//
//            }
//
//        });
//
//        //삭제하기
//        home_02_02_remove_B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_03_View.this);
//                alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                    }
//
//                }).setNegativeButton("네",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                key = App.home_03_ArrayList.get(position_10).getUser_key();
//
//                                //파이어베이스 데이터베이스 선언
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                final DatabaseReference myRef = database.getReference("Users_MyBrary");
//
//                                //리싸이클러뷰 파이어베이스 업데이트
//                                myRef.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                                            Home_02_02_ArrayList home_02_02_arrayList_00 = new Home_02_02_ArrayList();
//                                            home_02_02_arrayList_00 = snapshot.getValue(Home_02_02_ArrayList.class);
//
//                                            Log.d("체크", "삭제 전");
//
//                                            if (home_02_02_arrayList_00.getUser_key().equals(key)) {
//
//                                                Log.d("체크", "삭제 진입");
//
//                                                myRef.child(home_02_02_arrayList_00.getUser_key()).removeValue();
//
//                                            }
//
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                                onBackPressed();
//
//                                return;
//                            }
//                        });
//
//                android.app.AlertDialog alert = alert_confirm.create();
//
//                alert.show();
//            }
//        });
//
//        //뒤로가기
//        findViewById(R.id.home_02_02_back_B).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                onBackPressed();
//
//            }
//        });
//
//    }
//
//    //좋아요 트렌잭션
//    private void onStarClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Home_02_02_ArrayList home_03_mybrary = mutableData.getValue(Home_02_02_ArrayList.class);
//                if (home_03_mybrary == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (home_03_mybrary.user_mybrary_like.containsKey(App.user_UID_get())) {
//                    // Unstar the post and remove self from stars
////                    home_03_mybrary.like_count = home_03_mybrary.like_count - 1;
//                    home_03_mybrary.user_mybrary_like.remove(App.user_UID_get());
//                    home_03_like_image.setSelected(false);
//                } else {
//                    // Star the post and add self to stars
////                    home_03_mybrary.like_count = home_03_mybrary.like_count + 1;
//                    home_03_mybrary.user_mybrary_like.put(App.user_UID_get(), true);
//                    home_03_like_image.setSelected(true);
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(home_03_mybrary);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//
//            }
//        });
//    }
//
//    //팔로워 트렌잭션
//    private void onFollowerClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Member_ArrayList home_03_user_follower = mutableData.getValue(Member_ArrayList.class);
//                if (home_03_user_follower == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (home_03_user_follower.user_follower.containsKey(my_uid)) {
//                    // Unstar the post and remove self from stars
//                    home_03_user_follower.user_follower.remove(my_uid);
//
//                } else {
//                    // Star the post and add self to stars
//                    home_03_user_follower.user_follower.put(my_uid, true);
//
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(home_03_user_follower);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//
//            }
//        });
//    }
//
//    //팔로잉 트렌잭션
//    private void onFollowingClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Member_ArrayList home_03_user_following = mutableData.getValue(Member_ArrayList.class);
//                if (home_03_user_following == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (home_03_user_following.user_following.containsKey(opponent_uid)) {
//                    // Unstar the post and remove self from stars
//                    home_03_user_following.user_following.remove(opponent_uid);
//                } else {
//                    // Star the post and add self to stars
//                    home_03_user_following.user_following.put(opponent_uid, true);
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(home_03_user_following);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        Intent intent1 = new Intent(Home_03_View.this, Home_03.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent1);
//
//    }
//
//
//}
//

package com.example.dudu.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_03_View extends AppCompatActivity {

    String key;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    Home_02_02_ArrayList now_mybrary = new Home_02_02_ArrayList();

    //보기
    ImageView home_02_02_book_image;
    TextView home_02_02_book_name;
    TextView home_02_02_book_author;
    TextView home_02_02_book_date;
    TextView home_02_02_book_main;
    ImageButton home_02_02_back_B;
    Button home_02_02_remove_B;
    CircleImageView home_03_wright_user_profile;
    TextView home_03_wright_user_nick;
    Button home_03_view_follow_B;
    ImageView home_03_like_image;
    TextView home_03_like_text;

    String opponent_uid;
    String my_uid;

    Context context;

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
        home_03_wright_user_profile = findViewById(R.id.wright_user_profile);
        home_03_wright_user_nick = findViewById(R.id.wright_user_nick);
        home_03_view_follow_B = findViewById(R.id.home_03_view_Follow);
        home_03_like_image = findViewById(R.id.home_03_like_image);
        home_03_like_text = findViewById(R.id.home_03_like_text);

        Intent intent1 = getIntent();

//        final int position_10 = intent1.getIntExtra("position", -1);
        String mybrary_key = intent1.getStringExtra("mybrary_key");
        opponent_uid = intent1.getStringExtra("now_mybrary_uid");

        my_uid = App.user_UID_get();

        FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").child(mybrary_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                now_mybrary = dataSnapshot.getValue(Home_02_02_ArrayList.class);

                mGlideRequestManager.load(now_mybrary.book).fitCenter().into(home_02_02_book_image);

                home_02_02_book_name.setText(now_mybrary.name);
                home_02_02_book_author.setText(now_mybrary.author);
                home_02_02_book_date.setText(now_mybrary.finish);
                home_02_02_book_main.setText(now_mybrary.main);

                //나의 게시물인지 확인 (내가 아니다)
                if (!(now_mybrary.user_uid.equals(App.user_UID_get()))) {
                    home_03_view_follow_B.setVisibility(View.VISIBLE);
                    home_02_02_remove_B.setVisibility(View.INVISIBLE);
                    home_03_like_text.setVisibility(View.VISIBLE);

                }else{
                    //나다
                    home_03_view_follow_B.setVisibility(View.GONE);
                    home_02_02_remove_B.setVisibility(View.VISIBLE);

                }

                if (now_mybrary.user_mybrary_like.containsKey(App.user_UID_get())) {
                    home_03_like_image.setSelected(true);
                }else{
                    home_03_like_image.setSelected(false);
                }

//                //작성자 UID 가져오기
//                opponent_uid = now_mybrary.user_uid;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //리싸이클러뷰 글쓴이 정보 파이어베이스에서 가져오기
        FirebaseDatabase.getInstance().getReference("User_Info").child(opponent_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Member_ArrayList temp = new Member_ArrayList();

                temp = dataSnapshot.getValue(Member_ArrayList.class);

                home_03_wright_user_nick.setText(temp.user_nick);
                mGlideRequestManager.load(temp.user_profile).fitCenter().into(home_03_wright_user_profile);

                if (temp.user_following.containsKey(my_uid)) {

                    home_03_view_follow_B.setSelected(true);
                    home_03_view_follow_B.setTextColor(Color.parseColor("#e47700"));
                    home_03_view_follow_B.setText("팔로잉");

                } else {

                    home_03_view_follow_B.setSelected(false);
                    home_03_view_follow_B.setTextColor(Color.parseColor("#FFFFFF"));
                    home_03_view_follow_B.setText("팔로우");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //좋아요 트랜젝션
        FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").child(mybrary_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Home_02_02_ArrayList temp = dataSnapshot.getValue(Home_02_02_ArrayList.class);

                home_03_like_text.setText("좋아요 " + String.valueOf(temp.user_mybrary_like.size()) + "개");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //팔로우 버튼 트렌잭션
        home_03_view_follow_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //팔로우
                onFollowerClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(opponent_uid));

                //팔로잉
                onFollowingClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()));

            }
        });

        FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Member_ArrayList temp = new Member_ArrayList();

                temp = dataSnapshot.getValue(Member_ArrayList.class);

                if (!(temp.user_following.containsKey(opponent_uid))) {
                    // Unstar the post and remove self from stars
                    home_03_view_follow_B.setSelected(false);
                    home_03_view_follow_B.setTextColor(Color.parseColor("#FFFFFF"));
                    home_03_view_follow_B.setText("팔로우");
                } else {
                    // Star the post and add self to stars
                    home_03_view_follow_B.setSelected(true);
                    home_03_view_follow_B.setTextColor(Color.parseColor("#e47700"));
                    home_03_view_follow_B.setText("팔로잉");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        home_03_like_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (now_mybrary.user_mybrary_like.containsKey(App.user_UID_get())) {
                    MainActivity.showToast(Home_03_View.this, "취소하셨습니다.");
                } else {
                    MainActivity.showToast(Home_03_View.this, "좋아요를 눌렀습니다.");
                }

                onStarClicked(FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").child(now_mybrary.user_key));

            }

        });

        //삭제하기
        home_02_02_remove_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_03_View.this);
                alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                key = now_mybrary.getUser_key();

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

    //좋아요 트렌잭션
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Home_02_02_ArrayList home_03_mybrary = mutableData.getValue(Home_02_02_ArrayList.class);
                if (home_03_mybrary == null) {
                    return Transaction.success(mutableData);
                }

                if (home_03_mybrary.user_mybrary_like.containsKey(App.user_UID_get())) {
                    // Unstar the post and remove self from stars
                    home_03_mybrary.user_mybrary_like.remove(App.user_UID_get());
                    home_03_like_image.setSelected(false);
                } else {
                    // Star the post and add self to stars
                    home_03_mybrary.user_mybrary_like.put(App.user_UID_get(), true);
                    home_03_like_image.setSelected(true);
                }

                // Set value and report transaction success
                mutableData.setValue(home_03_mybrary);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    //팔로워 트렌잭션
    private void onFollowerClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Member_ArrayList home_03_user_follower = mutableData.getValue(Member_ArrayList.class);
                if (home_03_user_follower == null) {
                    return Transaction.success(mutableData);
                }

                if (home_03_user_follower.user_follower.containsKey(my_uid)) {
                    // Unstar the post and remove self from stars
                    home_03_user_follower.user_follower.remove(my_uid);

                } else {
                    // Star the post and add self to stars
                    home_03_user_follower.user_follower.put(my_uid, true);

                }

                // Set value and report transaction success
                mutableData.setValue(home_03_user_follower);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    //팔로잉 트렌잭션
    private void onFollowingClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Member_ArrayList home_03_user_following = mutableData.getValue(Member_ArrayList.class);
                if (home_03_user_following == null) {
                    return Transaction.success(mutableData);
                }

                if (home_03_user_following.user_following.containsKey(opponent_uid)) {
                    // Unstar the post and remove self from stars
                    home_03_user_following.user_following.remove(opponent_uid);
                } else {
                    // Star the post and add self to stars
                    home_03_user_following.user_following.put(opponent_uid, true);
                }

                // Set value and report transaction success
                mutableData.setValue(home_03_user_following);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

//    @Override
//    public void onBackPressed() {
//
//        Intent intent1 = new Intent(Home_03_View.this, Home_03.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent1);
//
//    }


}

