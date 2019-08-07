package com.example.android.Perfect_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout button_name;
    HumanSkeleton data;
    double origin_Height, origin_LShoulderToElbow, origin_RShoulderToElbow, origin_LElbowToWrist, origin_RElbowToWrist,
            origin_shoulderWidth, origin_LAnkleToknee, origin_RAnkleToknee, origin_LKneeToHip, origin_RKneeToHip, origin_bodyDistance;
    double origin_leg, origin_arm, distance;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);
        tv = findViewById(R.id.tv);

        Intent mintent = getIntent();
        data = mintent.getParcelableExtra("skeleton");
        origin_Height = Double.parseDouble(getIntent().getStringExtra("height"));
        distance = mintent.getDoubleExtra("distance",0.0);
        Log.d("distance check", ""+distance);

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

        tv.setText("다리길이 : " + (origin_RKneeToHip + origin_RAnkleToknee));

        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyModelActivity.class);
                intent.putExtra("height",getIntent().getStringExtra("height"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
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
