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
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Home_04 extends AppCompatActivity {

    ImageButton home_04_menu_01_b;
    ImageButton home_04_menu_02_b;
    ImageButton home_04_menu_03_b;
    ImageButton home_04_menu_04_b;
    ImageButton home_04_menu_05_b;
    ImageButton home_04_search; //검색창 버튼

    private long backPressedTime = 0;

    int REQ_CAMERA_SELECT = 1000;
    int REQ_PICTURE_SELECT = 1000;
    int REQ_CALL_SELECT = 1000;
    int REQ_SMS_SELECT = 1000;


    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04);

        //메뉴 1 - > 메뉴 1
        home_04_menu_01_b = findViewById(R.id.home_04_menu_01_B);
        home_04_menu_01_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 1 - > 메뉴 2
        home_04_menu_02_b = findViewById(R.id.home_04_menu_02_B);
        home_04_menu_02_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_02.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );
            }
        });

        //메뉴 1 - > 메뉴 3
        home_04_menu_03_b = findViewById(R.id.home_04_menu_03_B);
        home_04_menu_03_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_03.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 1 - > 메뉴 5
        home_04_menu_05_b = findViewById(R.id.home_04_menu_05_B);
        home_04_menu_05_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_05.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0,0 );

            }
        });

        //메뉴 3 - > 검색창
        home_04_search = findViewById(R.id.home_04_search);
        home_04_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Search_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //왼쪽 상단 메뉴

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_04);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer_01);

        // 드로어 화면을 열고 닫을 버튼 객체 참조
        ImageButton btnOpenDrawer = (ImageButton) findViewById(R.id.home_menu_01_B);

        // 드로어 여는 버튼 리스너
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }

        });


        //네비게이션바- > 내 정보 변경
        findViewById(R.id.home_drawer_my_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_00_my_info.class);
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
                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_04.this);
                alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Home_04.this, MainActivity.class);
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

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_04.this, Manifest.permission.CAMERA);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_04.this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_04.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_04.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
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

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_04.this, Manifest.permission.CALL_PHONE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_04.this, new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2905-3908"));
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_04.this, Manifest.permission.SEND_SMS);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_04.this, new String[]{Manifest.permission.SEND_SMS}, REQ_SMS_SELECT);
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
