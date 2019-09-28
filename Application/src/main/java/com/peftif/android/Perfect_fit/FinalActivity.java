package com.peftif.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.peftif.android.Perfect_fit.ModelData.Data_model;
import com.peftif.android.Perfect_fit.ModelData.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FinalActivity extends AppCompatActivity {

    TextView comment_arm, comment_shoulder, difference_arm, difference_shoulder, txt_totalComment;
    double arm, shoulder;
    ImageView btn_cloth, btn_home;
    ImageView img_icon;
    List<Data_model> datamodel;
    DatabaseHelper databaseHelper;
    int big = 0, small = 0, good = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        comment_arm = findViewById(R.id.comment_arm);
        comment_shoulder = findViewById(R.id.comment_shoulder);
        difference_arm = findViewById(R.id.difference_arm);
        difference_shoulder = findViewById(R.id.difference_shoulder);
        txt_totalComment = findViewById(R.id.txt_totalComment);
        img_icon = findViewById(R.id.img_icon);
        btn_cloth = findViewById(R.id.btn_cloth);
        btn_home = findViewById(R.id.btn_home);

        //intent ocr
        int armLength_cloth = getIntent().getIntExtra("arm",0);
        int shoulderWidth_cloth  = getIntent().getIntExtra("shoulder",0);
        int vertical_cloth = getIntent().getIntExtra("verticalWidth", 0);
        int horizontal_cloth = getIntent().getIntExtra("horizontalWidth",0);



       databaseHelper = new DatabaseHelper(getApplicationContext());
        datamodel = databaseHelper.getdata();

        Log.e("check arm cloth", ""+armLength_cloth);
        Log.e("check arm dis", ""+datamodel.get(0).getArm());

        arm = Math.round(datamodel.get(0).getArm() * 10)/10 - (double)armLength_cloth;
        shoulder = Math.round(datamodel.get(0).getShoulder() * 10)/10 - (double)shoulderWidth_cloth;

        difference_arm.setText(""+arm);
        difference_shoulder.setText(""+shoulder);
        setComment(comment_arm, arm);
        setComment(comment_shoulder, shoulder);
        setTotalComment();
        Toast.makeText(getApplicationContext(), "더 다양하고 세부적인 치수들의 비교는 곧 업데이트 될 예정입니다!", Toast.LENGTH_LONG).show();

        btn_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(getApplicationContext(),FittingActivity.class);
                startActivity(mintent);
                showDialog();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showDialog() {
        Dialog dialog = new Dialog(FinalActivity.this);
        dialog.setContentView(R.layout.dialog_setting3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }

    public void setComment(TextView textView, double difference) {
        if(difference <= 10 && difference >= -10) {
            textView.setText("적당");
            good++;
        }
        else if(difference > 10) {
            textView.setText("작음");
            small++;
        }
        else {
            textView.setText("큼");
            big++;
        }
    }

    public void setTotalComment() {
        if(small == 1) {
            txt_totalComment.setText("옷이 좀 작네요!");
            img_icon.setImageResource(R.drawable.sad);
        }
        else if(big == 1) {
            txt_totalComment.setText("옷이 좀 크네요!");
            img_icon.setImageResource(R.drawable.sad);
        }
        else {
            txt_totalComment.setText("옷이 딱 맞네요!");
            img_icon.setImageResource(R.drawable.smile);
        }
    }
}
