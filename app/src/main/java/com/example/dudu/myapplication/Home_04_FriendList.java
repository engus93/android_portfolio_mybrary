package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_04_FriendList extends AppCompatActivity {


    ImageButton home_04_friend_send; //채팅방 만들기
    ImageButton home_04_friendlist_back_B;  //뒤로가기 버튼

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_04_friendlist);


        home_04_friend_send = findViewById(R.id.home_04_friend_send);
        home_04_friendlist_back_B = findViewById(R.id.home_04_friendlist_back_B);

        //다중 사용자 채팅시
        home_04_friend_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //뒤로가기
        home_04_friendlist_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

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

        //리싸이클러뷰 파이어베이스 업데이트
        FirebaseDatabase.getInstance().getReference("User_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                App.all_userslist.clear();

                Member_ArrayList user_list = new Member_ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user_list = snapshot.getValue(Member_ArrayList.class);
                    System.out.println(user_list.user_UID);
                    if(!(user_list.user_UID.equals(App.user_UID_get()))){
                        Log.d("체크", "친구 추가 되야 함");
                        App.all_userslist.add(user_list);
                    }
                }

                Home_04_Friend_Adapter myAdapter = new Home_04_Friend_Adapter(getApplicationContext(), App.all_userslist);
                mRecyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
