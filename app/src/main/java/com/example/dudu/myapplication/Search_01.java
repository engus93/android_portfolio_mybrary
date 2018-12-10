package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Search_01 extends AppCompatActivity {

    ImageButton search_back_B;

    protected void onCreate(Bundle savedInstancState){
        super.onCreate(savedInstancState);
        setContentView(R.layout.search_01);

        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_01.png?alt=media&token=8e9a5582-85ed-4eb9-93cb-05d0e7a0f4e3", "골든아워 1", "이국종","15,600원", 1.5));
        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_02.png?alt=media&token=05509ea8-b878-475e-ad64-dab38d627ff6",  "그래서 하고 싶은 말이 뭔데?", "다케우치 가오루", "158,00원", 3.0));
        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_03.png?alt=media&token=a0d2cdde-d5e2-4cc9-8a53-f1a703406e0e",  "룬의 아이들 1", "전민희", "158,00원", 4.5));
        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_04.png?alt=media&token=e2ab7237-cd04-48f2-a20c-c018cce1c105",  "트렌드 코리아 2019", "김난도 전미영 이향은 이준영 등", "158,00원", 5.0));
        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_05.png?alt=media&token=ff93a575-4147-45ef-b5f8-302aa4163a1e",  "참을 수 없는 존재의 가벼움", "밀란 쿤데라", "158,00원",2.0));
        App.search_book_ArrayList.add(new Search_01_ArrayList("https://firebasestorage.googleapis.com/v0/b/mybrary-4084f.appspot.com/o/MyBrary%2FDefault%2Fbook_06.png?alt=media&token=19c70ab5-0f5a-4dcc-bb26-ad65ce5d60ae",  "처음부터 잘 쓰는 사람은 없습니다", "이다혜", "158,00원", 1.0));

        search_back_B = findViewById(R.id.search_back_B);
        search_back_B.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    protected void onResume() {

        super.onResume();

        Log.d("체크", "찜 후");

        //리싸이클러뷰

        final RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.search_01_RE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        final ArrayList<Search_01_ArrayList> search_book_ArrayList = new ArrayList<>();
        final Search_01_Adapter myAdapter = new Search_01_Adapter(Search_01.this, App.search_book_ArrayList);



        mRecyclerView.setAdapter(myAdapter);

    }

}
