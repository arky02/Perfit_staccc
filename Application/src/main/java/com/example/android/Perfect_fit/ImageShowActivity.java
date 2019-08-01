package com.example.android.Perfect_fit;

import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageShowActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        image = findViewById(R.id.image);
        image.setImageBitmap(BitmapFactory.decodeByteArray(Camera2BasicFragment.bitmapBytes,0,Camera2BasicFragment.bitmapBytes.length));
    }
}
