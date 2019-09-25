package com.peftif.android.Perfect_fit.FloatingMiniPerfit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.peftif.android.Perfect_fit.GlobalData;
import com.peftif.android.Perfect_fit.MainActivity;

public class CheckFWPermission extends AppCompatActivity {
    public Context context;
    public static boolean isPermissionOkay = false;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        overlayPermission();

    }
    public void mintent(){
        startService(new Intent(CheckFWPermission.this, FloatingWidgetService.class));
        GlobalData.isWidgetDistroyed = false;

        finish();
    }

    public void overlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.v("App", "Build Version Greater than or equal to M: " + Build.VERSION_CODES.M);
            checkDrawOverlayPermission();
        } else {
            Log.v("App", "OS Version Less than M");
            //No need for Permission as less then M OS.
        }
    }

    public final static int REQUEST_CODE = 101;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        Log.v("App", "Package Name: " + getApplicationContext().getPackageName());

        // Check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(context)) {
            Log.v("App", "Requesting Permission" + Settings.canDrawOverlays(context));
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getApplicationContext().getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, REQUEST_CODE); //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
        } else {
            Log.v("App", "We already have permission for it.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // Permission Granted by Overlay
                    // Do your Stuff
                    isPermissionOkay = true;
                   mintent();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("App", "OnActivity Result.");
        //check if received result code
        //  is equal our requested code for draw permission

        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // Permission Granted by Overlay
                    // Do your Stuff
                    isPermissionOkay = true;
                    mintent();
                } else {
                    Toast.makeText(getApplicationContext(), "퍼미션 수락 필요", Toast.LENGTH_SHORT).show();
                    Intent grantIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(grantIntent);

                }
            }
        }
    }
}