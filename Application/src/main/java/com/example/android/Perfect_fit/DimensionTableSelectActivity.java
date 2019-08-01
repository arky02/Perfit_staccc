package com.example.android.Perfect_fit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.res.AssetManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.view.View.GONE;


public class DimensionTableSelectActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;    ImageButton btn_okay;
    Button btn;
    private Uri resultUri = null;
    Bitmap image,bitmapimg; //사용되는 이미지
    private TessBaseAPI mTess; //Tess API reference
    String datapath = ""; //언어데이터가 있는 경로
    ProgressBar progressBar;
    AlertDialog dialog;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_table_select);

        btn_okay = findViewById(R.id.picture);

        btn = findViewById(R.id.btn);
        //progressBar = findViewById(R.id.progress1);
        imageview = findViewById(R.id.imageView);
        ll = findViewById(R.id.ll);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(DimensionTableSelectActivity.this);
            }
        });

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(resultUri!= null){
//                    setProgressDialog();
                    processImage();
                    Toast.makeText(DimensionTableSelectActivity.this, "ocr 로딩 성공", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(DimensionTableSelectActivity.this, "선택된 이미지 파일이 없습니다", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
//    private void setDialog(boolean show){
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        //View view = getLayoutInflater().inflate(R.layout.progress);
//        builder.setView(R.layout.activity_dimension_table_select);
//        Dialog dialog = builder.create();
//        if (show)dialog.show();
//        else dialog.dismiss();
//    }
//    public void setProgressDialog() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setView(ll);
//        dialog = builder.create();
//        dialog.show();
//        Window window = dialog.getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//            layoutParams.copyFrom(dialog.getWindow().getAttributes());
//            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//            layoutParams.height = LinearLayout
//                    .LayoutParams.WRAP_CONTENT;
//            dialog.getWindow().setAttributes(layoutParams);
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageview.setImageURI(resultUri);
                try {
                    bitmapimg =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startOCR(bitmapimg);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //startOCR(selectedBitmap);

    //-----------OCR -----------

    private void startOCR(Bitmap OCR_IMAGE){
        //이미지 디코딩을 위한 초기화
        image = OCR_IMAGE; //이미지파일
        //언어파일 경로
        datapath = getFilesDir() + "/tesseract/";

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"));

        //Tesseract API
        String lang = "kor";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);
    }

    //Process an Image
    public void processImage() {
        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        Intent intent = new Intent(getApplicationContext(),OCRActivity.class);
        intent.putExtra("OCRresult",OCRresult);
        startActivity(intent);
//        dialog.dismiss();
        ll.setVisibility(GONE);
        finish();
    }

    //copy file to device
    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/kor.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //check file on the device
    private void checkFile(File dir) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles();
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/kor.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }
    //-----------OCR ------------

}