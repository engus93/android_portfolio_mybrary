package com.example.dudu.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Opening_Activity extends AppCompatActivity {

    @BindView(R.id.animationView)
    LottieAnimationView lottieAnimationView;

    Boolean check = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new Start_ActivityTast().execute();

        lottieAnimationView.setAnimation("books.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();

    }

    //베스트셀러
    private class Start_ActivityTast extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(check) {

                Intent intent = new Intent(Opening_Activity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);

            }

            return null;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }

    @Override
    public void onBackPressed() {

        check = false;

        Intent intent = new Intent(Opening_Activity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);

    }
}
