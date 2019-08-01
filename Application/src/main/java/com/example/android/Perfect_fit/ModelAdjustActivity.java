package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ModelAdjustActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_adjust);

        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("img");
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        imageView.setImageBitmap(bitmap);
    }
}
