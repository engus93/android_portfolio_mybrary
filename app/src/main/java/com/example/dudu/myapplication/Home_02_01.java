package com.example.dudu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home_02_01 extends AppCompatActivity {

    //파이어베이스
    private FirebaseStorage storage;    //스토리지
    public String change = "https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fhome_02_default.png?alt=media&token=41370ddd-23c6-4ae2-a2c1-68db57c9ae2f";
    String key; //푸쉬 키

    Uri downloadUri;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    //등록
    Calendar myCalendar = Calendar.getInstance();

    ImageView home_02_01_book_image;
    EditText home_02_01_book_name;
    EditText home_02_01_book_author;
    TextView home_02_01_book_date;
    EditText home_02_01_book_main;
    Button home_02_01_book_plus_B;
    ImageButton home_02_01_back_B;
    SpinKitView home_02_01_book_image_progress;

    Home_02_02_ArrayList mybrary_plus;  //파이어베이스 넣을 객체

    //사진 저장 변수
    Bitmap bitmap_pic;  //사진 비트맵 저장
    String string_pic;  //사진 스트링 변환

    Context context;

    //사진 추가하기

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 1112;
    private static final int REQUEST_TAKE_ALBUM = 1113;

    private String currentPhotoPath;//실제 사진 파일 경로

    Uri imageUri;
    String set_date;

    String search_book_name;
    String search_book_author;
    String search_book_image;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02_01);

        home_02_01_book_image = findViewById(R.id.home_02_01_book_image);
        home_02_01_book_name = findViewById(R.id.home_02_01_book_name);
        home_02_01_book_author = findViewById(R.id.home_02_01_author);
        home_02_01_book_date = (TextView) findViewById(R.id.home_02_01_date);
        home_02_01_book_main = findViewById(R.id.home_02_01_main);
        home_02_01_book_plus_B = findViewById(R.id.home_02_01_plus_B);
        home_02_01_back_B = findViewById(R.id.home_02_01_back_B);
        home_02_01_book_image_progress = findViewById(R.id.my_info_progress);

        Sprite FadingCircle = new FadingCircle();
        home_02_01_book_image_progress.setIndeterminateDrawable(FadingCircle);
        home_02_01_book_image_progress.setVisibility(View.GONE);

        storage = FirebaseStorage.getInstance();    //스토리지 객체

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(Home_02_01.this);

        //뒤로가기
        home_02_01_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //서재 등록 날짜 세팅
        Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분", java.util.Locale.getDefault());
        set_date = dateFormat.format(date);
        home_02_01_book_date.setText(set_date);

        //책 사진 등록
        home_02_01_book_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermission();

            }
        });

        //등록 버튼

        findViewById(R.id.home_02_01_plus_B).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
                alert_confirm.setMessage("등록하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (home_02_01_book_name.getText().toString().length() <= 0) {

                                    MainActivity.showToast(Home_02_01.this, "제목을 입력해주세요.");

                                } else if (home_02_01_book_main.getText().toString().length() <= 0) {

                                    MainActivity.showToast(Home_02_01.this, "내용를 입력해주세요.");

                                } else {

//                                        Log.d("체크", "사진 어디냐1");

//                                    //쉐어드 생성
//                                    SharedPreferences saveMember_info = getSharedPreferences("mybrary", MODE_PRIVATE);
//                                    SharedPreferences.Editor save = saveMember_info.edit();
//
//                                    //해쉬맵 생성
//                                    HashMap<String, Home_02_02_ArrayList> mybrary_map = new HashMap<>();

//                                    App.mybrary_sort(); //정렬

//                                    //그리드 리싸이클러뷰 역순
//                                    Collections.reverse(App.home_02_02_ArrayList);

                                    //파이어베이스 데이터베이스 선언
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users_MyBrary");

                                    //랜덤 키 생성
                                    key = myRef.push().getKey();

                                    //정보 삽입
                                    App.home_02_02_ArrayList.add(new Home_02_02_ArrayList(change, home_02_01_book_name.getText().toString(), home_02_01_book_author.getText().toString(), home_02_01_book_date.getText().toString(), home_02_01_book_main.getText().toString(), key, App.user_UID_get()));
                                    mybrary_plus = new Home_02_02_ArrayList(change, home_02_01_book_name.getText().toString(), home_02_01_book_author.getText().toString(), home_02_01_book_date.getText().toString(), home_02_01_book_main.getText().toString(), key, App.user_UID_get());

                                    //파이어베이스에 저장
                                    myRef.child(key).setValue(mybrary_plus);

                                    FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            Member_ArrayList temp = dataSnapshot.child(App.user_UID_get()).getValue(Member_ArrayList.class);

                                            Set set = temp.user_follower.entrySet();

                                            Iterator iterator = set.iterator();

                                            while (iterator.hasNext()) {

                                                Map.Entry entry = (Map.Entry) iterator.next();

                                                String key = (String) entry.getKey();

                                                String user_token = dataSnapshot.child(key).child("user_token").getValue(String.class);

                                                sendFcm(user_token);

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


//                                    //정보 -> 해쉬맵에 삽입
//                                    for(int i = 0; i < App.home_02_02_ArrayList.size(); i++){
//                                        mybrary_map.put(App.User_ID + "_MyBrary_" + i , App.home_02_02_ArrayList.get(i));
//                                    }
//
//                                    save.clear();
//
//                                    //해쉬맵(Gson 변환) -> 쉐어드 삽입
//                                    save.putString(App.User_ID + "_MyBrary", App.gson.toJson(mybrary_map));
//
//                                    //저장
//                                    save.apply();

                                    Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    MainActivity.showToast(Home_02_01.this, "작성 되었습니다.");
                                    startActivity(intent1);

//                                    //그리드 리싸이클러뷰 역순
//                                    Collections.reverse(App.home_02_02_ArrayList);

                                    return;
                                }


                            }
                        });

                android.app.AlertDialog alert = alert_confirm.create();

                alert.show();

            }

        });

    }

    //임시저장 불러오기
    @Override
    protected void onResume() {
        super.onResume();

        //임시 저장 루트
        if (App.Regeneration) {

            App.Regeneration = false;

            android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);

            alert_confirm.setMessage("작성 중 내용 불러오기").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    search_book_image = getIntent().getStringExtra("search_book_image");
                    search_book_name = getIntent().getStringExtra("search_book_name");
                    search_book_author = getIntent().getStringExtra("search_book_author");

                    //책 정보 가져오기
                    if (search_book_name != null && search_book_author != null && search_book_image != null) {

                        Log.d("체크", "리즘2" + search_book_name);

                        home_02_01_book_name.setText(search_book_name);
                        home_02_01_book_author.setText(search_book_author);
                        mGlideRequestManager.load(search_book_image).fitCenter().into(home_02_01_book_image);

                        home_02_01_book_main.setText(App.home_02_01_temp_main);

                        change = search_book_image;

                        App.home_02_01_temp_main = "";

                    } else {
                        //리셋 시키기
                        home_02_01_book_name.setText("");
                        home_02_01_book_author.setText("");
                        home_02_01_book_main.setText("");

                        mGlideRequestManager.load("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fhome_02_default.png?alt=media&token=41370ddd-23c6-4ae2-a2c1-68db57c9ae2f").fitCenter().into(home_02_01_book_image);

                        change = search_book_image;

                        //서재 등록 날짜 세팅
                        Date date = new Date();
                        final SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분", java.util.Locale.getDefault());
                        set_date = dateFormat.format(date);
                        home_02_01_book_date.setText(set_date);

                    }

                }

            }).setNegativeButton("네",

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            change = App.Regeneration_list.book;

                            mGlideRequestManager.load(change).fitCenter().into(home_02_01_book_image);
                            home_02_01_book_name.setText(App.Regeneration_list.name);
                            home_02_01_book_author.setText(App.Regeneration_list.author);
                            home_02_01_book_main.setText(App.Regeneration_list.main);


                            return;
                        }
                    });

            android.app.AlertDialog alert = alert_confirm.create();

            alert.show();
        }else{

            search_book_image = getIntent().getStringExtra("search_book_image");
            search_book_name = getIntent().getStringExtra("search_book_name");
            search_book_author = getIntent().getStringExtra("search_book_author");

            //책 정보 가져오기
            if (search_book_name != null && search_book_author != null && search_book_image != null) {

                Log.d("체크", "리즘2" + search_book_name);

                home_02_01_book_name.setText(search_book_name);
                home_02_01_book_author.setText(search_book_author);
                mGlideRequestManager.load(search_book_image).fitCenter().into(home_02_01_book_image);

                home_02_01_book_main.setText(App.home_02_01_temp_main);

                change = search_book_image;

                App.home_02_01_temp_main = "";

            }

        }

    }

    void sendFcm(String to_user) {

        Log.d("메세지", "되고있니");

        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = to_user;
        //백그라운드용
        notificationModel.notification.title = App.my_nick;
        notificationModel.notification.text = App.my_nick + "님이 글을 작성하였습니다.";

        //포그라운드용
        notificationModel.data.title = App.my_nick;
        notificationModel.data.text = App.my_nick + "님이 글을 작성하였습니다.";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));

        Request request = new Request.Builder().header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AIzaSyCVJJ2FRUYpnUY2cZrBJo5LsxYjezSJFko")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    //날짜 표시 메소드
    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        home_02_01_book_date.setText(sdf.format(myCalendar.getTime()));
    }

    //뒤로가기 오버라이드
    @Override
    public void onBackPressed() {

        //등록 중단 취소
        android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
        alert_confirm.setMessage("등록을 중단 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

            //등록중단
        }).setNegativeButton("네", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
                alert_confirm.setMessage("임시 저장 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();
                        startActivity(intent1);

                    }

                }).setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.Regeneration = true;
                        App.Regeneration_list.book = change;
                        App.Regeneration_list.name = home_02_01_book_name.getText().toString();
                        App.Regeneration_list.author = home_02_01_book_author.getText().toString();
                        App.Regeneration_list.main = home_02_01_book_main.getText().toString();

                        Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);
                        return;
                    }
                });

                android.app.AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        android.app.AlertDialog alert = alert_confirm.create();
        alert.show();

    }

    //------------------------책 사진 적용--------------

    //프로필 사진 설정
    void showmybrary() {

        final List<String> ListItems = new ArrayList<>();
        ListItems.add("사진 찍기");
        ListItems.add("앨범 선택");
        ListItems.add("검색해서 가져오기");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("서재 사진 등록");
        builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int pos) {

                        if (pos == 0) {
                            selectPhoto();
                        } else if (pos == 1) {
                            selectGallery();
                        } else if (pos == 2) {

                            App.home_02_01_temp_main = home_02_01_book_main.getText().toString();

                            //검색해서 가져오기 (틀만 구현)
                            Intent intent = new Intent(Home_02_01.this, Search_01.class);
                            startActivity(intent);

                        }

                    }

                }

        );

        builder.show();
    }

    //카메라로 찍은 사진 가져오기
    private void selectPhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    imageUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }

        }
    }

    //이미지 파일화 시키기
    public File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/MyBrary/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyBrary/" + imageFileName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    //    사진 가져오기
    private void galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    //찍은 사진 가져오기
    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
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

        bitmap_pic = rotate(bitmap, exifDegree);    //비트맵 회전
        string_pic = App.getBase64String(bitmap_pic);   //비트맵 스트링 변환

//        home_02_01_book_image.setImageBitmap(bitmap_pic);//이미지 뷰에 비트맵 넣기

//        //쉐어드 생성
//        SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);
//        SharedPreferences.Editor save = savenick_info.edit();
//
//        //해쉬맵 생성
//        HashMap<String, String> profile_map = new HashMap<>();
//
//        //정보 삽입
//        String user_profile = string_pic;
//
//        //정보 -> 해쉬맵에 삽입
//        profile_map.put(App.User_ID + "_user_profile", user_profile);
//
//        //해쉬맵(Gson 변환) -> 쉐어드 삽입
//        save.putString(App.User_ID + "_user_profile", App.gson.toJson(profile_map));
//
//        //저장
//        save.apply();

        Toast.makeText(Home_02_01.this, "사진이 업로드 되는 동안 잠시만 기다려주세요. :)", Toast.LENGTH_SHORT).show();

        //파이어 베이스 업로드
        upload(currentPhotoPath);


    }

    //갤러리에서 가져오기
    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    //선택한 데이터 처리 1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_TAKE_ALBUM:

                    if (resultCode == Activity.RESULT_OK) {
                        if (data.getData() != null) {
                            sendPicture(data.getData());

//                            //쉐어드 생성
//                            SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);
//                            SharedPreferences.Editor save = savenick_info.edit();
//
//                            //해쉬맵 생성
//                            HashMap<String, String> profile_map = new HashMap<>();
//
//                            //정보 삽입
//                            String user_profile = string_pic;
//
//                            //정보 -> 해쉬맵에 삽입
//                            profile_map.put(App.User_ID + "_user_profile", user_profile);
//
//                            //해쉬맵(Gson 변환) -> 쉐어드 삽입
//                            save.putString(App.User_ID + "_user_profile", App.gson.toJson(profile_map));
//
//                            //저장
//                            save.apply();

                        }
                    }

                    break;

                case REQUEST_TAKE_PHOTO:

                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            Log.i("REQUEST_TAKE_PHOTO", "OK");
                            galleryAddPic();

                            getPictureForPhoto(); //카메라에서 가져오기

                        } catch (Exception e) {
                            Log.e("REQUEST_TAKE_PHOTO", e.toString());
                        }
                    } else {
                        Toast.makeText(Home_02_01.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                    break;

                default:
                    break;
            }

        }
    }

    //선택한 데이터 처리 2
    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환

        bitmap_pic = rotate(bitmap, exifDegree);    //비트맵 회전
        string_pic = App.getBase64String(bitmap_pic);   //비트맵 스트링 변환


        Toast.makeText(Home_02_01.this, "사진이 업로드 되는 동안 잠시만 기다려주세요. :)", Toast.LENGTH_SHORT).show();

        upload(imagePath);

    }

    //이미지 찍은 포커스 대로 가져오기
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

    //이미지 정방향 맞춰주기
    private Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    //경로 가져오기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    //권한 체크 1
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {

                new android.app.AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);

            }
        } else {

            //책 사진 바꾸기
            showmybrary();

        }

    }

    //권한 있으면 프사 기능
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA: {
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

                showmybrary();

                break;
            }

        }

    }

    public void upload(String uri) {

        home_02_01_book_image.setVisibility(View.INVISIBLE);
        home_02_01_book_image_progress.setVisibility(View.VISIBLE);

        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(uri));
        final StorageReference riversRef = storageRef.child("MyBrary/User_MyBrary/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();

                    System.out.println(downloadUri);

                    //정보 삽입
                    change = downloadUri.toString();


                    home_02_01_book_image_progress.setVisibility(View.GONE);

                    home_02_01_book_image.setImageBitmap(bitmap_pic);
                    home_02_01_book_image.setVisibility(View.VISIBLE);

                    Toast.makeText(Home_02_01.this, "사진이 업로드 되었습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Home_02_01.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}


// ---------------------- 캘린더 날짜 선택 -------------------------[
//            //오늘 날짜 세팅
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth, int dayOfhour, int hourOfmin) {
//
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                updateLabel();
//
//            }
//
//        };
//
//            //선택 날짜 캐치
//        findViewById(R.id.home_02_01_date).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                new DatePickerDialog(Home_02_01.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//
//        });