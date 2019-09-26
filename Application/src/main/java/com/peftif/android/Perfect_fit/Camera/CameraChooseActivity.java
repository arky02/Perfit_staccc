package com.peftif.android.Perfect_fit.Camera;

import android.app.Dialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.peftif.android.Perfect_fit.R;


public class CameraChooseActivity extends AppCompatActivity {

    Button btn_pic_other, btn_pic_me;
    int check = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_choose);

        btn_pic_other = findViewById(R.id.btn_pic_other);
        btn_pic_me = findViewById(R.id.btn_pic_me);

        btn_pic_me.setOnClickListener(v -> {

            PickMeCreateDialog();

        });

        btn_pic_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickOtherCreateDialog();

            }
        });
    }

    public void PickMeCreateDialog() {
        final Dialog dialog = new Dialog(CameraChooseActivity.this);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(R.layout.dialog_tutorial);
        dialog.setCancelable(false);

        final ImageView btn_next = dialog.findViewById(R.id.btn_next);
        final ImageView image = dialog.findViewById(R.id.image);
        final LinearLayout tutorial = dialog.findViewById(R.id.tutorial);
        final LinearLayout end = dialog.findViewById(R.id.end);
        final Button btn_ok = dialog.findViewById(R.id.btn_ok);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check++;

                switch (check) {
                    case 2 :
                        image.setImageResource(R.drawable.selfie_tutorial_2);
                        break;
                    case 3 :
                        image.setImageResource(R.drawable.selfie);
                        break;
                    case 4 :
                        tutorial.setVisibility(View.GONE);
                        end.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(getApplicationContext(), CameraActivity.class);
                mintent.putExtra("CameraChoose", 1);
                mintent.putExtra("name", getIntent().getStringExtra("name"));
                mintent.putExtra("height", getIntent().getStringExtra("height"));
                startActivity(mintent);
                finish();
            }
        });

        btn_pic_other.setOnClickListener(v ->
        {Intent mintent2 = new Intent(getApplicationContext(), CameraActivity.class);
        mintent2.putExtra("CameraChoose", 2);
        mintent2.putExtra("name", getIntent().getStringExtra("name"));
        mintent2.putExtra("height", getIntent().getStringExtra("height"));
        Log.e("name,height2", getIntent().getStringExtra("name") + getIntent().getStringExtra("height"));
        startActivity(mintent2);
        finish();});
        dialog.show();
    }

    public void PickOtherCreateDialog() {
        final Dialog dialog = new Dialog(CameraChooseActivity.this);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(R.layout.dialog_tutorial1);
        dialog.setCancelable(false);

        final ImageView btn_next = dialog.findViewById(R.id.btn_next);
        final ImageView image = dialog.findViewById(R.id.image);
        final LinearLayout tutorial = dialog.findViewById(R.id.tutorial);
        final LinearLayout end = dialog.findViewById(R.id.end);
        final Button btn_ok = dialog.findViewById(R.id.btn_ok);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check++;

                switch (check) {
                    case 2 :
                        image.setImageResource(R.drawable.tutorial);
                        break;
                    case 3 :
                        tutorial.setVisibility(View.GONE);
                        end.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent2 = new Intent(getApplicationContext(), CameraActivity.class);
                mintent2.putExtra("CameraChoose", 2);
                mintent2.putExtra("name", getIntent().getStringExtra("name"));
                mintent2.putExtra("height", getIntent().getStringExtra("height"));
                startActivity(mintent2);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
}
