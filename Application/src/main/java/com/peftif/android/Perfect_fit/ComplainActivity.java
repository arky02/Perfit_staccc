package com.peftif.android.Perfect_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.peftif.android.Perfect_fit.Camera.CameraChooseActivity;


public class ComplainActivity extends AppCompatActivity {
    Button submit;
    TextInputEditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        submit = findViewById(R.id.bt_submit);
        edt = findViewById(R.id.tiet_apply);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt.getText().equals("")||edt.length() == 0||edt.length() == 1) {

                    Toast.makeText(getApplicationContext(), "글을 충분히 입력하신 후에 다시 제출해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                } else {


                    final Dialog dialog = new Dialog(ComplainActivity.this);

                    if (dialog.getWindow() != null) {
                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                    dialog.setContentView(R.layout.dialog_complain);
                    dialog.setCancelable(true);


                    final Button btn_ok = dialog.findViewById(R.id.btn_ok44);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }


                    });
                    dialog.show();

                    edt.setText("");

                }
            }
        });



    }

}
