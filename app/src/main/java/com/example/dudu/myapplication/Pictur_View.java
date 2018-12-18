package com.example.dudu.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pictur_View extends AppCompatActivity {

    @BindView(R.id.picture_view_image)
    ImageView picture_view_image;

    RequestManager requestManager;

    @BindView(R.id.picture_view_progress)
    ProgressBar picture_view_progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view);
        ButterKnife.bind(this);

        requestManager = Glide.with(this);

        String picture = getIntent().getStringExtra("picture");

        picture_view_progress.setVisibility(View.VISIBLE);
        requestManager.load(picture).fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        picture_view_progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(picture_view_image);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}
