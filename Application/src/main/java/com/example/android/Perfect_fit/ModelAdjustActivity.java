package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.Perfect_fit.Camera.CameraActivity;
import com.example.android.Perfect_fit.PoseEstimation.HumanSkeleton;

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
                origin_LShoulderToElbow = getOrigin(getDistance(data.getLeftshoulder(), data.getLeftelbow()));
                origin_bodyDistance = getOrigin(getDistance(getCenter(data.getLefthip(), data.getRighthip()), getCenter(data.getLeftshoulder(), data.getRightshoulder())));
                origin_RShoulderToElbow = getOrigin(getDistance(data.getRightshoulder(), data.getRightelbow()));
                origin_LAnkleToknee = getOrigin(getDistance(data.getLeftankle(), data.getLeftknee()));
                origin_RAnkleToknee = getOrigin(getDistance(data.getRightankle(), data.getRightknee()));
                origin_LElbowToWrist = getOrigin(getDistance(data.getLeftelbow(), data.getLeftwrist()));
                origin_RElbowToWrist = getOrigin(getDistance(data.getRightelbow(), data.getRightwrist()));
                origin_LKneeToHip = getOrigin(getDistance(data.getLeftknee(), data.getLefthip()));
                origin_RKneeToHip = getOrigin(getDistance(data.getRightknee(), data.getRighthip()));
                origin_shoulderWidth = getOrigin(getDistance(data.getLeftshoulder(), data.getRightshoulder()));

                origin_leg = origin_LAnkleToknee + origin_LKneeToHip;
                origin_arm = origin_LElbowToWrist + origin_LShoulderToElbow;
                origin_shoulder = origin_shoulderWidth;

                Intent intent1 = new Intent(ModelAdjustActivity.this, ModifyModelActivity.class);
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
