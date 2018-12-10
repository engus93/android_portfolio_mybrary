package com.example.dudu.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Home_05_Adapter extends RecyclerView.Adapter<Home_05_Adapter.Home_Heart_ViewHolder>  {

    String key; //푸쉬 키 찾기

    Context context;

    private ArrayList<Home_05_ArrayList> heart_book_ArrayList;

    //글라이드 오류 방지
    public RequestManager mGlideRequestManager;

    Home_05_Adapter(Context context,ArrayList<Home_05_ArrayList> heartInfoArrayList){
        this.context = context;
        this.heart_book_ArrayList = heartInfoArrayList;
    }

    @Override
    public Home_Heart_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_05_re, parent, false);

        return new Home_Heart_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Home_Heart_ViewHolder holder, final int position) {

        //글라이드 오류 방지
        mGlideRequestManager = Glide.with(context);

        mGlideRequestManager.load(heart_book_ArrayList.get(position).heart_book).fitCenter().into(holder.heart_book_image);
        holder.heart_book_name.setText(heart_book_ArrayList.get(position).heart_name);
        holder.heart_book_author.setText(heart_book_ArrayList.get(position).heart_author);
        holder.heart_book_price.setText(heart_book_ArrayList.get(position).heart_price);
        holder.heart_book_date.setRating((float)heart_book_ArrayList.get(position).heart_rank);

        holder.click_heart_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.heart_heart:
                        holder.click_heart_heart.setSelected(true);
                        break;
                }

//                찜목록 정렬
//                App.heart_sort();

                Toast.makeText(context, heart_book_ArrayList.get(position).heart_name + "가 찜목록에 삭제 되었습니다.", Toast.LENGTH_SHORT).show();

                //어댑터에서 삭제
                key = heart_book_ArrayList.get(position).getUser_key();

                //파이어베이스 데이터베이스 선언
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("Users_Like_Book").child(App.user_UID_get());

                //리싸이클러뷰 파이어베이스 업데이트
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Home_05_ArrayList home_05_arrayList = new Home_05_ArrayList();
                            home_05_arrayList = snapshot.getValue(Home_05_ArrayList.class);

                            Log.d("체크", "삭제 전");

                            if (home_05_arrayList.getUser_key().equals(key)) {

                                Log.d("체크", "삭제 진입");

                                myRef.child(home_05_arrayList.getUser_key()).removeValue();

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return;
            }
        });

//                // 어댑터에서 RecyclerView에 반영하도록 합니다.
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, heart_book_ArrayList.size());

//                //---------------------------------쉐어드-----------------------------------------
//
//                //쉐어드 생성
//                SharedPreferences saveMember_info = context.getSharedPreferences("Heart", MODE_PRIVATE);
//                SharedPreferences.Editor save = saveMember_info.edit();
//
//                //해쉬맵 생성
//                HashMap<String, Home_05_ArrayList> heart_map = new HashMap<>();
//
//                //정보 -> 해쉬맵에 삽입
//                for (int i = 0; i < App.heart_book_ArrayList.size(); i++) {
//
//                    heart_map.put(App.User_ID + "_Heart_" + i, App.heart_book_ArrayList.get(i));
//
//                }
//
//                save.clear();
//
//                //해쉬맵(Gson 변환) -> 쉐어드 삽입
//                save.putString(App.User_ID + "_Heart", App.gson.toJson(heart_map));
//
//                //저장
//                save.apply();

                //찜목록 정렬
//                App.heart_sort();

            }

    @Override
    public int getItemCount() {
        return heart_book_ArrayList.size();
    }

    public static class Home_Heart_ViewHolder extends RecyclerView.ViewHolder {
        ImageView heart_book_image;
        TextView heart_book_name;
        TextView heart_book_author;
        TextView heart_book_price;
        RatingBar heart_book_date;

        //클릭 변수
        CardView click_item;
        ImageButton click_heart_heart;

        Home_Heart_ViewHolder(View view){
            super(view);
            heart_book_image = view.findViewById(R.id.heart_book);
            heart_book_name = view.findViewById(R.id.heart_name);
            heart_book_author = view.findViewById(R.id.heart_author);
            heart_book_price = view.findViewById(R.id.heart_price);
            heart_book_date = view.findViewById(R.id.heart_star);
            click_heart_heart = view.findViewById(R.id.heart_heart);
        }
    }

}
