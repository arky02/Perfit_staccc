package com.peftif.android.Perfect_fit;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.peftif.android.Perfect_fit.ModelData.Data_model;
import com.peftif.android.Perfect_fit.ModelData.DatabaseHelper;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    TextView tv;
    VideoView videoView;
    Boolean isFinish = false;
    Boolean ifModelExist;
    List<Data_model> datamodel;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);
        tv = findViewById(R.id.tv);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_splash);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
//                startNextActivity();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                datamodel = databaseHelper.getdata();

                Log.e("check", datamodel.toString());

                if(datamodel.toString() == "[]") {
                    Intent intent = new Intent(getApplicationContext(), ModelCreateActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setBackgroundColor(Color.TRANSPARENT);

                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video started; hide the placeholder.
//                            FrameLayout placeholder = findViewById(R.id.placeholder);
                            FrameLayout placeholder = findViewById(R.id.placeholder);
                            placeholder.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }

                });
            }
        });
    }
}


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, ModelCreateActivity.class));
//                finish();
//            }
//        }, 1500);





//    private void startNextActivity() {
//        if (isFinishing())
//            return;
//        fadeAnimation(tv);
//
//        if(isFinish) {
//
//            //TODO 튜토리얼 intent, 모델 exist boolean 만들기
//            if(!ifModelExist){
//                //startActivity(new Intent(this, 튜토리얼.class));
//            }else{
//                startActivity(new Intent(this, MainActivity.class));
//            }
//            //TODO End
//
//            finish();
//        }
//    }
//
//    public void fadeAnimation(final View tv) {
//
//        final Animation animationFade;
//
//        tv.setAlpha(0f);
//
//        animationFade = AnimationUtils.loadAnimation(this, R.anim.fadein);
//
//
//        Handler mhandler = new Handler();
//
//        mhandler.postDelayed(new Runnable() {
//
//            @Override
//
//            public void run() {
//
//                // TODO Auto-generated method stub
//
//                tv.setAlpha(1f);
//                tv.startAnimation(animationFade);
//            }
//
//        }, 0);
//
//        isFinish = true;
//
//    }


