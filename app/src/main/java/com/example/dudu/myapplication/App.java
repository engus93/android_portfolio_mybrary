package com.example.dudu.myapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class App extends Application {

    static Gson gson = new Gson();
    static Type collectionTypeMember = new TypeToken<HashMap<String,Member_ArrayList>>(){}.getType();

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);




    }
}
