package com.example.dudu.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Home_00_my_info extends AppCompatActivity {

    Button bt_01;
    Button bt_02;
    Button bt_03;
    ImageButton my_info_back_B;
    private TextView nick;
    private TextView genre;

    int REQ_CAMERA_SELECT = 1000;
    int REQ_PICTURE_SELECT = 1000;
    int REQ_CALL_SELECT = 1000;
    int REQ_SMS_SELECT = 1000;

    int count = 0;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info);


//        Intent intent1 = getIntent();
//        String name;
//        if(intent1.hasExtra("nick")){
//            name = intent1.getStringExtra("nick");
//            nick = (TextView) findViewById(R.id.home_00_my_info_nick);
//            nick.setText(name);
//
//        }else if(intent1.hasExtra("genre")) {
//            name = intent1.getStringExtra("genre");
//            genre = (TextView) findViewById(R.id.home_00_my_info_genre);
//            genre.setText(name);
//        }

            //내 정보 수정 - > 닉네임 수정
        bt_02 = findViewById(R.id.my_info_nick_B);
        bt_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_00_my_info.this, Home_00_my_info_01.class);
                startActivity(intent1);
            }
        });

        //내 정보 수정 - > 책 장르 수정
        bt_03 = findViewById(R.id.my_info_genre_B);
        bt_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_00_my_info.this, Home_00_my_info_02.class);
                startActivity(intent1);
            }
        });

        my_info_back_B = findViewById(R.id.my_info_back_B);
        my_info_back_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    protected void onResume(){
        super.onResume();

        Intent intent1 = getIntent();
        String name;
        if(intent1.hasExtra("nick")){
            name = intent1.getStringExtra("nick");
            nick = (TextView) findViewById(R.id.home_00_my_info_nick);
            nick.setText(name);
        }

        if(intent1.hasExtra("genre")) {
            name = intent1.getStringExtra("genre");
            genre = (TextView) findViewById(R.id.home_00_my_info_genre);
            genre.setText(name);
        }

    }


    @Override
    public void onBackPressed(){

        Intent intent1 = new Intent(Home_00_my_info.this, Home_01.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intent1);

    }

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

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_00_my_info.this, Manifest.permission.CAMERA);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_00_my_info.this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_SELECT);
                            } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent);

                            }

                        }

                        break;
                    }
                    case 2: {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            int permissionCheck = ContextCompat.checkSelfPermission(Home_00_my_info.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                                // 권한 없음
                                ActivityCompat.requestPermissions(Home_00_my_info.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
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





}
