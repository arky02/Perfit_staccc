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
    Button btn_ok, btn_rePic;
    int isPick;
    final int PICH_ME = 1;
    final int PICK_OTHER = 2;

    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_adjust);

        mySurfaceView = findViewById(R.id.surfaceview);
        btn_ok = findViewById(R.id.btn_ok);
        btn_rePic = findViewById(R.id.btn_rePic);

        Intent intent = getIntent();
        humanSkeleton = intent.getParcelableExtra("skeleton");
        isPick = intent.getIntExtra("CameraChoose", 0);
        Log.e("camera", ""+isPick);
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
                finish();
            }
        });

        btn_rePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (isPick) {
                    case PICH_ME :
                        Intent intent = new Intent(ModelAdjustActivity.this, CameraActivity.class);
                        intent.putExtra("CameraChoose", 1);
                        startActivity(intent);
                        finish();
                        break;
                    case PICK_OTHER :
                        Intent intent1 = new Intent(ModelAdjustActivity.this, CameraActivity.class);
                        intent1.putExtra("CameraChoose", 2);
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        });
    }
}
