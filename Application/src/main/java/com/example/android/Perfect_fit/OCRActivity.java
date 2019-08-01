package com.example.android.Perfect_fit;

import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OCRActivity extends AppCompatActivity {

    Bitmap image; //사용되는 이미지
    String OCRresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        //이미지 디코딩을 위한 초기화
        String OCRresult =  getIntent().getStringExtra("OCRresult");

        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);
    }
}
