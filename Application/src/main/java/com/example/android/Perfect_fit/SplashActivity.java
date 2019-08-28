package com.example.android.Perfect_fit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    VideoView videoView;
    Boolean isFinish = false;
    Boolean ifModelExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        videoView = findViewById(R.id.videoView);
        tv = findViewById(R.id.textView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_color);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, ModelCreateActivity.class));
//                finish();
//            }
//        }, 1500);


    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        fadeAnimation(tv);

        if(isFinish) {

            //TODO 튜토리얼 intent, 모델 exist boolean 만들기
            if(!ifModelExist){
                //startActivity(new Intent(this, 튜토리얼.class));
            }else{
                startActivity(new Intent(this, MainActivity.class));
            }
            //TODO End

            finish();
        }
    }

    public void fadeAnimation(final View tv) {

        final Animation animationFade;

        tv.setAlpha(0f);

        animationFade = AnimationUtils.loadAnimation(this, R.anim.fadein);


        Handler mhandler = new Handler();

        mhandler.postDelayed(new Runnable() {

            @Override

            public void run() {

                // TODO Auto-generated method stub

                tv.setAlpha(1f);
                tv.startAnimation(animationFade);
            }

        }, 0);

        isFinish = true;

    }
}
