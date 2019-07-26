package com.example.android.Perfect_fit;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class ModelCreateActivity extends AppCompatActivity {

    EditText name, key;
    boolean isNameInputed, isKeyInputed;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_create);

        name = findViewById(R.id.name);
        key = findViewById(R.id.key);
        check = findViewById(R.id.check);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isNameInputed = name.getId() == R.id.name && s.length() > 0;
                check.setEnabled(isNameInputed && isKeyInputed);
                if (check.isEnabled()){
                    check.setTextColor(Color.parseColor("#000000"));
                    check.setBackgroundResource(R.drawable.buttondesign);
                }else{
                    check.setTextColor(Color.parseColor("#80000000"));
                    check.setBackgroundResource(R.drawable.buttondesign_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isKeyInputed = key.getId() == R.id.key && s.length() > 0;
                check.setEnabled(isNameInputed && isKeyInputed);
                if (check.isEnabled()){
                    check.setTextColor(Color.parseColor("#80000000"));
                    check.setBackgroundResource(R.drawable.buttondesign);

                }else{
                    check.setTextColor(Color.parseColor("#A0A0A0"));
                    check.setBackgroundResource(R.drawable.buttondesign_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}