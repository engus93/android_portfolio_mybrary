package com.example.dudu.myapplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

  static String User_ID; //현재 로그인 아이디
  static String Login_User_Profile;

//    static String uid;

  // 비밀번호 정규식
  static Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,15}$");

  static Gson gson = new Gson();  //Gson 선언
  static Type collectionTypeMember = new TypeToken<HashMap<String,Member_ArrayList>>(){}.getType();   //회원 가입 타입 분류


  static ArrayList<Home_02_02_ArrayList> list = new ArrayList<>();    //교체용
  static ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList = new ArrayList<>();    //어레이리스트
  static Type collectionTypeMyBrary = new TypeToken<HashMap<String,Home_02_02_ArrayList>>(){}.getType();  //타입 분류

  static ArrayList<Home_05_ArrayList> list_01 = new ArrayList<>();    //교체용
  static ArrayList<Home_05_ArrayList> heart_book_ArrayList = new ArrayList<>();   //찜목록
  static Type collectionTypeHeart = new TypeToken<HashMap<String,Home_05_ArrayList>>(){}.getType();   //찜목록 타입 분류

  static ArrayList<Home_02_02_ArrayList> home_03_ArrayList = new ArrayList<>();    //피드 모든 게시물 어레이리스트

  static ArrayList<Member_ArrayList> all_userslist = new ArrayList<>();      //현재 가입 유저

  static ArrayList<Home_04_ChatRoom_Model> user_chat_room = new ArrayList<>();   //모든 채팅방 리스트

  static Type collectionTypeString = new TypeToken<HashMap<String,String>>(){}.getType(); //일반 스트링 타입 분류

  static ArrayList<Search_01_ArrayList> search_book_ArrayList = new ArrayList<>();    //검색용 어레이 리스트

  static ArrayList<Search_01_ArrayList> search_best_book_info_ArrayList = new ArrayList<>();  //검색창 처음에 베스트셀러

  static Search_01_ArrayList chat_bot_search_book;  //챗봇 베스트셀러
  static ArrayList<Search_01_ArrayList> chat_bot_recommendbook = new ArrayList<>();  //챗봇 추천도서
  static ArrayList<Search_01_ArrayList> chat_bot_newbook = new ArrayList<>();  //챗봇 신간도서

  static String my_nick;

  static String home_02_01_temp_main;

  static Boolean Regeneration = false;
  static Home_02_02_ArrayList Regeneration_list = new Home_02_02_ArrayList();

  static Boolean error_skip = true;


  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("mybrary.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build());

    FirebaseDatabase.getInstance().setPersistenceEnabled(true);

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

//  static void heart_sort(){
//
//
//    App.list_01.clear();
//
//    for (int i = App.heart_book_ArrayList.size(); 0 < i; i--) {
//
//      App.list_01.add(App.heart_book_ArrayList.get(i - 1));
//
//    }
//
//    App.heart_book_ArrayList.clear();
//
//    for (int i = 0; i < App.list_01.size(); i++) {
//
//      Log.d("체크", "잘 넣음");
//
//      App.heart_book_ArrayList.add(App.list_01.get(i));
//
//    }
//
//  }

  //비트맵 -> 스트링 변환
  static String getBase64String(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

    byte[] imageBytes = byteArrayOutputStream.toByteArray();

    return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
  }

  //스트링 -> 비트맵 전환
  static Bitmap getBitmap(String zz) {

    byte[] decodedByteArray = Base64.decode(zz, Base64.NO_WRAP);
    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

    return decodedBitmap;
  }

  static String user_UID_get(){

    //해당 UID 캐치
    FirebaseUser user;
    user = FirebaseAuth.getInstance().getCurrentUser();

    String uid = user.getUid();

    return uid;

  }
}
