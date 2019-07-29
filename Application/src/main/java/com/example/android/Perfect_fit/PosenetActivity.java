package com.example.android.Perfect_fit;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class PosenetActivity extends AppCompatActivity {

    TextView tv_count;
    CountDownTimer mCountDown = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posenet);

        tv_count = findViewById(R.id.tv_count);

        Intent intent = getIntent();
        String url = intent.getStringExtra("img");

        RequestHttpURLConnection requestPosenet = new RequestHttpURLConnection();
        requestPosenet.request(url);
        Log.d("checkkk", "posetnet ok!");

        CountDown();

    }

    public void CountDown() {
        mCountDown = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_count.setText(Html.fromHtml(Long.toString(millisUntilFinished / 1000L + 1)));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
