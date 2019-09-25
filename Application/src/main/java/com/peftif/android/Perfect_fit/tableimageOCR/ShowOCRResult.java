package com.peftif.android.Perfect_fit.tableimageOCR;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.peftif.android.Perfect_fit.R;

public class ShowOCRResult extends AppCompatActivity {

    TextView tv_arm,tv_shoulder,tv_vertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ocrresult);

        String[] shoulder = getIntent().getStringArrayExtra("arrayListShoulder");
        String[] arm = getIntent().getStringArrayExtra("arrayListArm");
        String[] vertical = getIntent().getStringArrayExtra("arrayListVertical");

        tv_arm = findViewById(R.id.tv_arm);
        tv_shoulder = findViewById(R.id.tv_shoulder);
        tv_vertical = findViewById(R.id.tv_vertical);

        tv_vertical.setText(vertical.toString());
        tv_arm.setText(arm.toString());
        tv_shoulder.setText(shoulder.toString());

    }
}
