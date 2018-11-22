package com.example.dudu.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.dudu.myapplication.MainActivity.showToast;

public class Home_02 extends AppCompatActivity {

    ImageButton home_02_menu_01_b;
    ImageButton home_02_menu_02_b;
    ImageButton home_02_menu_03_b;
    ImageButton home_02_menu_04_b;
    ImageButton home_02_menu_05_b;
    ImageButton home_02_search; //검색창 버튼

    private long backPressedTime = 0;

    int REQ_CAMERA_SELECT = 1000;
    int REQ_PICTURE_SELECT = 1000;
    int REQ_CALL_SELECT = 1000;
    int REQ_SMS_SELECT = 1000;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02);

        //메뉴 2 - > 메뉴 1
        home_02_menu_01_b = findViewById(R.id.home_02_menu_01_B);
        home_02_menu_01_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Home_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 2 - > 메뉴 3
        home_02_menu_03_b = findViewById(R.id.home_02_menu_03_B);
        home_02_menu_03_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Home_03.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 2 - > 메뉴 4
        home_02_menu_04_b = findViewById(R.id.home_02_menu_04_B);
        home_02_menu_04_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Home_04.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 2 - > 메뉴 5
        home_02_menu_05_b = findViewById(R.id.home_02_menu_05_B);
        home_02_menu_05_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Home_05.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 2 - > 검색창
        home_02_search = findViewById(R.id.home_02_search);
        home_02_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Search_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //메뉴 2 -> 서재 등록
        findViewById(R.id.home_02_mini_4_B).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Home_02.this, Home_02_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }

        });

        //메뉴 2 - > 메뉴 1
        findViewById(R.id.home_02_re_fropile_I).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer);

        // 드로어 화면을 열고 닫을 버튼 객체 참조
        ImageButton btnOpenDrawer = (ImageButton) findViewById(R.id.home_menu_00_B);

        // 드로어 여는 버튼 리스너
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //왼쪽 상단 메뉴 프로필 사진 변경
        findViewById(R.id.home_drawer_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showprofile();
            }
        });

        //왼쪽 상단 메뉴 문의하기
        findViewById(R.id.home_drawer_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showquestion();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_02_RE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,3);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final Home_02_Adapter myAdapter = new Home_02_Adapter(getApplicationContext(),Home_02_01.home_02_02_ArrayList);
        mRecyclerView.setAdapter(myAdapter);


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
            showToast(this, "한번 더 누르시면 종료가 됩니다.");
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 롱클릭했을 때 나오는 context Menu 의 항목을 선택(클릭) 했을 때 호출

        Log.d("팝업", "진입");

        switch(item.getItemId()) {
            case 1001 :// 빨강 메뉴 선택시
                Log.d("팝업", "수정완료");
                showToast(this, "수정 완료");
                return true;
            case 1002 :// 녹색 메뉴 선택시
                Log.d("팝업", "수정완료");
                showToast(this, "삭제 완료");
                return true;
        }

        return super.onContextItemSelected(item);
    }

    //프로필 사진 교체 메소드
    void showprofile() {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("사진 찍기");
        ListItems.add("앨범 선택");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("프로필 사진");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                switch (pos + 1) {

                    case 1: {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_02.this, Manifest.permission.CAMERA);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_02.this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_02.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_02.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
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

    //프로필 사진 교체 메소드
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

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_02.this, Manifest.permission.CALL_PHONE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_02.this, new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2905-3908"));
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_02.this, Manifest.permission.SEND_SMS);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_02.this, new String[]{Manifest.permission.SEND_SMS}, REQ_SMS_SELECT);
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
