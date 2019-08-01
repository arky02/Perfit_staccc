package com.example.android.Perfect_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    LinearLayout button_name;
    TextView text;
    double origin_leg, origin_arm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);
        text = findViewById(R.id.text_test);

        Intent mintent = getIntent();
        origin_leg  = mintent.getDoubleExtra("legdistance",0);
        origin_arm  = mintent.getDoubleExtra("armdistance",0);
        mintent.getStringExtra("armdistance");

        text.setText("다리길이 : " + origin_leg + "팔길이" + origin_arm);

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
