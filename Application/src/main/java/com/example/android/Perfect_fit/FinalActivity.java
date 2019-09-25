package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    TextView user_shoulder,user_leg,user_arm,cloth_arm,cloth_shoulder,cloth_verticallength,cloth_horizontallength,shoulder_difference,arm_difference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        double armLength_user = getIntent().getDoubleExtra("armDistance",0);
        double legLength_user = getIntent().getDoubleExtra("legDistance", 0);
        double shoulderWidth_user = getIntent().getDoubleExtra("shoulderWidth", 0);

        int armLength_cloth = getIntent().getIntExtra("armDistance1",0);
        int shoulderWidth_cloth  = getIntent().getIntExtra("shoulderWidth",0);
        int vertical_cloth = getIntent().getIntExtra("verticalWidth", 0);
        int horizontal_cloth = getIntent().getIntExtra("horizontalWidth",0);

        ArrayList<String> arrayListShoulder = new ArrayList<>();
        ArrayList<String> arrayListarm= new ArrayList<>();
        ArrayList<String> arrayListvertical= new ArrayList<>();
        arrayListShoulder = getIntent().getStringArrayListExtra("arrayListShoulder");
        arrayListarm = getIntent().getStringArrayListExtra("arrayListArm");
        arrayListvertical = getIntent().getStringArrayListExtra("arrayListVertical");

        user_arm = findViewById(R.id.final_user_arm);
        user_leg = findViewById(R.id.final_user_leg);
        user_shoulder= findViewById(R.id.final_user_shoulder);
        cloth_arm = findViewById(R.id.final_cloth_arm);
        cloth_horizontallength = findViewById(R.id.final_cloth_horizontallength);
        cloth_shoulder = findViewById(R.id.final_cloth_shoulder);
        cloth_verticallength = findViewById(R.id.final_cloth_verticallength);
        arm_difference = findViewById(R.id.final_arm_difference);
        shoulder_difference = findViewById(R.id.final_shoulder_difference);

        if (armLength_user !=0 &&legLength_user!=0&&shoulderWidth_user!=0){
            Toast.makeText(getApplicationContext(), "신체 치수 데이터가 존재하지 않습니다.\n신체 치수 측정을 다시 진행해주세요.", Toast.LENGTH_SHORT).show();
        }else if(armLength_cloth!=0&&shoulderWidth_cloth!=0){
            if(arrayListarm !=null||arrayListShoulder!=null||arrayListvertical !=null){

                user_arm.setText(armLength_user +"cm");
                user_leg.setText(legLength_user +"cm");
                user_shoulder.setText(shoulderWidth_user+"cm");
                cloth_verticallength.setText(arrayListvertical.get(0) +"cm");
                cloth_shoulder.setText(arrayListShoulder.get(0)+"cm");
                cloth_arm.setText(arrayListarm.get(0)+"cm");

            }else{
                Toast.makeText(getApplicationContext(), "옷 치수 데이터가 존재하지 않습니다. 옷 치수 데이터를 입력한 뒤 다시 진행해주세요", Toast.LENGTH_SHORT).show();
            }

        }else{
            user_arm.setText(armLength_user +"cm");
            user_leg.setText(legLength_user +"cm");
            user_shoulder.setText(shoulderWidth_user+"cm");
            cloth_verticallength.setText(vertical_cloth+"cm");
            cloth_shoulder.setText(shoulderWidth_cloth+"cm");
            cloth_horizontallength.setText(horizontal_cloth +"cm");
            cloth_arm.setText(armLength_cloth+"cm");
        }
    }
}
