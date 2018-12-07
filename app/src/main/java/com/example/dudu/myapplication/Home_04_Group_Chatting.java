package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Home_04_Group_Chatting extends AppCompatActivity {
    Map<String, Member_ArrayList> all_user_info = new HashMap<>();
    String chat_room_key;

    //채팅방 아이템
    ImageView home_04_chatting_joinlist; //채팅방 메뉴
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    TextView home_04_chatting_nick; //유저 닉네임 (방제)
    EditText home_04_chatting_ET;   //텍스트 적기
    ImageView home_04_chatting_send;    //보내기
    ImageView home_04_chatting_camera;   //카메라

    //파이어베이스
    private FirebaseStorage storage;    //스토리지
    public String change;
    String key; //푸쉬 키
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH시 mm분 ");

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    int user_count = 0;

    //채팅 내용
    List<Home_04_ChatRoom_Model.Message> contents = new ArrayList<>();

    //리싸이클러뷰
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_04_chatting);

        //파인드 뷰
        home_04_chatting_joinlist = findViewById(R.id.home_04_chatting_people_list);
        home_04_friendlist_back_B = findViewById(R.id.home_04_chatting_back_B);
        home_04_chatting_nick = findViewById(R.id.home_04_chatting_nick);
        home_04_chatting_ET = findViewById(R.id.home_04_chatting_ET);
        home_04_chatting_send = findViewById(R.id.home_04_chatting_send);
        home_04_chatting_camera = findViewById(R.id.home_04_chatting_camera);
        mRecyclerView = findViewById(R.id.home_04_chatting_re);

        chat_room_key = getIntent().getStringExtra("chat_room_key");

        FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item : dataSnapshot.getChildren()){
                    all_user_info.put(item.getKey(), item.getValue(Member_ArrayList.class));
                }

                init();

                mRecyclerView.setAdapter(new Group_Message_Adapter());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(Home_04_Group_Chatting.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    void init(){

        //보내는 버튼
        home_04_chatting_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Home_04_ChatRoom_Model.Message message = new Home_04_ChatRoom_Model.Message();
                message.wright_user = App.user_UID_get();
                message.contents = home_04_chatting_ET.getText().toString();
                message.time = ServerValue.TIMESTAMP;

                FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chat_room_key).child("message").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //텍스트 창 초기화
                        home_04_chatting_ET.setText(null);

                    }
                });


            }
        });

    }

    class Group_Message_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        //글라이드 오류 방지
        public RequestManager mGlideRequestManager;

        public Group_Message_Adapter(){
            getMessageList();
        }

        //메세지 내용 불러오는 메소드
        void getMessageList(){
            System.out.println(chat_room_key);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chat_room_key).child("message");
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
                            FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(chat_room_key).child("message").updateChildren(read_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_04_chatting_re,parent,false);

            return new Group_Message_ViewHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            Group_Message_ViewHolder messageViewHolder = ((Group_Message_ViewHolder)holder);

            //레이아웃 초기화
            messageViewHolder.chat_you.setVisibility(View.GONE);
            messageViewHolder.chat_me.setVisibility(View.GONE);

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_04_Group_Chatting.this);

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
                messageViewHolder.user_nick.setText(all_user_info.get(contents.get(position).wright_user).user_nick);
                mGlideRequestManager.load(all_user_info.get(contents.get(position).wright_user).user_profile).fitCenter().into(messageViewHolder.user_profile);
                messageViewHolder.time_you.setText(message_time);

                read_user_count(position, messageViewHolder.read_you);

            }

        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        private class Group_Message_ViewHolder extends RecyclerView.ViewHolder {
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

            public Group_Message_ViewHolder(View view) {
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
    }

    void read_user_count(final int position, final TextView textView) {
        if (user_count == 0) {

            FirebaseDatabase.getInstance().getReference("Chatting_Room").child(chat_room_key).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
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
