package com.peftif.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.peftif.android.Perfect_fit.FloatingMiniPerfit.CheckFWPermission;

public class Main2Activity extends AppCompatActivity {

    Button btnfirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnfirst = findViewById(R.id.btnfirst);

    btnfirst.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mintent = new Intent(getApplicationContext(), CheckFWPermission.class);
            startActivity(mintent);
        }
    });
    }
}
