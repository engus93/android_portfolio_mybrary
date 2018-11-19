package com.example.dudu.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home_01 extends AppCompatActivity {

    ImageButton home_01_menu_01_b;  //홈메뉴 버튼 1
    ImageButton home_01_menu_02_b;  //홈메뉴 버튼 2
    ImageButton home_01_menu_03_b;  //홈메뉴 버튼 3
    ImageButton home_01_menu_04_b;  //홈메뉴 버튼 4
    ImageButton home_01_menu_05_b;  //홈메뉴 버튼 5
    ImageButton home_01_search; //검색창 버튼
    Button home_01_my_info;    //내 정보 변경

    ImageButton bt; //리싸이클러뷰 추가
    int book_rank_count = 1;    //순위 카운트

    private static final int REQ_CAMERA_SELECT = 1000;
    private static final int  REQ_PICTURE_SELECT = 1001;
    private static final int  REQ_CALL_SELECT = 1002;
    private static final int  REQ_SMS_SELECT = 1003;

    private long backPressedTime = 0;   //뒤로가기 2초 세기

    //왼쪽 상단 메뉴




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

        //메뉴 1 - > 내 정보 변경
        home_01_my_info = findViewById(R.id.home_drawer_my_info);
        home_01_my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_01.this, Home_00_my_info.class);
                startActivity(intent1);

            }
        });

//         리싸이클러뷰

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

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.home_drawer);

        // 드로어 화면을 열고 닫을 버튼 객체 참조
        final ImageButton btnOpenDrawer = (ImageButton) findViewById(R.id.home_menu_00_B);

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
                sendTakePhotoIntent();
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

//        bt = findViewById(R.id.home_01_search);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (book_rank_count) {
//
//                    case 1: {
//                        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "7위", "골든아워 7", "칠국종", "2018.10"));
//                        break;
//                    }
//
//                    case 2: {
//                        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "8위", "골든아워 8", "팔국종", "2018.10"));
//                        break;
//                    }
//
//                    case 3: {
//                        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "9위", "골든아워 9", "구국종", "2018.10"));
//                        break;
//                    }
//
//                    case 4: {
//                        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "10위", "골든아워 10", "십국종", "2018.10"));
//                        break;
//                    }
//
//                    default: {
//                        best_book_info_ArrayList.add(new Home_01_ArrayList(R.drawable.book_06, "11위", "없음", "없음", "없음"));
//                    }
//
//                }
//                mRecyclerView.setAdapter(myAdapter);
//                book_rank_count += 1;
//            }
//        });
//    }

    //뒤로 두번 누르면 종료
    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
            if (0 <= intervalTime && (long) 2000 >= intervalTime) {
                finishAffinity();
            } else {
                backPressedTime = tempTime;
                MainActivity.showToast(this, "한번 더 누르시면 종료가 됩니다.");
            }
        }
//    }


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

                        int permissionCheck = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.CAMERA);

                        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                            // 권한 없음
                            ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_SELECT);
                        } else {    // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivity(intent);

                        }

                    }

                    break;
                }
                case 2: {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        int permissionCheck = ContextCompat.checkSelfPermission(Home_01.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                            // 권한 없음
                            ActivityCompat.requestPermissions(Home_01.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PICTURE_SELECT);
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





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView)findViewById(R.id.photo)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
}
}




