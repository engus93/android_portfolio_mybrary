package com.example.dudu.myapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class App extends Application {

    static String User_ID; //현재 로그인 아이디

    static Gson gson = new Gson();
    static Type collectionTypeMember = new TypeToken<HashMap<String,Member_ArrayList>>(){}.getType();
    static Type collectionTypeMyBrary = new TypeToken<HashMap<String,Home_02_02_ArrayList>>(){}.getType();
    static Type collectionTypeHeart = new TypeToken<HashMap<String,Home_05_ArrayList>>(){}.getType();

    static Type collectionTypeString = new TypeToken<HashMap<String,String>>(){}.getType();


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);




    }
}
