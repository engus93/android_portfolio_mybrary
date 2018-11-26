package com.example.dudu.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class App extends Application {

    static String User_ID; //현재 로그인 아이디

    static Gson gson = new Gson();
    static Type collectionTypeMember = new TypeToken<HashMap<String,Member_ArrayList>>(){}.getType();


    static ArrayList<Home_02_02_ArrayList> list = new ArrayList<>();    //교체용
    static ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList = new ArrayList<>();    //어레이리스트
    static Type collectionTypeMyBrary = new TypeToken<HashMap<String,Home_02_02_ArrayList>>(){}.getType();  //타입 분류

    static Type collectionTypeHeart = new TypeToken<HashMap<String,Home_05_ArrayList>>(){}.getType();

    static Type collectionTypeString = new TypeToken<HashMap<String,String>>(){}.getType();




    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }

    static void mybrary_sort(){

        App.list.clear();

        for (int i = App.home_02_02_ArrayList.size(); 0 < i; i--) {

            App.list.add(App.home_02_02_ArrayList.get(i - 1));

        }

        App.home_02_02_ArrayList.clear();

        for (int i = 0; i < App.list.size(); i++) {

            Log.d("체크", "잘 넣음");

            App.home_02_02_ArrayList.add(App.list.get(i));

        }

    }
}
