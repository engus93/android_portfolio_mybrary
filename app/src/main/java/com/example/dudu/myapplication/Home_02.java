package com.example.dudu.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.example.dudu.myapplication.MainActivity.showToast;

public class Home_02 extends AppCompatActivity {

    ImageButton home_02_menu_01_b;
    ImageButton home_02_menu_02_b;
    ImageButton home_02_menu_03_b;
    ImageButton home_02_menu_04_b;
    ImageButton home_02_menu_05_b;
    ImageButton home_02_search; //검색창 버튼

    TextView home_02_nick_title;

    TextView home_02_book_count;  //내 서재 게시글 변수

    private long backPressedTime = 0;

    int REQ_CALL_SELECT = 1300;
    int REQ_SMS_SELECT = 1400;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02);

        //-------------------------------쉐어드-------------------------------------

        //쉐어드 생성
        SharedPreferences saveMember_info = getSharedPreferences("member_info", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기
        String check = saveMember_info.getString(App.User_ID + "_MyBrary", "");

        home_02_book_count = findViewById(R.id.home_02_re_mini_1_T);  //내 서재 게시글 수
        home_02_nick_title = findViewById(R.id.home_02_nick_title); //내 닉네임 글

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
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_02);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer_02);

        // 드로어 여는 버튼 리스너
        findViewById(R.id.home_menu_02_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);

                System.out.println("드로어 열기");

            }
        });

        //네비게이션바- > 내 정보 변경
        findViewById(R.id.home_drawer_my_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_02.this, Home_00_my_info.class);
                startActivity(intent1);

                System.out.println("내정보 변경");

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
                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02.this);
                alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Home_02.this, MainActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();

        //--------------------------------------쉐어드---------------------------------------------------

        //쉐어드 생성
        SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 프사
        String profile = savenick_info.getString(App.User_ID + "_user_profile", "");

        //프사 세팅
        if(!(profile.equals(""))) {

            Log.d("체크", "프사 수정");

            //해쉬맵 생성
            HashMap<String, String> profile_map = new HashMap<>();

            //해쉬맵에 삽입
            profile_map = App.gson.fromJson(profile,App.collectionTypeString);

            //이미지 삽입 (드로어)
            ImageView drower_profile = findViewById(R.id.home_drawer_profile);
            Bitmap bitmap_pic = App.getBitmap(profile_map.get(App.User_ID + "_user_profile"));
            drower_profile.setImageBitmap(bitmap_pic);

            //이미지 삽입 (내 서재)
            ImageView mybrary_profile = findViewById(R.id.home_02_re_fropile_I);
            mybrary_profile.setImageBitmap(bitmap_pic);

        }

        //쉐어드 생성
        savenick_info = getSharedPreferences("member_info_01", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 닉네임
        String nick = savenick_info.getString(App.User_ID + "_user_nick", "");

        if(!(nick.equals(""))) {

            Log.d("체크", "닉네임 수정");

            //해쉬맵 생성
            HashMap<String, String> nick_map = new HashMap<>();

            //해쉬맵에 삽입
            nick_map = App.gson.fromJson(nick,App.collectionTypeString);

            //일치
            TextView user_nick = findViewById(R.id.home_02_nick_title);
            user_nick.setText(nick_map.get(App.User_ID + "_user_nick"));

        }

        //쉐어드 생성
        savenick_info = getSharedPreferences("member_info_03", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 대화명
        String talk = savenick_info.getString(App.User_ID + "_user_talk", "");

        if(!(talk.equals(""))) {

            Log.d("체크", "대화명 수정");

            //해쉬맵 생성
            HashMap<String, String> user_talk_map = new HashMap<>();

            //해쉬맵에 삽입
            user_talk_map = App.gson.fromJson(talk,App.collectionTypeString);

            //일치
            TextView user_talk = findViewById(R.id.home_02_re_screen_T);
            user_talk.setText(user_talk_map.get(App.User_ID + "_user_talk"));

        }

        //쉐어드 생성
        savenick_info = getSharedPreferences("mybrary", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 내 서재
        String mybrary = savenick_info.getString(App.User_ID + "_MyBrary", "");

        if (!(mybrary.equals(""))) {

            Log.d("체크", "잘 불러옴");

            //해쉬맵 생성
            HashMap<String, Home_02_02_ArrayList> mybrary_map = new HashMap<>();

            //해쉬맵에 삽입
            mybrary_map = App.gson.fromJson(mybrary, App.collectionTypeMyBrary);

            App.home_02_02_ArrayList.clear();

            for (int i = 0; i < mybrary_map.size(); i++) {

                Log.d("체크", "잘 넣음");

                App.home_02_02_ArrayList.add(mybrary_map.get(App.User_ID + "_MyBrary_" + i));

            }

            //내 서재 게시글 수 갱신
            String a = String.valueOf(App.home_02_02_ArrayList.size());
            home_02_book_count.setText(a);

            //---------------------------리싸이클러뷰---------------------------------
            RecyclerView mRecyclerView;
            RecyclerView.LayoutManager mLayoutManager;

            mRecyclerView = findViewById(R.id.home_02_RE);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this,3);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            //그리드 리싸이클러뷰 역순
            Collections.reverse(App.home_02_02_ArrayList);

            Home_02_Adapter myAdapter = new Home_02_Adapter(getApplicationContext(),App.home_02_02_ArrayList);
            mRecyclerView.setAdapter(myAdapter);

        }


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

    //문의 하기 메소드
    void showquestion() {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("전화로 문의하기");
        ListItems.add("SMS로 문의하기");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(Home_02.this);
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
