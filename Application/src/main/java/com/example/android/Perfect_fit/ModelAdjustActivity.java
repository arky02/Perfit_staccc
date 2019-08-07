package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ModelAdjustActivity extends AppCompatActivity {

    private HumanSkeleton humanSkeleton;
    com.example.android.Perfect_fit.MySurfaceView mySurfaceView;
    Button btn_ok;

    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_adjust);

        mySurfaceView = findViewById(R.id.surfaceview);
        btn_ok = findViewById(R.id.btn_ok);

        Intent intent = getIntent();
        humanSkeleton = intent.getParcelableExtra("skeleton");
        mySurfaceView.setHumanSkeleton(humanSkeleton);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = mySurfaceView.getDistance();

                Intent intent1 = new Intent(ModelAdjustActivity.this, MainActivity.class);
                intent1.putExtra("name",getIntent().getStringExtra("name"));
                intent1.putExtra("height",getIntent().getStringExtra("height"));
                intent1.putExtra("distance",distance);
                intent1.putExtra("skeleton", getIntent().getParcelableExtra("skeleton"));
                startActivity(intent1);
            }
        });

    }
}
