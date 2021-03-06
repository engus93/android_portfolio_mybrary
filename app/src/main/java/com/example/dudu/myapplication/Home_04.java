package com.example.dudu.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.TreeMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home_04 extends AppCompatActivity {

    ImageButton home_04_menu_01_b;
    ImageButton home_04_menu_02_b;
    ImageButton home_04_menu_03_b;
    ImageButton home_04_menu_04_b;
    ImageButton home_04_menu_05_b;
    ImageButton home_04_friend_plus; //검색창 버튼

    Home_04_Adapter home_04_adapter = new Home_04_Adapter();

    ImageView drower_profile;   //드로어 프로필

    private long backPressedTime = 0;

    int REQ_CALL_SELECT = 1300;
    int REQ_SMS_SELECT = 1400;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    String opponent_uid = null;

    Animation shake;

    ProgressBar home_drower_progress;

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(this);

        drower_profile = findViewById(R.id.home_drawer_profile);
        home_drower_progress = findViewById(R.id.home_drower_progress);

        //애니메이션 파인드
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.message_shake);

        //메뉴 4 - > 메뉴 1
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

        //메뉴 4 - > 메뉴 2
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

        //메뉴 4 - > 메뉴 3
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


        //메뉴 4 - > 메뉴 5
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

        //메뉴 4 - > 채팅 추가
        home_04_friend_plus = findViewById(R.id.home_04_friend_plus);
        home_04_friend_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Home_04.this, Home_04_FriendList.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        findViewById(R.id.chat_bot_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(Home_04.this, ChatBot_main.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }
        });

        //유저 정보 파이어 베이스
        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Member_ArrayList temp = new Member_ArrayList();

                temp = dataSnapshot.child(App.user_UID_get()).getValue(Member_ArrayList.class);

                Log.d("체크", "" + temp.user_profile);

                mGlideRequestManager.load(temp.user_profile).fitCenter()
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                home_drower_progress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(drower_profile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    class Home_04_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Home_04_ChatRoom_Model> chatRoom_model = new ArrayList<>();
        private ArrayList<Home_04_ChatRoom_Model> chatRoom_model_sort = new ArrayList<>();
        private ArrayList<String> room_keys = new ArrayList<>();
        private ArrayList<String> opponent_users = new ArrayList<>();

        //글라이드 오류 방지
        public RequestManager mGlideRequestManager;

        String group_chat_nick = null;

        public Home_04_Adapter() {

            FirebaseDatabase.getInstance().getReference().child("Chatting_Room").orderByChild("users/" + App.user_UID_get()).equalTo(true).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatRoom_model.clear();
                    room_keys.clear();

                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        chatRoom_model.add(item.getValue(Home_04_ChatRoom_Model.class));

                    }

                    Collections.sort(chatRoom_model, new Comparator<Home_04_ChatRoom_Model>() {

                        @Override
                        public int compare(Home_04_ChatRoom_Model o1, Home_04_ChatRoom_Model o2) {
                            if (o1.lasttime > o2.lasttime) {
                                return 1;
                            } else if (o1.lasttime < o2.lasttime) {
                                return -1;
                            }
                            return 0;
                        }

                    });

                    Collections.reverse(chatRoom_model);

                    home_04_adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_re, parent, false);
            return new home_04_re(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            final home_04_re home_04_re_item = ((home_04_re)holder);
            String opponentuid = null;

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_04.this);

            //각 방의 유저를 체크
            for(String user_uid : chatRoom_model.get(position).users.keySet()){

                if(!(user_uid.equals(App.user_UID_get()))){

                    opponentuid = user_uid;

                    opponent_users.add(opponentuid);

                }

            }

            final String finalOpponentuid = opponentuid;

            FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //개인인지 단체인지 구별
                    if(chatRoom_model.get(position).users.size() > 2) {

                        Member_ArrayList opponent_info = new Member_ArrayList();

                        Boolean count = true;

                        for (String group_uid : chatRoom_model.get(position).users.keySet()) {

                            if (!(group_uid.equals(App.user_UID_get()))) {

                                opponent_info = dataSnapshot.child(group_uid).getValue(Member_ArrayList.class);

                                if (count) {

                                    group_chat_nick = opponent_info.user_nick;

                                    count = false;

                                } else {

                                    group_chat_nick += ", " + opponent_info.user_nick;

                                }
                            }

                        }

                        group_chat_nick = group_chat_nick + " (" + chatRoom_model.get(position).users.size() + "명)";

                        mGlideRequestManager.load(R.drawable.group_chat_profile).fitCenter().into(home_04_re_item.user_profile);
                        home_04_re_item.user_nick.setText(group_chat_nick);

                    }else {
                        Member_ArrayList opponent_info = dataSnapshot.child(finalOpponentuid).getValue(Member_ArrayList.class);
                        mGlideRequestManager.load(opponent_info.user_profile).fitCenter().into(home_04_re_item.user_profile);
                        home_04_re_item.user_nick.setText(opponent_info.user_nick);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if(chatRoom_model.get(position).message_count.get(App.user_UID_get()) == 0){
                home_04_re_item.message_count.setVisibility(View.INVISIBLE);
            }else{
                home_04_re_item.message_count.setVisibility(View.VISIBLE);
                home_04_re_item.message_count.setText(String.valueOf(chatRoom_model.get(position).message_count.get(App.user_UID_get())));
                home_04_re_item.click_item.startAnimation(shake);
            }

            //맵에 내용을 다 넣어서 메세지의 키 값을 스트링으로 뽑아서 그 것을 파이어 베이스에서 가져옴
            Map<String, Home_04_ChatRoom_Model.Message> messageMap = new TreeMap<>(Collections.<String>reverseOrder());
            messageMap.putAll(chatRoom_model.get(position).message);

            if (messageMap.keySet().toArray().length > 0) {
                String lastMessage = (String) Objects.requireNonNull(messageMap.keySet().toArray())[0];
                if(Objects.requireNonNull(chatRoom_model.get(position).message.get(lastMessage)).picture.length() > 5){
                    home_04_re_item.user_main.setText("사진");
                }else{
                    home_04_re_item.user_main.setText(Objects.requireNonNull(chatRoom_model.get(position).message.get(lastMessage)).contents);
                }

                //시간
                long unixTime = (long) Objects.requireNonNull(chatRoom_model.get(position).message.get(lastMessage)).time;
                Date date = new Date(unixTime);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh시 mm분");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String message_time = simpleDateFormat.format(date);
                home_04_re_item.user_time.setText(message_time);

                //채팅방 입장
                home_04_re_item.click_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        if (chatRoom_model.get(position).users.size() > 2) {
                            Intent intent = new Intent(v.getContext(), Home_04_Group_Chatting.class);
                            intent.putExtra("chat_room_key", chatRoom_model.get(position).chat_medel_room_key);
                            intent.putExtra("chat_room_name", group_chat_nick);
                            startActivity(intent);
                            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);

                        } else {

                            FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chatRoom_model.get(position).chat_medel_room_key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    Home_04_ChatRoom_Model temp = new Home_04_ChatRoom_Model();

                                    temp = dataSnapshot.getValue(Home_04_ChatRoom_Model.class);

                                    for(String user_uid : temp.users.keySet()){

                                        if(!(user_uid.equals(App.user_UID_get()))){

                                            opponent_uid = user_uid;

                                            break;

                                        }

                                    }

                                    Intent intent = new Intent(v.getContext(), Home_04_Chatting.class);
                                    intent.putExtra("chat_room_key", chatRoom_model.get(position).chat_medel_room_key);
                                    intent.putExtra("opponent_uid", opponent_uid);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }


                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return chatRoom_model.size();
        }

        public class home_04_re extends RecyclerView.ViewHolder {

            ImageView user_profile;
            TextView user_nick;
            TextView user_main;
            TextView user_time;
            ConstraintLayout click_item;
            TextView message_count;

            public home_04_re(View view) {
                super(view);

                user_profile = view.findViewById(R.id.home_04_re_profile);
                user_nick = view.findViewById(R.id.home_04_re_nick);
                user_main = view.findViewById(R.id.home_04_re_main);
                user_time = view.findViewById(R.id.home_04_re_time);
                click_item = view.findViewById(R.id.home_04_re_cardview);
                message_count = view.findViewById(R.id.home_04_message_count);


            }
        }

    }

   @Override
    protected void onResume() {
        super.onResume();

        //---------------------------리싸이클러뷰---------------------------------
        RecyclerView mRecyclerView = findViewById(R.id.home_04_re);
        mRecyclerView.setAdapter(home_04_adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}