package com.example.dudu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Home_02_01 extends AppCompatActivity {

    //등록

    Calendar myCalendar = Calendar.getInstance();

    ImageView home_02_01_book_image;
    EditText home_02_01_book_name;
    EditText home_02_01_book_author;
    TextView home_02_01_book_date;
    EditText home_02_01_book_main;
    Button home_02_01_book_plus_B;
    ImageButton home_02_01_back_B;



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
    Boolean Regeneration = false;

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

                                }else {

                                        Log.d("체크", "사진 어디냐1");

                                    //쉐어드 생성
                                    SharedPreferences saveMember_info = getSharedPreferences("mybrary", MODE_PRIVATE);
                                    SharedPreferences.Editor save = saveMember_info.edit();

                                    //해쉬맵 생성
                                    HashMap<String, Home_02_02_ArrayList> mybrary_map = new HashMap<>();

                                    App.mybrary_sort(); //정렬

                                    if(imageUri == null) {
                                        Log.d("체크", "널");
                                        //정보 삽입
                                        App.home_02_02_ArrayList.add(new Home_02_02_ArrayList("null", home_02_01_book_name.getText().toString(), home_02_01_book_author.getText().toString(), home_02_01_book_date.getText().toString(), home_02_01_book_main.getText().toString()));
                                    }else{
                                        Log.d("체크", "잘");
                                        //정보 삽입
                                        App.home_02_02_ArrayList.add(new Home_02_02_ArrayList(imageUri.toString(), home_02_01_book_name.getText().toString(), home_02_01_book_author.getText().toString(), home_02_01_book_date.getText().toString(), home_02_01_book_main.getText().toString()));
                                    }

                                    //정보 -> 해쉬맵에 삽입
                                    for(int i = 0; i < App.home_02_02_ArrayList.size(); i++){

                                        mybrary_map.put(App.User_ID + "_MyBrary_" + i , App.home_02_02_ArrayList.get(i));

                                    }

                                    save.clear();

                                    //해쉬맵(Gson 변환) -> 쉐어드 삽입
                                    save.putString(App.User_ID + "_MyBrary", App.gson.toJson(mybrary_map));

                                    //저장
                                    save.apply();

                                        Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        MainActivity.showToast(Home_02_01.this, "작성 되었습니다.");
                                        startActivity(intent1);



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

        if (Regeneration) {

            Regeneration = false;

            android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
            
            alert_confirm.setMessage("작성 중 내용 불러오기").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent1 = new Intent(Home_02_01.this, Home_02_01.class);

                    finish();

                    startActivity(intent1);

                }

            }).setNegativeButton("네",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                            Log.d("책등록", "등록됨");
                            return;
                        }
                    });

            android.app.AlertDialog alert = alert_confirm.create();

            alert.show();
        }

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
                        startActivity(intent1);

                    }

                }).setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Regeneration = true;
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
                        }else if (pos == 2) {

                            //검색해서 가져오기 (틀만 구현)
                            Toast.makeText(Home_02_01.this, "검색하러 가즈아ㅏㅏ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Home_02_01.this, Search_01.class);
                            startActivityForResult(intent, 4747);

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
        home_02_01_book_image.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

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
        home_02_01_book_image.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

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

                    if (resultCode == Activity.RESULT_OK) {
                        if (data.getData() != null) {
                            System.out.println(imageUri);
                            sendPicture(data.getData());
                            imageUri = data.getData();
                            String RealPthImage = getRealPathFromURI(imageUri);
                            imageUri = Uri.parse(RealPthImage);
                        }
                    }
                    break;

                case REQUEST_TAKE_PHOTO:
//                    getPictureForPhoto(); //카메라에서 가져오기

                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            Log.i("REQUEST_TAKE_PHOTO", "OK");
                            galleryAddPic();

                            home_02_01_book_image.setImageURI(imageUri);

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
            showmybrary();

        }

    }

    //권한 있으면 사진 기능 가능
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

                showmybrary();

                break;
            }

        }


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