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

package com.example.android.Perfect_fit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            if(getIntent().getIntExtra("CameraChoose",0) == 1 ){// 내가찍음, 1
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
            }else if(getIntent().getIntExtra("CameraChoose",0) == 2 ){ //남이찍어줌, 2
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment_other.newInstance())
                        .commit();
            }else if(getIntent().getIntExtra("CameraChoose",0) == 0 ){
                Toast.makeText(this, "오류가 발생했습니다.다시 시작해주세요", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
