package com.example.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class VisionOCR extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    public static Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_ocr);
        Intent intent = getIntent();
        Bitmap imagebitmap = (Bitmap)intent.getParcelableExtra("image");
         mContext = this;

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        getTextFromImage(imagebitmap);
    }
    public void getTextFromImage(Bitmap bitmap){

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(getApplicationContext(), "Could not get the Text", Toast.LENGTH_SHORT).show();
        }else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            StringBuilder sb = new StringBuilder();


            for(int i =0 ; i< items.size();++i){
                TextBlock myItem = items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("\n");

            }
            textView.setText(sb.toString());
        }

    }
}
