package com.example.dudu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Home_00_my_info extends AppCompatActivity {

    ImageView iv_view;  //프로필 사진
    Button bt_02;   //닉네임 수정 버튼
    Button bt_03;   //닉네임 수정 버튼
    Button my_info_profile_B; //프로필 사진 수정 버튼
    ImageButton my_info_back_B; //뒤로가기

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 1112;
    private static final int REQUEST_TAKE_ALBUM = 1113;
    private static final int REQUEST_IMAGE_CROP = 1114;

    private String currentPhotoPath;//실제 사진 파일 경로

    Uri imageUri;
    Uri photoURI, albumURI;

    protected void onCreate(Bundle savedIstancesState) {
        super.onCreate(savedIstancesState);
        setContentView(R.layout.home_00_my_info);

        iv_view = (ImageView) findViewById(R.id.my_info_profile);   //프로필 사진

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

        //내 정보 수정 - > 대화명 수정
        findViewById(R.id.my_info_talk_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_00_my_info.this, Home_00_my_info_03.class);
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

        //왼쪽 상단 메뉴 프로필 사진 변경
        my_info_profile_B = findViewById(R.id.my_info_profile_B);
        my_info_profile_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //권한 체크
                checkPermission();

            }
        });

    }

    protected void onResume(){
        super.onResume();

        //쉐어드 생성
        SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 프사
        String profile = savenick_info.getString(App.User_ID + "_user_profile", "");

        if(!(profile.equals(""))) {

            Log.d("체크", "프사 수정");

            //해쉬맵 생성
            HashMap<String, String> profile_map = new HashMap<>();

            //해쉬맵에 삽입
            profile_map = App.gson.fromJson(profile,App.collectionTypeString);

            //이미지 삽입
            iv_view.setImageURI(Uri.parse(profile_map.get(App.User_ID + "_user_profile")));

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
            TextView user_nick = findViewById(R.id.home_00_my_info_nick);
            user_nick.setText(nick_map.get(App.User_ID + "_user_nick"));

        }

        //쉐어드 생성
        savenick_info = getSharedPreferences("member_info_02", MODE_PRIVATE);

        //쉐어드 안에 있는 정보 가져오기 - 좋아하는 책
        String like = savenick_info.getString(App.User_ID + "_user_like", "");

        if(!(like.equals(""))) {

            Log.d("체크", "좋아하는 책 수정");

            //해쉬맵 생성
            HashMap<String, String> like_map = new HashMap<>();

            //해쉬맵에 삽입
            like_map = App.gson.fromJson(like,App.collectionTypeString);

            //일치
            TextView user_like = findViewById(R.id.home_00_my_info_genre);
            user_like.setText(like_map.get(App.User_ID + "_user_like"));

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
            TextView user_talk = findViewById(R.id.home_00_my_info_talk);
            user_talk.setText(user_talk_map.get(App.User_ID + "_user_talk"));

        }

    }

    //프로필 사진 설정
    void showprofile() {

            final List<String> ListItems = new ArrayList<>();
            ListItems.add("사진 찍기");
            ListItems.add("앨범 선택");
            final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("프로필 사진");
            builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int pos) {

                            if (pos == 0) {
                                selectPhoto();
                            } else if (pos == 1) {
                                selectGallery();
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
                    imageUri = FileProvider.getUriForFile(this, "com.example.dudu.myapplication", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }

        }
    }

    //이미지 파일화 시키기

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".png";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "MyBrary");

        if (!storageDir.exists()) {
            Log.i("currentPhotoPath", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        currentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
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

        iv_view.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

        //쉐어드 생성
        SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);
        SharedPreferences.Editor save = savenick_info.edit();

        //해쉬맵 생성
        HashMap<String, String> profile_map = new HashMap<>();

        //정보 삽입
        String user_profile = imageUri.toString();

        //정보 -> 해쉬맵에 삽입
        profile_map.put(App.User_ID + "_user_profile", user_profile);

        //해쉬맵(Gson 변환) -> 쉐어드 삽입
        save.putString(App.User_ID + "_user_profile", App.gson.toJson(profile_map));

        //저장
        save.apply();

    }

    //갤러리에서 가져오기
    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    //선택한 데이터 처리 1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_TAKE_ALBUM:
//                    sendPicture(data.getData()); //갤러리에서 가져오기

                    if (resultCode == Activity.RESULT_OK) {
                        if (data.getData() != null) {
                            sendPicture(data.getData());
                            imageUri = data.getData();
                            String RealPthImage = getRealPathFromURI(imageUri);
                            imageUri = Uri.parse(RealPthImage);

                            //쉐어드 생성
                            SharedPreferences savenick_info = getSharedPreferences("member_info_00", MODE_PRIVATE);
                            SharedPreferences.Editor save = savenick_info.edit();

                            //해쉬맵 생성
                            HashMap<String, String> profile_map = new HashMap<>();

                            //정보 삽입
                            String user_profile = imageUri.toString();

                            //정보 -> 해쉬맵에 삽입
                            profile_map.put(App.User_ID + "_user_profile", user_profile);

                            //해쉬맵(Gson 변환) -> 쉐어드 삽입
                            save.putString(App.User_ID + "_user_profile", App.gson.toJson(profile_map));

                            //저장
                            save.apply();

                        }
                    }

                    break;

                case REQUEST_TAKE_PHOTO:
                    getPictureForPhoto(); //카메라에서 가져오기

                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            Log.i("REQUEST_TAKE_PHOTO", "OK");
                            galleryAddPic();

                            iv_view.setImageURI(imageUri);

                        } catch (Exception e) {
                            Log.e("REQUEST_TAKE_PHOTO", e.toString());
                        }
                    } else {
                        Toast.makeText(Home_00_my_info.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
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
        iv_view.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기



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

    // 카메라 전용 크랍
    public void cropImage(){
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    //권한 체크 1
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {

//                Log.d("체크", "1");
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

//                Log.d("체크", "2");

            }
        }else{

            //프로필 사진 바꾸기
            showprofile();

        }

    }

    //권한 있으면 프사 기능
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:{
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

//                Log.d("체크", "권한 진입");

                showprofile();

                break;
            }

        }


    }








}
