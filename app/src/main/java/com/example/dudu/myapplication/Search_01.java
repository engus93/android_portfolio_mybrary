package com.example.dudu.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.tools.jsc.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Search_01 extends AppCompatActivity {

    ImageButton search_back_B;
    EditText search_searchbar;
    Button search_button;

    RecyclerView mRecyclerView;
    TextView search_nothing;

    String search_word;

    String search_url = "http://book.interpark.com/api/search.api?key=9A0ACD60A50795084682869204DE13D2A6A3FAB4767E8869BD4C8340C8F61FAC&output=json&queryType=title&sort=accuracy&maxResults=30&query=";

    protected void onCreate(Bundle savedInstancState){
        super.onCreate(savedInstancState);
        setContentView(R.layout.search_01);

        search_searchbar = findViewById(R.id.search_01_searchbar);
        search_button = findViewById(R.id.search_01_searchbutton);
        search_back_B = findViewById(R.id.search_back_B);
        mRecyclerView = findViewById(R.id.search_01_RE);
        search_nothing = findViewById(R.id.search_nothing);

        search_back_B.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                finish();

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_word = search_searchbar.getText().toString();

                //키보드 내리기
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                if (search_word.length() > 0) {
                    new GetBookSearchTask().execute();
                } else {
                    MainActivity.showToast(Search_01.this, "글자를 입력해주세요.");
                }
            }
        });

    }

    protected void onResume() {

        super.onResume();

        Log.d("체크", "찜 후");



    }

    //베스트셀러
    private class GetBookSearchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;
            HttpURLConnection urlConnection = null;
            BufferedInputStream buf = null;

            try {
                //[URL 지정과 접속]

                //웹서버 URL 지정
                url = new URL(search_url + search_word);

                //URL 접속
                urlConnection = (HttpURLConnection) url.openConnection();

                //[웹문서 소스를 버퍼에 저장]
                //데이터를 버퍼에 기록

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                String page = "";

                //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }

                //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
                JSONObject json = new JSONObject(page);

                //item 에 해당하는 배열을 할당
                JSONArray jArr = json.getJSONArray("item");

                App.search_book_ArrayList.clear();

                //배열의 크기만큼 반복하면서, name과 address의 값을 추출함
                for (int i = 0; i < jArr.length(); i++) {
                    //i번째 배열 할당
                    json = jArr.getJSONObject(i);

                    //책 데이터 추출
                    String title = json.getString("title");
                    String coverLargeUrl = json.getString("coverLargeUrl");
                    String author = json.getString("author");
                    String price = String.valueOf(json.getInt("priceStandard"));
                    double star = json.getDouble("customerReviewRank")/2.0;
                    String book_main = json.getString("description");
                    String book_link = json.getString("mobileLink");
                    String book_publisher = json.getString("publisher");
                    String date = json.getString("pubDate");

                    App.search_book_ArrayList.add(new Search_01_ArrayList(coverLargeUrl, title, author, price + "원", book_publisher, date , star, book_main, book_link));

                }


            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //리싸이클러뷰
            RecyclerView.LayoutManager mLayoutManager;

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(Search_01.this);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            final Search_01_Adapter myAdapter = new Search_01_Adapter(Search_01.this, App.search_book_ArrayList);

            mRecyclerView.setAdapter(myAdapter);

            if(App.search_book_ArrayList.size() == 0) {
                search_nothing.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }else {
                mRecyclerView.setVisibility(View.VISIBLE);
                search_nothing.setVisibility(View.GONE);
            }

        }
    }

}
