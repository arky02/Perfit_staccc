package com.example.android.Perfect_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                mintent.putExtra("CameraChoose",1);
                mintent.putExtra("name",getIntent().getStringExtra("name"));
                mintent.putExtra("height",getIntent().getStringExtra("height"));

                startActivity(mintent);
                finish();
            }
        });

        btn_pic_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent2 = new Intent(getApplicationContext(),CameraActivity.class);
                mintent2.putExtra("CameraChoose",2);
                mintent2.putExtra("name",getIntent().getStringExtra("name"));
                mintent2.putExtra("height",getIntent().getStringExtra("height"));
                Log.e("name,height2",getIntent().getStringExtra("name")+getIntent().getStringExtra("height"));
                startActivity(mintent2);
                finish();
            }
        });


    }
}
