package com.example.android.Perfect_fit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
    double pose[][] = new double[18][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);
        text = findViewById(R.id.text_test);

        Intent intent = getIntent();
        String posenetData = intent.getStringExtra("posenet");
        Log.d("pose - 1",posenetData);
        // 0 : 코, 1 : 목, 2 : 오른쪽 어깨, 3: 오른쪽 팔굼치, 4 : 오른쪽 손목, 5 : 왼쪽 어깨
        // 6 : 왼쪽 팔굼치, 7 : 왼쪽 손목, 8 : 오른쪽 엉덩이, 9 : 오른쪽 무릎, 10 : 오른쪽 발목
        //11 : 왼쪽 엉덩이, 12 : 왼쪽 무릎, 13 : 왼쪽 발목, 14 : 오른쪽 눈, 15 : 왼쪽 눈
        //16 : 오른쪽 귀, 17 : 왼쪽 귀
        try {
            JSONObject jsonObject = new JSONObject(posenetData);
            JSONArray jsonArray = jsonObject.getJSONArray("predictions");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

            for (int i = 0; i <18 ; i++) {
                JSONObject jsonObject2 = jsonObject1.getJSONObject(Integer.toString(i));
                pose[i][0] = jsonObject2.getDouble("x");
                pose[i][1] = jsonObject2.getDouble("y");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
