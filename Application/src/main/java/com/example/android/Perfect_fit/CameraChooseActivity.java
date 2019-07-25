package com.example.android.Perfect_fit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CameraChooseActivity extends AppCompatActivity {

    Button btn_pic_other,btn_pic_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_choose);

        btn_pic_other = findViewById(R.id.btn_pic_other);
        btn_pic_me = findViewById(R.id.btn_pic_me);

        btn_pic_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(mintent);
            }
        });

        btn_pic_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent2 = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(mintent2);
            }
        });


    }
}
