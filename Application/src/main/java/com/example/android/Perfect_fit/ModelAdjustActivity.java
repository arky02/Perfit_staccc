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
    HumanSkeleton data;

    double origin_Height, origin_LShoulderToElbow, origin_RShoulderToElbow, origin_LElbowToWrist, origin_RElbowToWrist,
            origin_shoulderWidth, origin_LAnkleToknee, origin_RAnkleToknee, origin_LKneeToHip, origin_RKneeToHip, origin_bodyDistance;
    double origin_leg, origin_arm, origin_shoulder, distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_adjust);

        mySurfaceView = findViewById(R.id.surfaceview);
        btn_ok = findViewById(R.id.btn_ok);
        btn_rePic = findViewById(R.id.btn_rePic);

        Intent intent = getIntent();
        humanSkeleton = intent.getParcelableExtra("skeleton");
        origin_Height = Double.parseDouble(getIntent().getStringExtra("height"));
        data = intent.getParcelableExtra("skeleton");
        isPick = intent.getIntExtra("CameraChoose", 0);
        mySurfaceView.setHumanSkeleton(humanSkeleton);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = mySurfaceView.getDistance();

                //진짜 길이 구하기
                origin_LShoulderToElbow = getOrigin(mySurfaceView.getLShoulderToElbow());
                origin_bodyDistance = getOrigin(mySurfaceView.getbodyDistance());
                origin_RShoulderToElbow = getOrigin(mySurfaceView.getRShoulderToElbow());
                origin_LAnkleToknee = getOrigin(mySurfaceView.getLAnkleToknee());
                origin_RAnkleToknee = getOrigin(mySurfaceView.getRAnkleToknee());
                origin_LElbowToWrist = getOrigin(mySurfaceView.getLElbowToWrist());
                origin_RElbowToWrist = getOrigin(mySurfaceView.getRElbowToWrist());
                origin_LKneeToHip = getOrigin(mySurfaceView.getLKneeToHip());
                origin_RKneeToHip = getOrigin(mySurfaceView.getRKneeToHip());
                origin_shoulderWidth = getOrigin(mySurfaceView.getShoulderWidth());

                origin_leg = origin_LAnkleToknee + origin_LKneeToHip;
                origin_arm = origin_LElbowToWrist + origin_LShoulderToElbow;
                origin_shoulder = origin_shoulderWidth;

                Intent intent1 = new Intent(ModelAdjustActivity.this, FinalActivity.class);
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("height", getIntent().getStringExtra("height"));
                intent1.putExtra("armDistance", origin_arm);
                intent1.putExtra("legDistance", origin_leg);
                intent1.putExtra("shoulderWidth", origin_shoulder);
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
    public double getOrigin(double X) {
        return (origin_Height * X)/distance;
    }

    public HumanSkeleton.Point getCenter(HumanSkeleton.Point a, HumanSkeleton.Point b) {
        HumanSkeleton.Point point = new HumanSkeleton.Point(0, 0);
        point.x = (a.x + b.x)/2;
        point.y = (a.y + b.y)/2;
        return point;
    }

    public double getDistance (HumanSkeleton.Point a, HumanSkeleton.Point b) {
        return Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
    }
}
