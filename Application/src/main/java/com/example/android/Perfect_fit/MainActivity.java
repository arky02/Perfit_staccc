package com.example.android.Perfect_fit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    LinearLayout button_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);

        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyModelActivity.class);
                intent.putExtra("height",getIntent().getStringExtra("height"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                Log.e("name,height",getIntent().getStringExtra("name")+getIntent().getStringExtra("height"));
                startActivity(intent);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
            }
        });
    }
}
