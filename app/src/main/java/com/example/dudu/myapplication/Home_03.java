package com.example.dudu.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home_03 extends AppCompatActivity {

    ImageButton home_03_menu_01_b;
    ImageButton home_03_menu_02_b;
    ImageButton home_03_menu_03_b;
    ImageButton home_03_menu_04_b;
    ImageButton home_03_menu_05_b;
    ImageButton home_03_search; //검색창 버튼

    ImageView drower_profile;   //드로어 프로필

    private long backPressedTime = 0;

    int REQ_CALL_SELECT = 1300;
    int REQ_SMS_SELECT = 1400;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_03);

        drower_profile = findViewById(R.id.home_drawer_profile);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(this);

        //---------------------------리싸이클러뷰---------------------------------
        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_03_Re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,3);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //메뉴 3 - > 메뉴 1
        home_03_menu_01_b = findViewById(R.id.home_03_menu_01_B);
        home_03_menu_01_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Home_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 3 - > 메뉴 2
        home_03_menu_02_b = findViewById(R.id.home_03_menu_02_B);
        home_03_menu_02_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Home_02.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 3 - > 메뉴 4
        home_03_menu_04_b = findViewById(R.id.home_03_menu_04_B);
        home_03_menu_04_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Home_04.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 3 - > 메뉴 5
        home_03_menu_05_b = findViewById(R.id.home_03_menu_05_B);
        home_03_menu_05_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Home_05.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 3 - > 검색창
        home_03_search = findViewById(R.id.home_03_search);
        home_03_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Search_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //유저 정보 파이어 베이스
        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Member_ArrayList temp = new Member_ArrayList();

                temp = dataSnapshot.child(App.user_UID_get()).getValue(Member_ArrayList.class);

                Log.d("체크", "" + temp.user_profile);

                mGlideRequestManager.load(temp.user_profile).fitCenter().into(drower_profile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //리싸이클러뷰 파이어베이스 업데이트
        FirebaseDatabase.getInstance().getReference("Users_MyBrary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                App.home_03_ArrayList.clear();

                Home_02_02_ArrayList home_03_arrayList = new Home_02_02_ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    home_03_arrayList = snapshot.getValue(Home_02_02_ArrayList.class);
                    App.home_03_ArrayList.add(home_03_arrayList);
                }

                Collections.reverse(App.home_03_ArrayList);

                Home_03_Adapter myAdapter = new Home_03_Adapter(getApplicationContext(), App.home_03_ArrayList);
                mRecyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //왼쪽 상단 메뉴

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_03);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer_01);

        // 드로어 여는 버튼 리스너
        findViewById(R.id.home_menu_01_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }

        });


        //네비게이션바- > 내 정보 변경
        findViewById(R.id.home_drawer_my_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_03.this, Home_00_my_info.class);
                startActivity(intent1);

            }
        });

        //네비게이션바 -> 왼쪽 상단 메뉴 문의하기
        findViewById(R.id.home_drawer_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showquestion();
            }
        });

        //네비게이션바 -> 왼쪽 로그아웃
        findViewById(R.id.home_drawer_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_03.this);
                alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Home_03.this, MainActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                return;
                            }
                        });

                android.app.AlertDialog alert = alert_confirm.create();

                alert.show();

            }
        });



    }

    //뒤로 두번 누르면 종료
    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && (long) 2000 >= intervalTime) {
            finishAffinity();
        } else {
            backPressedTime = tempTime;
            MainActivity.showToast(this, "한번 더 누르시면 종료가 됩니다.");
        }

    }

    //문의하기 메소드
    void showquestion() {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("전화로 문의하기");
        ListItems.add("SMS로 문의하기");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("문의하기");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                switch (pos + 1) {
                    case 1: {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_03.this, Manifest.permission.CALL_PHONE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_03.this, new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2905-3908"));
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_03.this, Manifest.permission.SEND_SMS);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_03.this, new String[]{Manifest.permission.SEND_SMS}, REQ_SMS_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Uri uri = Uri.parse("smsto:01029053908");
                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                intent.putExtra("sms_body", "성함 : \n내용 : ");
                                startActivity(intent);
                            }
                        }

                        break;
                    }
                }
            }
        });
        builder.show();
    }


    }


