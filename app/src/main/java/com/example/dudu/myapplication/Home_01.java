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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Home_01 extends AppCompatActivity {

    ImageButton home_01_menu_01_b;  //홈메뉴 버튼 1
    ImageButton home_01_menu_02_b;  //홈메뉴 버튼 2
    ImageButton home_01_menu_03_b;  //홈메뉴 버튼 3
    ImageButton home_01_menu_04_b;  //홈메뉴 버튼 4
    ImageButton home_01_menu_05_b;  //홈메뉴 버튼 5
    ImageButton home_01_search; //검색창 버튼
    Button home_01_my_info;    //내 정보 변경

    private static final int REQ_CALL_SELECT = 1300;
    private static final int REQ_SMS_SELECT = 1400;

    private long backPressedTime = 0;   //뒤로가기 2초 세기

    //왼쪽 상단 메뉴
    ImageView iv = null;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_01);



        //메뉴 1 - > 메뉴 2
        home_01_menu_02_b = findViewById(R.id.home_01_menu_02_B);
        home_01_menu_02_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Home_02.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0, 0);
            }
        });

        //메뉴 1 - > 메뉴 3
        home_01_menu_03_b = findViewById(R.id.home_01_menu_03_B);
        home_01_menu_03_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Home_03.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0, 0);

            }
        });

        //메뉴 1 - > 메뉴 4
        home_01_menu_04_b = findViewById(R.id.home_01_menu_04_B);
        home_01_menu_04_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Home_04.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0, 0);

            }
        });

        //메뉴 1 - > 메뉴 5
        home_01_menu_05_b = findViewById(R.id.home_01_menu_05_B);
        home_01_menu_05_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Home_05.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                overridePendingTransition(0, 0);

            }
        });

        //메뉴 1 - > 검색창
        home_01_search = findViewById(R.id.home_01_search);
        home_01_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Search_01.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });







        //--------------------------------리싸이클러뷰--------------------------------------------

        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_01_RE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<Home_01_ArrayList> best_book_info_ArrayList = new ArrayList<>();
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_01, "1위", "골든아워 1", "이국종", "2018.10"));
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_02, "2위", "그래서 하고 싶은 말이 뭔데?", "다케우치 가오루", "2018.08"));
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_03, "3위", "룬의 아이들 1", "전민희", "2018.11"));
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_04, "4위", "트렌드 코리아 2019", "김난도 전미영 이향은 이준영 등", "2018.10"));
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_05, "5위", "참을 수 없는 존재의 가벼움", "밀란 쿤데라", "2018.06"));
        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "6위", "처음부터 잘 쓰는 사람은 없습니다", "이다혜", "2018.10"));


        final Home_01_Adapter myAdapter = new Home_01_Adapter(best_book_info_ArrayList);

        mRecyclerView.setAdapter(myAdapter);






        //------------------------왼쪽 상단 네비게이션 바------------------------------------

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_01);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer_01);

        // 드로어 화면을 열고 닫을 버튼 객체 참조
        final ImageButton btnOpenDrawer = (ImageButton) findViewById(R.id.home_menu_00_B);

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
                Intent intent1 = new Intent(Home_01.this, Home_00_my_info.class);
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
                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_01.this);
                alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Home_01.this, MainActivity.class);
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

    //프로필 문의하기 메소드
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

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.CALL_PHONE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-2905-3908"));
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.SEND_SMS);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.SEND_SMS}, REQ_SMS_SELECT);
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


////왼쪽 상단 메뉴 프로필 사진 변경
//        iv = (ImageView) findViewById(R.id.home_drawer_profile);
//
//                iv.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        showprofile();
//        }
//        });




//        //프로필 사진 교체 메소드
//    void showprofile() {
//        final List<String> ListItems = new ArrayList<>();
//        ListItems.add("사진 찍기");
//        ListItems.add("앨범 선택");
//        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("프로필 사진");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int pos) {
//                switch (pos + 1) {
//
//                    case 1: {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                            int permissionCheck1 = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.CAMERA);
//                            int permissionCheck2 = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                            if (permissionCheck1 == PackageManager.PERMISSION_DENIED | permissionCheck2 == PackageManager.PERMISSION_DENIED) {
//
//                                if (permissionCheck1 == PackageManager.PERMISSION_DENIED) {
//                                    // 권한 없음
//                                    ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_SELECT);
//
//                                    if(permissionCheck2 == PackageManager.PERMISSION_DENIED){
//                                        // 권한 없음
//                                        ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
//                                    }
//                                } else {
//                                    ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
//                                }
//                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 1);
//
//                            }
//
//                        }
//
//                        break;
//                    }
//                case 2: {
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                        int permissionCheck = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//                            // 권한 없음
//                            ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
//                        } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.setType("image/*");
//                            startActivity(intent);
//                        }
//                    }
//
//                    break;
//                }
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        iv.setImageURI(data.getData());
//    }







