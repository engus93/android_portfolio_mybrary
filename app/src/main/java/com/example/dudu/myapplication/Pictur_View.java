package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pictur_View extends AppCompatActivity {

    @BindView(R.id.picture_view_image)
    ImageView picture_view_image;

    RequestManager requestManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view);
        ButterKnife.bind(this);

        requestManager = Glide.with(this);

        String picture = getIntent().getStringExtra("picture");

        requestManager.load(picture).fitCenter().into(picture_view_image);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}
