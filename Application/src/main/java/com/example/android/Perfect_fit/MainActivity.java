package com.example.android.Perfect_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static class Point {
        public double x = 0;
        public double y = 0;
    }

    LinearLayout button_name;
    TextView text;
    HumanSkeleton data;
    double height, LShoulderToElbow, RShoulderToElbow, LElbowToWrist, RElbowToWrist, shoulderWidth, LAnkleToknee, RAnkleToknee, LKneeToHip, RKneeToHip, bodyDistance;
    double origin_Height, origin_LShoulderToElbow, origin_RShoulderToElbow, origin_LElbowToWrist, origin_RElbowToWrist, origin_shoulderWidth, origin_LAnkleToknee, origin_RAnkleToknee, origin_LKneeToHip, origin_RKneeToHip, origin_bodyDistance;
    double origin_leg, origin_arm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);
        text = findViewById(R.id.text_test);

        Intent mintent = getIntent();
        if(mintent.getStringExtra("height") != null) {
            origin_Height = Double.parseDouble(mintent.getStringExtra("height"));
        }
        Log.d("kk", Double.toString(origin_Height));

        Intent intent = getIntent();
        String posenetData = intent.getStringExtra("posenet");
        try {
            data = new HumanSkeleton(posenetData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        부위별 길이 구하기

        LShoulderToElbow = getDistance(data.getLeftshoulder().x , data.getLeftelbow().x, data.getLeftshoulder().y , data.getLeftelbow().y);
        RShoulderToElbow = getDistance(data.getRightshoulder().x , data.getRightelbow().x, data.getRightshoulder().y , data.getRightelbow().y);
        LElbowToWrist = getDistance(data.getLeftelbow().x, data.getLeftwrist().x, data.getLeftelbow().y, data.getLeftwrist().y);
        RElbowToWrist = getDistance(data.getRightelbow().x, data.getRightwrist().x, data.getRightelbow().y, data.getRightwrist().y);
        shoulderWidth = getDistance(data.getLeftshoulder().x, data.getRightshoulder().x, data.getLeftshoulder().y, data.getRightshoulder().y);
        LAnkleToknee = getDistance(data.getLeftankle().x, data.getLeftknee().x, data.getLeftankle().y, data.getLeftknee().y);
        RAnkleToknee = getDistance(data.getRightankle().x, data.getRightknee().x, data.getRightankle().y, data.getRightknee().y);
        LKneeToHip = getDistance(data.getLeftknee().x, data.getLefthip().x, data.getLeftknee().y, data.getLefthip().y);
        RKneeToHip = getDistance(data.getRightknee().x, data.getRighthip().x, data.getRightknee().y, data.getRighthip().y);
        bodyDistance = getDistance(getCenter(data.getLefthip().x, data.getRighthip().x, data.getLefthip().y, data.getRighthip().y).x,
                getCenter(data.getLeftshoulder().x, data.getRightshoulder().x, data.getLeftshoulder().y, data.getRightshoulder().y).x,
                getCenter(data.getLefthip().x, data.getRighthip().x, data.getLefthip().y, data.getRighthip().y).y,
                getCenter(data.getLeftshoulder().x, data.getRightshoulder().x, data.getLeftshoulder().y, data.getRightshoulder().y).y);

        //키 : 발목부터 눈까지 + 눈부터 목까지
        height = getDistance(getCenter(data.getLeftankle().x, data.getRightankle().x, data.getLeftankle().y, data.getRightankle().y).x,
                getCenter(data.getLeftteye().x, data.getRighteye().x, data.getLeftteye().y, data.getRighteye().y).x,
                getCenter(data.getLeftankle().x, data.getRightankle().x, data.getLeftankle().y, data.getRightankle().y).y,
                getCenter(data.getLeftteye().x, data.getRighteye().x, data.getLeftteye().y, data.getRighteye().y).y) +
                getDistance(data.getNeck().x, getCenter(data.getLeftteye().x, data.getRighteye().x, data.getLeftteye().y, data.getRighteye().y).x,
                        data.getNeck().y, getCenter(data.getLeftteye().x, data.getRighteye().x, data.getLeftteye().y, data.getRighteye().y).y);

        //진짜 길이 구하기
        origin_LShoulderToElbow = getOrigin(LShoulderToElbow);
        origin_bodyDistance = getOrigin(bodyDistance);
        origin_RShoulderToElbow = getOrigin(RShoulderToElbow);
        origin_LAnkleToknee = getOrigin(LAnkleToknee);
        origin_RAnkleToknee = getOrigin(RAnkleToknee);
        origin_LElbowToWrist = getOrigin(LElbowToWrist);
        origin_RElbowToWrist = getOrigin(RElbowToWrist);
        origin_LKneeToHip = getOrigin(LKneeToHip);
        origin_RKneeToHip = getOrigin(RKneeToHip);
        origin_shoulderWidth = getOrigin(shoulderWidth);
        origin_arm = getAve(origin_LShoulderToElbow + origin_LElbowToWrist, origin_RShoulderToElbow + origin_LElbowToWrist);
        origin_leg = getAve(origin_RAnkleToknee + origin_RKneeToHip, origin_LAnkleToknee + origin_RKneeToHip);

        text.setText("다리길이 : " + origin_leg + "팔길이" + origin_arm);

        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyModelActivity.class);
                intent.putExtra("height",getIntent().getStringExtra("height"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                Log.e("name,height",getIntent().getStringExtra("name")+getIntent().getStringExtra("height"));
                startActivity(intent);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
            }
        });
    }
    public double getDistance(double a_x, double b_x, double a_y, double b_y) {
        return Math.sqrt((a_x - b_x)*(a_x - b_x) + (a_y - b_y)*(a_y - b_y));
    }

    public Point getCenter(double a_x, double b_x, double a_y, double b_y) {
        Point point = new Point();
        point.x = (a_x + b_x)/2;
        point.y = (a_y + b_y)/2;
        return point;
    }

    public double getOrigin(double X) {
        return (origin_Height * X)/height;
    }

    public double getAve(double a, double b) {
        return (a + b)/2;
    }
}
