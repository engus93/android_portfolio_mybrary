package com.example.dudu.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Home_02_following extends AppCompatActivity {

    String opponent_uid;

    ImageView home_04_friend_send; //채팅방 만들기
    ImageView home_04_friendlist_back_B;  //뒤로가기 버튼
    TextView home_04_friendlist_title;  //타이틀 제목

    EditText home_04_friend_search;
    ImageView home_04_friend_search_image;

    List<Member_ArrayList> all_user_info = new ArrayList<>();
    List<Member_ArrayList> following_user_info = new ArrayList<>();
    List<Member_ArrayList> search_user_info = new ArrayList<>();
    Member_ArrayList my_info = new Member_ArrayList();

    Boolean first_login = true;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    Home_02_Following_Adapter home_02_following_adapter = new Home_02_Following_Adapter();

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_friendlist);

        home_04_friend_send = findViewById(R.id.home_04_friend_send);
        home_04_friendlist_back_B = findViewById(R.id.home_04_friendlist_back_B);
        home_04_friendlist_title = findViewById(R.id.home_04_friendlist_title);
        home_04_friend_search = findViewById(R.id.home_04_friend_search_ET);

        //세팅
        home_04_friendlist_title.setText("Following");
        home_04_friend_send.setVisibility(View.GONE);

        //뒤로가기
        home_04_friendlist_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        home_04_friend_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String searchText = home_04_friend_search.getText().toString();
                home_02_following_adapter.filter(searchText);

            }

        });

        //---------------------------리싸이클러뷰---------------------------------
        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.home_04_friend_re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(home_02_following_adapter);

    }

    public class Home_02_Following_Adapter extends RecyclerView.Adapter<Home_02_Following_Adapter.home_02_follower_re> {

        public Home_02_Following_Adapter() {

            FirebaseDatabase.getInstance().getReference().child("User_Info").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String key;

                    all_user_info.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Member_ArrayList member_arrayList = snapshot.getValue(Member_ArrayList.class);

                        if (member_arrayList.user_UID.equals(App.user_UID_get())) {

                            my_info = member_arrayList;

                        } else {

                            all_user_info.add(member_arrayList);

                        }

                    }

                    Set set = my_info.user_following.entrySet();

                    Iterator iterator = set.iterator();

                    following_user_info.clear();

                    while (iterator.hasNext()) {

                        Map.Entry entry = (Map.Entry) iterator.next();

                        key = (String) entry.getKey();

                        following_user_info.add(dataSnapshot.child(key).getValue(Member_ArrayList.class));

                    }


                    search_user_info.clear();

                    search_user_info.addAll(following_user_info);

                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //틀 생성
        @Override
        public Home_02_Following_Adapter.home_02_follower_re onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_02_follower, parent, false);

            return new Home_02_Following_Adapter.home_02_follower_re(view);

        }

        //묶어주기
        @Override
        public void onBindViewHolder(final home_02_follower_re holder, final int position) {

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(Home_02_following.this);

            mGlideRequestManager.load(search_user_info.get(position).user_profile).into(holder.user_profile);
            holder.user_nick.setText(search_user_info.get(position).getUser_nick());
            holder.user_id.setText(search_user_info.get(position).getMember_id());

            opponent_uid = search_user_info.get(position).user_UID;

            holder.user_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //팔로우
                    onFollowerClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(opponent_uid));

                    //팔로잉
                    onFollowingClicked(FirebaseDatabase.getInstance().getReference().child("User_Info").child(App.user_UID_get()));

                }
            });

            //리싸이클러뷰 글쓴이 정보 파이어베이스에서 가져오기
            FirebaseDatabase.getInstance().getReference("User_Info").child(App.user_UID_get()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Member_ArrayList temp = new Member_ArrayList();

                    temp = dataSnapshot.getValue(Member_ArrayList.class);

                    Log.d("체크", "0차 관문");

//                    if(temp.user_following.size() > position) {

                        Log.d("체크", "1차 관문");

                        if (temp.user_following.containsKey(search_user_info.get(position).user_UID)) {


                            Log.d("체크", "2차 관문");

                            holder.user_follow.setSelected(true);
                            holder.user_follow.setTextColor(Color.parseColor("#e47700"));
                            holder.user_follow.setText("팔로잉");

                        } else {


                            Log.d("체크", "3차 관문");

                            holder.user_follow.setSelected(false);
                            holder.user_follow.setTextColor(Color.parseColor("#FFFFFF"));
                            holder.user_follow.setText("팔로우");

                        }

                    }

//                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //미니홈피 가기
            holder.click_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Home_02_following.this, Home_02_Others.class);
                    intent.putExtra("user_uid", search_user_info.get(position).user_UID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                }
            });

        }


        //현재 위치
        @Override
        public int getItemCount() {
            return search_user_info.size();
        }

        public class home_02_follower_re extends RecyclerView.ViewHolder {

            ImageView user_profile;
            TextView user_nick;
            TextView user_id;
            Button user_follow;
            CardView click_item;

            home_02_follower_re(View view) {
                super(view);
                user_profile = view.findViewById(R.id.home_02_follower_profile);
                user_nick = view.findViewById(R.id.home_02_follower_nick);
                user_id = view.findViewById(R.id.home_02_follower_id);
                user_follow = view.findViewById(R.id.home_02_follower_B);
                click_item = view.findViewById(R.id.home_02_follower_cardview);

            }

        }

        //검색 아이템
        void filter(String searchText) {
            search_user_info.clear();

            if(searchText.length() == 0) {
                search_user_info.addAll(following_user_info);
            }
            else {

                for(Member_ArrayList item : all_user_info) {

                    if(item.getUser_nick().contains(searchText)) {

                        search_user_info.add(item);

                    }
                }
            }

            notifyDataSetChanged();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();

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

                if (home_03_user_following.user_following.containsKey(opponent_uid)) {
                    // Unstar the post and remove self from stars
                    home_03_user_following.user_following.remove(opponent_uid);
                } else {
                    // Star the post and add self to stars
                    home_03_user_following.user_following.put(opponent_uid, true);
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



}
