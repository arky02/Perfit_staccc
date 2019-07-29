package com.example.android.Perfect_fit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class ModifyModelActivity extends AppCompatActivity {

    ImageView button_cancel;
    Button btn_createModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_model);

        button_cancel = findViewById(R.id.button_cancel);
        btn_createModel = findViewById(R.id.btn_createModel);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Data_model> ModelData = new ArrayList<>();

        if(ModelData.isEmpty()) {
            Global_Data data  = (Global_Data) getApplication();
            String name = data.getName();
            Data_model modeldata = new Data_model(name);
            ModelData.add(modeldata);

            Adapter_modify_model adapter = new Adapter_modify_model(ModelData);

            recyclerView.setAdapter(adapter);
        }
        else {
            Global_Data data  = (Global_Data) getApplication();
            String name = data.getName();
            Data_model modeldata = new Data_model(name);
            ModelData.add(modeldata);

            Adapter_modify_model adapter = new Adapter_modify_model(ModelData);
            adapter.notifyDataSetChanged();

        }


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stay, R.anim.sliding_down);
            }
        });

        btn_createModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });

    }

    public void CreateDialog() {
        final Dialog dialog = new Dialog(ModifyModelActivity.this);
        if(dialog.getWindow() != null){
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(R.layout.dialog_create_model);

        final EditText edt_name = dialog.findViewById(R.id.edt_name);
        final EditText edt_height = dialog.findViewById(R.id.edt_height);
        ImageView btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = null;
                int height = 0;

                if(!edt_name.getText().toString().isEmpty()) {
                    name = edt_name.getText().toString();
                }
                if(!edt_height.getText().toString().isEmpty()) {
                    height = Integer.parseInt(edt_height.getText().toString());
                }
                Global_Data data = (Global_Data) getApplication();
                data.setName(name);
                data.setHeight(height);
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), CameraChooseActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }

//    public void Dialog() {
//        final CustomDialog_CreateModel customDialog_createModel = new CustomDialog_CreateModel(ModifyModelActivity.this);
//        customDialog_createModel.show();
//    }

}
