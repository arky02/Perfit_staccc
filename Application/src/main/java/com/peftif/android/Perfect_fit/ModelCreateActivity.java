package com.peftif.android.Perfect_fit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peftif.android.Perfect_fit.Camera.CameraChooseActivity;
import com.peftif.android.Perfect_fit.PoseEstimation.PosenetActivity;

public class ModelCreateActivity extends AppCompatActivity {

    EditText name, key;
    boolean isNameInputed, isKeyInputed;
    Button check;
    final int UNCONNECTED = 3;

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
//                    check.setTextColor(Color.parseColor("#000000"));
//                    check.setBackgroundResource(R.drawable.buttondesign);
                }else{
//                    check.setTextColor(Color.parseColor("#80000000"));
//                    check.setBackgroundResource(R.drawable.buttondesign_disabled);
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
//                    check.setTextColor(Color.parseColor("#80000000"));
//                    check.setBackgroundResource(R.drawable.buttondesign);

                }else{
//                    check.setTextColor(Color.parseColor("#A0A0A0"));
//                    check.setBackgroundResource(R.drawable.buttondesign_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InternetCheck()) {
                    Intent mintent = new Intent(getApplicationContext(), CameraChooseActivity.class);
                    mintent.putExtra("img", "/storage/emulated/0/Android/data/com.peftif.android.Perfect_fit/files/pic.jpg");
                    mintent.putExtra("name",name.getText().toString());
                    mintent.putExtra("height",key.getText().toString());
                    startActivity(mintent);
                    finish();
                }
            }
        });


        requestReadExternalStoragePermission();
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                // MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1 : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean InternetCheck() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == UNCONNECTED) {
            Toast.makeText(this, "인터넷 연결을 확인해주세요!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}