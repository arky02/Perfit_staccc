package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.File;

public class ModelAdjustActivity extends AppCompatActivity {

    private HumanSkeleton humanSkeleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        humanSkeleton = intent.getParcelableExtra("skeleton");
        setContentView(new MySurfaceView(this, humanSkeleton));


    }
}
