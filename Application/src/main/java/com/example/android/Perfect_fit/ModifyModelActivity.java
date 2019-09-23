package com.example.android.Perfect_fit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.Perfect_fit.Camera.CameraChooseActivity;
import com.example.android.Perfect_fit.ModelData.Data_model;
import com.example.android.Perfect_fit.ModelData.DatabaseHelper;

import java.util.List;

public class ModifyModelActivity extends AppCompatActivity {

    ImageView button_cancel;
    Button btn_createModel;
    DatabaseHelper databaseHelper;
    String name, height;
    RecyclerView recyclerView;
    RecycleAdapter recycler;
    RecyclerView.LayoutManager layoutManager;
    List<Data_model> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_model);

        button_cancel = findViewById(R.id.button_cancel);
        btn_createModel = findViewById(R.id.btn_createModel);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        databaseHelper = new DatabaseHelper(this);

        String name = getIntent().getStringExtra("name");
        String height = getIntent().getStringExtra("height");
        Double arm_length = getIntent().getDoubleExtra("armDistance",0);
        Double leg_length = getIntent().getDoubleExtra("legDistance",0);
        Double shoulder_length = getIntent().getDoubleExtra("shoulderWidth",0);



        Log.e("name,height",name+height);
        if(name == null && height == null&&arm_length== 0&&leg_length == 0&&shoulder_length ==0){
            RefreshAdapter();
        }else{
            Data_model modeldata1 = new Data_model();
            modeldata1.setName(name);
            modeldata1.setHeight(height);
            modeldata1.setArm(arm_length);
            modeldata1.setLeg(leg_length);
            modeldata1.setShoulder(shoulder_length);
            databaseHelper.insertdata(name, height,arm_length,leg_length,shoulder_length);
            RefreshAdapter();
        }
        /*
        databaseHelper.insertdata(name, height);
        ArrayList<Data_model> ModelData = new ArrayList<>();
        ModelData.add(modeldata);
        Adapter_modify_model adapter = new Adapter_modify_model(ModelData); //어뎁터를 데이터로 적용시켜야함
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        */
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
        recycler.setOnItemClickListener(
                new RecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        System.out.println(pos);
                        datamodel = databaseHelper.getdata();
                        String modelHigth = datamodel.get(pos).getHeight();
                        String modelName = datamodel.get(pos).getName();
                        Double modelArm = datamodel.get(pos).getArm();
                        Double modelLeg = datamodel.get(pos).getLeg();
                        Double modelShoulder = datamodel.get(pos).getShoulder();

                        System.out.println(pos+","+modelName+","+modelHigth+","+modelArm+","+modelLeg+","+modelShoulder);

//                        Toast.makeText(ModifyModelActivity.this, pos, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void CreateDialog() {
        final Dialog dialog = new Dialog(ModifyModelActivity.this);
        if (dialog.getWindow() != null) {
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

                name = edt_name.getText().toString();
                height = edt_height.getText().toString();
                if(name.isEmpty() || height.isEmpty()){
                    Toast.makeText(ModifyModelActivity.this, "이름과 키를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Intent mintent = new Intent(getApplicationContext(), CameraChooseActivity.class);
                    mintent.putExtra("name", edt_name.getText().toString());
                    mintent.putExtra("height", edt_height.getText().toString());
//                    databaseHelper.insertdata(name,height);
                    edt_height.setText("");
                    edt_name.setText("");
//                    RefreshAdapter();
                    dialog.dismiss();
                }
                /*
                String name = null;
                int height = 0;

                if (!edt_name.getText().toString().isEmpty()) {
                    name = edt_name.getText().toString();
                }
                if (!edt_height.getText().toString().isEmpty()) {
                    height = Integer.parseInt(edt_height.getText().toString());
                }
                Global_Data data = (Global_Data) getApplication();
                data.setName(name);
                data.setHeight(height);
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), CameraChooseActivity.class);
                */

            }
        });
        dialog.show();

    }

    public void RefreshAdapter(){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        datamodel = databaseHelper.getdata();
        recycler = new RecycleAdapter(datamodel);

        Log.i("HIteshdata", "" + datamodel);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);

    }

//    public void Dialog() {
//        final CustomDialog_CreateModel customDialog_createModel = new CustomDialog_CreateModel(ModifyModelActivity.this);
//        customDialog_createModel.show();
//    }

}
