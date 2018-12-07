package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home_04_Chatting extends AppCompatActivity {

    //리싸이클러뷰
     RecyclerView mRecyclerView;
     RecyclerView.LayoutManager mLayoutManager;

    //파이어베이스
    private FirebaseStorage storage;    //스토리지
    public String change;
    String key; //푸쉬 키
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH시 mm분 ");

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    int user_count = 0;

    Uri downloadUri;

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

    ImageView home_04_chatting_joinlist; //채팅방 메뉴
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    TextView home_04_chatting_nick; //유저 닉네임 (방제)
    EditText home_04_chatting_ET;   //텍스트 적기
    ImageView home_04_chatting_send;    //보내기
    ImageView home_04_chatting_camera;   //카메라

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    //-----채팅방 변수들

    //상대방 정보
    private String opponent_uid;
    private String chatroom_key;


    Member_ArrayList opponent_chat_info;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_chatting);

        home_04_chatting_joinlist = findViewById(R.id.home_04_chatting_people_list);
        home_04_friendlist_back_B = findViewById(R.id.home_04_chatting_back_B);
        home_04_chatting_nick = findViewById(R.id.home_04_chatting_nick);
        home_04_chatting_ET = findViewById(R.id.home_04_chatting_ET);
        home_04_chatting_send = findViewById(R.id.home_04_chatting_send);
        home_04_chatting_camera = findViewById(R.id.home_04_chatting_camera);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(this);

        opponent_uid = getIntent().getStringExtra("opponent_uid");

        //보내는 버튼
        home_04_chatting_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Home_04_ChatRoom_Model chatRoom_model = new Home_04_ChatRoom_Model();

                chatRoom_model.users.put(App.user_UID_get(), true);
                chatRoom_model.users.put(opponent_uid, true);

                if (chatroom_key == null) {
                    home_04_chatting_send.setEnabled(false);    //전송버튼 비활성화 통신중에 잠시 대기
                    FirebaseDatabase.getInstance().getReference().child("Chatting_Room").push().setValue(chatRoom_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });
                } else {
                    //빈 텍스트 보내기 막기
                    if (!(home_04_chatting_ET.getText().length() <= 0)) {
                        Home_04_ChatRoom_Model.Message message = new Home_04_ChatRoom_Model.Message();
                        message.wright_user = App.user_UID_get();
                        message.contents = home_04_chatting_ET.getText().toString();
                        message.time = ServerValue.TIMESTAMP;

                        FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chatroom_key).child("message").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                //알림
                                sendFcm();

                                //텍스트 창 초기화
                                home_04_chatting_ET.setText(null);

                            }
                        });
                    }
                }


            }
        });

        //---------------------------리싸이클러뷰---------------------------------
        mRecyclerView = findViewById(R.id.home_04_chatting_re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //메뉴창
        home_04_chatting_joinlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.showToast(Home_04_Chatting.this, "메뉴 창!");

            }
        });

        //뒤로가기
        home_04_friendlist_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //카메라 기능
        home_04_chatting_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showchat();

            }
        });

        checkChatRoom();    //내가 생성하려는 방이 중복된게 있는지 체크

    }

    void sendFcm(){

        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = opponent_chat_info.push_Token;
        notificationModel.notification.tile = "보낸이 아이디";
        notificationModel.notification.text = home_04_chatting_ET.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));

        Request request = new Request.Builder().header("Content-Type", "application/json").addHeader("Authorization", "key=AIzaSyCVJJ2FRUYpnUY2cZrBJo5LsxYjezSJFko")
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

    void checkChatRoom(){

        FirebaseDatabase.getInstance().getReference().child("Chatting_Room").orderByChild("users/"+App.user_UID_get()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item : dataSnapshot.getChildren()){

                    Home_04_ChatRoom_Model chatRoom_model = item.getValue(Home_04_ChatRoom_Model.class);

                    if(chatRoom_model.users.containsKey(opponent_uid) && chatRoom_model.users.size() == 2){

                        chatroom_key = item.getKey();
                        home_04_chatting_send.setEnabled(true); //전송 버튼 살리기(활성화)
                        mRecyclerView.setAdapter(new Home_04_Chatting_Adapter());
                    }
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    class Home_04_Chatting_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<Home_04_ChatRoom_Model.Message> contents;

        //글라이드 오류 방지
        public RequestManager mGlideRequestManager;

        public Home_04_Chatting_Adapter() {
            contents = new ArrayList<>();

            //유저의 정보 가져오기
            FirebaseDatabase.getInstance().getReference().child("User_Info").child(opponent_uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    opponent_chat_info = dataSnapshot.getValue(Member_ArrayList.class);

                    home_04_chatting_nick.setText(opponent_chat_info.user_nick);

                    getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        //메세지 내용 불러오는 메소드
        void getMessageList(){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chatroom_key).child("message");
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    contents.clear();
                    Map<String, Object> read_user_map = new HashMap<>();

                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        String key = item.getKey();
                        Home_04_ChatRoom_Model.Message message_origin = item.getValue(Home_04_ChatRoom_Model.Message.class);
                        Home_04_ChatRoom_Model.Message message_modify = item.getValue(Home_04_ChatRoom_Model.Message.class);
                        message_modify.read.put(App.user_UID_get(), true);

                        read_user_map.put(key,message_modify);
                        contents.add(message_origin);
                    }

                    if(contents.size() > 0) {

                        if (!(contents.get(contents.size() - 1).read.containsKey(App.user_UID_get()))) {
                            FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chatroom_key).child("message").updateChildren(read_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    notifyDataSetChanged();
                                    mRecyclerView.scrollToPosition(contents.size() - 1);
                                }
                            });
                        } else {
                            notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(contents.size() - 1);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_chatting_re, parent, false);

            return new home_04_chatting_re(v1);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            home_04_chatting_re messageViewHolder = ((home_04_chatting_re)holder);

            //레이아웃 초기화
            messageViewHolder.chat_you.setVisibility(View.GONE);
            messageViewHolder.chat_me.setVisibility(View.GONE);

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_04_Chatting.this);

            long unixTime = (long) contents.get(position).time;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String message_time = simpleDateFormat.format(date);

            //내가 보낸 메세지
            if(contents.get(position).wright_user.equals(App.user_UID_get())){

                messageViewHolder.chat_me.setVisibility(View.VISIBLE);
                messageViewHolder.getUser_contents_me.setText(contents.get(position).contents);
                messageViewHolder.time_me.setText(message_time);

                read_user_count(position, messageViewHolder.read_me);


                //상대방이 보낸 메세지
            }else{

                messageViewHolder.chat_you.setVisibility(View.VISIBLE);
                messageViewHolder.user_contents.setText(contents.get(position).contents);
                messageViewHolder.user_nick.setText(opponent_chat_info.user_nick);
                mGlideRequestManager.load(opponent_chat_info.user_profile).fitCenter().into(messageViewHolder.user_profile);
                messageViewHolder.time_you.setText(message_time);

                read_user_count(position, messageViewHolder.read_you);

            }

        }

        @Override
        public int getItemCount() {
            return contents.size();
        }


        private class home_04_chatting_re extends RecyclerView.ViewHolder {
            ImageView user_profile;
            TextView user_nick;
            TextView user_contents;
            TextView getUser_contents_me;
            TextView time_you;
            TextView time_me;
            TextView read_you;
            TextView read_me;
            ConstraintLayout chat_you;
            ConstraintLayout chat_me;

            home_04_chatting_re(View view) {
                super(view);
                user_profile = view.findViewById(R.id.home_04_chat_profile);
                user_nick = view.findViewById(R.id.home_04_chat_nick);
                user_contents = view.findViewById(R.id.home_04_chat_main);
                getUser_contents_me = view.findViewById(R.id.home_04_chat_main_me);
                time_you = view.findViewById(R.id.time_you);
                time_me = view.findViewById(R.id.time_me);
                read_you = view.findViewById(R.id.home_04_chat_read_you);
                read_me = view.findViewById(R.id.home_04_chat_read_me);
                chat_you = view.findViewById(R.id.home_04_chatting_re_you);
                chat_me = view.findViewById(R.id.home_04_chatting_re_me);

            }
        }

        void read_user_count(final int position, final TextView textView) {
            if (user_count == 0) {

                FirebaseDatabase.getInstance().getReference("Chatting_Room").child(chatroom_key).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map<String, String> chat_user = (Map<String, String>) dataSnapshot.getValue();
                        user_count = chat_user.size();
                        int count = user_count - contents.get(position).read.size();

                        if (count > 0) {
                            textView.setVisibility((View.VISIBLE));
                            textView.setText(String.valueOf(count));
                        } else {
                            textView.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }else{

                int count = user_count - contents.get(position).read.size();

                if (count > 0) {
                    textView.setVisibility((View.VISIBLE));
                    textView.setText(String.valueOf(count));
                } else {
                    textView.setVisibility(View.INVISIBLE);
                }

            }
        }

    }

    @Override
    public void onBackPressed() {

        if(valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
        finish();

        Intent intent1 = new Intent(this, Home_04.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);

    }

//    //------------------------책 사진 적용--------------
//
//    //사진 설정
//    void showchat() {
//
//        final List<String> ListItems = new ArrayList<>();
//        ListItems.add("사진 찍기");
//        ListItems.add("앨범 선택");
//        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("서재 사진 등록");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int pos) {
//
//                        if (pos == 0) {
//                            selectPhoto();
//                        } else if (pos == 1) {
//                            selectGallery();
//                        } else {
//
//                        }
//
//
//                    }
//
//                }
//
//        );
//
//        builder.show();
//    }
//
//    //카메라로 찍은 사진 가져오기
//    private void selectPhoto() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//
//                }
//                if (photoFile != null) {
//                    imageUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
//                }
//            }
//
//        }
//    }
//
//    //이미지 파일화 시키기
//    public File createImageFile() throws IOException {
//        File dir = new File(Environment.getExternalStorageDirectory() +  "/MyBrary/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".png";
//
//        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyBrary/" + imageFileName);
//        currentPhotoPath = storageDir.getAbsolutePath();
//
//        return storageDir;
//
//    }
//
//    //    사진 가져오기
//    private void galleryAddPic() {
//        Log.i("galleryAddPic", "Call");
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        sendBroadcast(mediaScanIntent);
//    }
//
//    //찍은 사진 가져오기
//    private void getPictureForPhoto() {
//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(currentPhotoPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int exifOrientation;
//        int exifDegree;
//
//        if (exif != null) {
//            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            exifDegree = exifOrientationToDegrees(exifOrientation);
//        } else {
//            exifDegree = 0;
//        }
//
//        bitmap_pic = rotate(bitmap, exifDegree);    //비트맵 회전
//        string_pic = App.getBase64String(bitmap_pic);   //비트맵 스트링 변환
//
//        Toast.makeText(Home_04_Chatting.this, "사진이 업로드 되는 동안 잠시만 기다려주세요. :)", Toast.LENGTH_SHORT).show();
//
//        //파이어 베이스 업로드
//        upload(currentPhotoPath);
//
//
//    }
//
//    //갤러리에서 가져오기
//    private void selectGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
//    }
//
//    //선택한 데이터 처리 1
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            switch (requestCode) {
//
//                case REQUEST_TAKE_ALBUM:
//
//                    if (resultCode == Activity.RESULT_OK) {
//                        if (data.getData() != null) {
//                            sendPicture(data.getData());
//
//                        }
//                    }
//
//                    break;
//
//                case REQUEST_TAKE_PHOTO:
//
//                    if (resultCode == Activity.RESULT_OK) {
//                        try {
//                            Log.i("REQUEST_TAKE_PHOTO", "OK");
//                            galleryAddPic();
//
//                            getPictureForPhoto(); //카메라에서 가져오기
//
//                        } catch (Exception e) {
//                            Log.e("REQUEST_TAKE_PHOTO", e.toString());
//                        }
//                    } else {
//                        Toast.makeText(Home_04_Chatting.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
//                    }
//
//                    break;
//
//                default:
//                    break;
//            }
//
//        }
//    }
//
//    //선택한 데이터 처리 2
//    private void sendPicture(Uri imgUri) {
//
//        String imagePath = getRealPathFromURI(imgUri); // path 경로
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imagePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//
//        bitmap_pic = rotate(bitmap, exifDegree);    //비트맵 회전
//        string_pic = App.getBase64String(bitmap_pic);   //비트맵 스트링 변환
//
//        Toast.makeText(Home_04_Chatting.this, "사진이 업로드 되는 동안 잠시만 기다려주세요. :)", Toast.LENGTH_SHORT).show();
//
//        upload(imagePath);
//
//    }
//
//    //이미지 찍은 포커스 대로 가져오기
//    private int exifOrientationToDegrees(int exifOrientation) {
//        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
//            return 90;
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
//            return 180;
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
//            return 270;
//        }
//        return 0;
//    }
//
//    //이미지 정방향 맞춰주기
//    private Bitmap rotate(Bitmap src, float degree) {
//
//        // Matrix 객체 생성
//        Matrix matrix = new Matrix();
//        // 회전 각도 셋팅
//        matrix.postRotate(degree);
//        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
//        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
//                src.getHeight(), matrix, true);
//    }
//
//    //경로 가져오기
//    private String getRealPathFromURI(Uri contentUri) {
//        int column_index = 0;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor.moveToFirst()) {
//            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        }
//
//        return cursor.getString(column_index);
//    }
//
//    //권한 체크 1
//    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
//            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
//                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
//
//                new android.app.AlertDialog.Builder(this)
//                        .setTitle("알림")
//                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
//                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getPackageName()));
//                                startActivity(intent);
//                            }
//                        })
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                        .setCancelable(false)
//                        .create()
//                        .show();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
//
//            }
//        } else {
//
//            //프로필 사진 바꾸기
//            showchat();
//
//        }
//
//    }
//
//    //권한 있으면 프사 기능
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSION_CAMERA: {
//                for (int i = 0; i < grantResults.length; i++) {
//                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
//                    if (grantResults[i] < 0) {
//                        Toast.makeText(this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//
//                showchat();
//
//                break;
//            }
//
//        }
//
//    }
//
//    public void upload(String uri){
//
//        storage = FirebaseStorage.getInstance();    //스토리지 객체
//
//        StorageReference storageRef = storage.getReference();
//
//        Uri file = Uri.fromFile(new File(uri));
//        final StorageReference riversRef = storageRef.child("MyBrary/User_Chat/"+file.getLastPathSegment());
//        UploadTask uploadTask = riversRef.putFile(file);
//
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//
//                // Continue with the task to get the download URL
//                return riversRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    downloadUri = task.getResult();
//
//                    System.out.println(downloadUri);
//
//                    //정보 삽입
//                    change = downloadUri.toString();
//
//                    System.out.println(change);
//
//                    //서재 등록 날짜 세팅
//                    Date date = new Date();
//                    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분", java.util.Locale.getDefault());
//                    String set_date = dateFormat.format(date);
//
//                    Home_04_ChattingList chat_contents = new Home_04_ChattingList(set_date, App.user_UID_get(), "", change );
//                    FirebaseDatabase.getInstance().getReference("User_Message").child("User_Chat").child(App.now_chat_user.room_key).push().setValue(chat_contents);
//
//
//                    Toast.makeText(Home_04_Chatting.this, "사진이 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(Home_04_Chatting.this, "업로드 실패", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//
//    }


}


//        //상대방 UID 추출
//        if (!(App.now_chat_user.user_1.equals(App.user_UID_get()))) {
//            App.opponent.opponent_uid = App.now_chat_user.user_1;
//        } else {
//            App.opponent.opponent_uid = App.now_chat_user.user_2;
//        }

//        //유저 정보
//        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                App.opponent.opponent_nick = (String) dataSnapshot.child(App.opponent.opponent_uid).child("user_nick").getValue();
//                App.opponent.opponent_profile = (String) dataSnapshot.child(App.opponent.opponent_uid).child("user_profile").getValue();
//
//                home_04_chatting_nick.setText(App.opponent.opponent_nick);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//
//        //모든 채팅방 리스트 불러오기
//        FirebaseDatabase.getInstance().getReference("User_Message").child("User_Room").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Home_04_ChatRoom_Model single_chatting = new Home_04_ChatRoom_Model();
//
//                App.user_chat_room.clear();
//
//                //모든 채팅방 리스트 불러오기
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    single_chatting = snapshot.getValue(Home_04_ChatRoom_Model.class);
//
//                    App.user_chat_room.add(single_chatting);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });