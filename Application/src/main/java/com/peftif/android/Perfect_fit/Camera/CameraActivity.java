/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peftif.android.Perfect_fit.Camera;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.peftif.android.Perfect_fit.R;


public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            if(getIntent().getIntExtra("CameraChoose",0) == 1 ){// 내가찍음, 1
                Bundle bundle = new Bundle();
                bundle.putString("name",getIntent().getStringExtra("name"));
                bundle.putString("height",getIntent().getStringExtra("height"));
                bundle.putInt("CameraChoose",getIntent().getIntExtra("CameraChoose", 0));
                Log.e("cameraq", ""+getIntent().getIntExtra("CameraChoose", 0));

                Camera2BasicFragment frag = Camera2BasicFragment.newInstance();
                frag.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag)
                    .commit();
            }else if(getIntent().getIntExtra("CameraChoose",0) == 2 ){ //남이찍어줌, 2
                Bundle bundle = new Bundle();
                bundle.putString("name",getIntent().getStringExtra("name"));
                bundle.putString("height",getIntent().getStringExtra("height"));
                bundle.putInt("CameraChoose",getIntent().getIntExtra("CameraChoose", 0));
                Log.e("cameraq", ""+getIntent().getIntExtra("CameraChoose", 0));
                Log.e("name,height2",getIntent().getStringExtra("name")+getIntent().getStringExtra("height"));
                Camera2BasicFragment_other frag1 = Camera2BasicFragment_other.newInstance();
                frag1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, frag1)
                        .commit();
            }else if(getIntent().getIntExtra("CameraChoose",0) == 0 ){
                Toast.makeText(this, "오류가 발생했습니다.다시 시작해주세요", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
