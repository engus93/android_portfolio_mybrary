package com.example.dudu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home_02_Others extends AppCompatActivity {

    @BindView(R.id.home_02_others_back)
    ImageButton home_02_others_back;

    @BindView(R.id.home_02_others_profile)
    ImageView home_02_others_profile;

    @BindView(R.id.home_02_others_title)
    TextView home_02_others_title;

    @BindView(R.id.home_02_others_re_mini_1_T)
    TextView home_02_others_book;

    @BindView(R.id.home_02_others_re_mini_2_T)
    TextView home_02_others_follower;

    @BindView(R.id.home_02_others_re_mini_3_T)
    TextView home_02_others_following;

    @BindView(R.id.home_02_others_re_screen_T)
    TextView home_02_others_screen;

    @BindView(R.id.home_02_others_follow)
    Button home_02_others_follow;

    @BindView(R.id.home_02_others_message)
    Button home_02_others_message;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    String other_user_uid;

    String roomkey;

    Member_ArrayList other_info = new Member_ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_02_others);
        ButterKnife.bind(this);

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(Home_02_Others.this);

        other_user_uid = getIntent().getStringExtra("user_uid");

        //유저 정보 세팅
        FirebaseDatabase.getInstance().getReference().child("User_Info").child(other_user_uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                other_info = dataSnapshot.getValue(Member_ArrayList.class);

                mGlideRequestManager.load(other_info.user_profile).fitCenter().into(home_02_others_profile);

                home_02_others_title.setText(other_info.user_nick);

                home_02_others_follower.setText(String.valueOf(other_info.user_follower.size()));

                home_02_others_following.setText(String.valueOf(other_info.user_following.size()));

                home_02_others_screen.setText(other_info.user_talk);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //채팅방으로 이동
        home_02_others_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("Chatting_Room").orderByChild("users/" + App.user_UID_get()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Home_04_ChatRoom_Model chatRoom_model = new Home_04_ChatRoom_Model();

                        Boolean skip = true;

                        for (DataSnapshot item : dataSnapshot.getChildren()) {

                            chatRoom_model = item.getValue(Home_04_ChatRoom_Model.class);

                            System.out.println("시발~" + other_user_uid);

                            if (chatRoom_model.users.containsKey(other_info.user_UID) && chatRoom_model.users.size() == 2) {

                                roomkey = item.getKey();

                                skip = false;

                                break;

                            }else{

                                chatRoom_model = new Home_04_ChatRoom_Model();

                            }

                        }

                        if (skip) {
                            roomkey = FirebaseDatabase.getInstance().getReference().child("Chatting_Room").push().getKey();

                            chatRoom_model.users.put(App.user_UID_get(), true);
                            chatRoom_model.users.put(other_info.user_UID, true);
                            chatRoom_model.now_login.put(App.user_UID_get(), true);
                            chatRoom_model.now_login.put(other_info.user_UID, true);
                            chatRoom_model.message_count.put(App.user_UID_get(), 0);
                            chatRoom_model.message_count.put(other_info.user_UID, 0);
                            chatRoom_model.lasttime = System.currentTimeMillis();
                            chatRoom_model.chat_medel_room_key = roomkey;

                            FirebaseDatabase.getInstance().getReference().child("Chatting_Room").child(roomkey).setValue(chatRoom_model);

                        }else{

                        }

                        Intent intent = new Intent(Home_02_Others.this, Home_04_Chatting.class);
                        intent.putExtra("opponent_uid", other_info.user_UID);
                        intent.putExtra("chat_room_key", roomkey);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //팔로우 버튼 트렌잭션
        home_02_others_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //팔로우
                onFollowerClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(other_user_uid));

                //팔로잉
                onFollowingClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()));

            }
        });

        //---------------------------리싸이클러뷰---------------------------------
        //리싸이클러뷰
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById(R.id.home_02_others_Recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,3);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Home_02_Others_Adapter myAdapter = new Home_02_Others_Adapter();
        mRecyclerView.setAdapter(myAdapter);

        FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Member_ArrayList temp = new Member_ArrayList();

                temp = dataSnapshot.getValue(Member_ArrayList.class);

                if (temp.user_following.containsKey(other_user_uid)) {
                    // Unstar the post and remove self from stars
                    home_02_others_follow.setSelected(true);
                    home_02_others_follow.setTextColor(Color.parseColor("#e47700"));
                    home_02_others_follow.setText("팔로잉");
                } else {
                    // Star the post and add self to stars
                    home_02_others_follow.setSelected(false);
                    home_02_others_follow.setTextColor(Color.parseColor("#FFFFFF"));
                    home_02_others_follow.setText("팔로우");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //팔로워 트렌잭션
    private void onFollowerClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Member_ArrayList home_03_user_follower = mutableData.getValue(Member_ArrayList.class);
                if (home_03_user_follower == null) {
                    return Transaction.success(mutableData);
                }

                if (home_03_user_follower.user_follower.containsKey(App.user_UID_get())) {
                    // Unstar the post and remove self from stars
                    home_03_user_follower.user_follower.remove(App.user_UID_get());

                } else {
                    // Star the post and add self to stars
                    home_03_user_follower.user_follower.put(App.user_UID_get(), true);

                }

                // Set value and report transaction success
                mutableData.setValue(home_03_user_follower);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    //팔로잉 트렌잭션
    private void onFollowingClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Member_ArrayList home_03_user_following = mutableData.getValue(Member_ArrayList.class);
                if (home_03_user_following == null) {
                    return Transaction.success(mutableData);
                }

                if (home_03_user_following.user_following.containsKey(other_user_uid)) {
                    // Unstar the post and remove self from stars
                    home_03_user_following.user_following.remove(other_user_uid);
                } else {
                    // Star the post and add self to stars
                    home_03_user_following.user_following.put(other_user_uid, true);
                }

                // Set value and report transaction success
                mutableData.setValue(home_03_user_following);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }

    class Home_02_Others_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Home_02_02_ArrayList> others_mybrary = new ArrayList<>();

        //글라이드 오류 방지
        public RequestManager mGlideRequestManager;

        public Home_02_Others_Adapter() {

            FirebaseDatabase.getInstance().getReference().child("Users_MyBrary").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    others_mybrary.clear();

                    Home_02_02_ArrayList temp = new Home_02_02_ArrayList();

                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        temp = item.getValue(Home_02_02_ArrayList.class);

                        if(temp.user_uid.equals(other_user_uid)) {

                            others_mybrary.add(item.getValue(Home_02_02_ArrayList.class));

                        }else {

                        }

                    }

                    Collections.reverse(others_mybrary);

                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_re_02, parent, false);
            return new home_02_other_re(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            final home_02_other_re home_02_other_re = ((home_02_other_re)holder);

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_02_Others.this);

            mGlideRequestManager.load(others_mybrary.get(position).book).into(((home_02_other_re) holder).book_image);
            ((home_02_other_re) holder).book_name.setText(others_mybrary.get(position).name);
            ((home_02_other_re) holder).book_author.setText(others_mybrary.get(position).author);
            ((home_02_other_re) holder).book_finish.setText(others_mybrary.get(position).finish);

            //게시물 보기
            ((home_02_other_re) holder).click_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Home_02_Others.this, Home_03_View.class);
                    intent.putExtra("mybrary_key", others_mybrary.get(position).user_key);
                    intent.putExtra("now_mybrary_uid", others_mybrary.get(position).user_uid);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return others_mybrary.size();
        }

        public class home_02_other_re extends RecyclerView.ViewHolder {

            ImageView book_image;
            TextView book_name;
            TextView book_author;
            TextView book_finish;
            CardView click_item;

            public home_02_other_re(View view) {
                super(view);

                book_image = view.findViewById(R.id.home_02_re_book_I);
                book_name = view.findViewById(R.id.home_02_re_book_name_T);
                book_author = view.findViewById(R.id.home_02_re_book_author_T);
                book_finish = view.findViewById(R.id.home_02_re_book_finish_T);
                click_item = view.findViewById(R.id.home_02_cardview);


            }
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
