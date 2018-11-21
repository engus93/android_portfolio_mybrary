package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Search_01 extends AppCompatActivity {

    ImageButton search_back_B;

    protected void onCreate(Bundle savedInstancState){
        super.onCreate(savedInstancState);
        setContentView(R.layout.search_01);

        search_back_B = findViewById(R.id.search_back_B);
        search_back_B.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                finish();

            }
        });

        //리싸이클러뷰

        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.search_01_RE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<Search_01_ArrayList> search_book_ArrayList = new ArrayList<>();
        final Search_01_Adapter myAdapter = new Search_01_Adapter(getApplicationContext(),search_book_ArrayList);

        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_01, "골든아워 1", "이국종", 1.5));
        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_02,  "그래서 하고 싶은 말이 뭔데?", "다케우치 가오루", 3.0));
        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_03,  "룬의 아이들 1", "전민희", 4.5));
        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_04,  "트렌드 코리아 2019", "김난도 전미영 이향은 이준영 등", 5.0));
        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_05,  "참을 수 없는 존재의 가벼움", "밀란 쿤데라", 2.0));
        search_book_ArrayList.add(new Search_01_ArrayList(R.drawable.book_06,  "처음부터 잘 쓰는 사람은 없습니다", "이다혜", 1.0));



        mRecyclerView.setAdapter(myAdapter);




    }

}
