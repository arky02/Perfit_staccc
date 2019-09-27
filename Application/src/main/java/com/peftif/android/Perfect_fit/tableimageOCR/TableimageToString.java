package com.peftif.android.Perfect_fit.tableimageOCR;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.res.AssetManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.peftif.android.Perfect_fit.R;
import com.theartofdev.edmodo.cropper.CropImage;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.GaussianBlur;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static org.opencv.imgproc.Imgproc.resize;
import static org.opencv.imgproc.Imgproc.threshold;


public class TableimageToString extends AppCompatActivity {

    private ImageView imageview;
    ImageButton btn_okay;
    private Uri resultUri = null;
    Bitmap image,bitmapimg; //사용되는 이미지
    Button btn;
    private TessBaseAPI mTess; //Tess API reference
    String datapath = ""; //언어데이터가 있는 경로
    LinearLayout ll,layout2;
    RelativeLayout layout1;
    public static int thresholdMin = 145; // Threshold 80 to 105
    private int thresholdMax = 255; // Always 255
    public String recognizeResult = "";
    Mat origin;
    Boolean isDone = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_table_select);

        OpenCVLoader.initDebug();
        btn_okay = findViewById(R.id.picture);
        ProgressBar progress = findViewById(R.id.loader);
        progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FF1684"), PorterDuff.Mode.MULTIPLY);
        imageview = findViewById(R.id.imageView);
        ll = findViewById(R.id.ll);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        btn = findViewById(R.id.btn);
        layout2.setVisibility(GONE);
        layout1.setVisibility(VISIBLE);
        CropImage.activity().start(TableimageToString.this);
        startOCR();



        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(resultUri!= null){
//                    ll.bringToFront();
//                    ll.setVisibility(VISIBLE);
                    layout2.setVisibility(VISIBLE);
                    layout1.setVisibility(GONE);
                    ImageView load= (ImageView) findViewById(R.id.imageLoad);
                    Glide.with(getApplicationContext()).load(R.drawable.loading).into(load);

                    Log.e("recognizeResult", "okaybtn");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BitmaptoMat(bitmapimg);
                            Log.e("recognizeResult", "thread");
                            Log.e("recognizeResult", recognizeResult);
                            Intent mintent = new Intent(getApplicationContext(),EditDistance.class);
                            mintent.putExtra("OCRresult", recognizeResult);
                            startActivity(mintent);
                            isDone = false;
                            finish();
                            //TODO
                        }
                    }).start();

                }else{
                    Toast.makeText(TableimageToString.this, "선택된 이미지 파일이 없습니다", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(TableimageToString.this);
                startOCR();

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isDone = false){
            this.finish();
        }
        super.onBackPressed();

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
            isDone = true;
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageview.setImageURI(resultUri);
                try {
                    bitmapimg =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }



    //startOCR(selectedBitmap);

    //-----------OCR -----------

    private void startOCR(){

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
    public String processImage(Bitmap bitmap) {
        String OCRresult = null;
//      mTess.setImage(image);  이 image는...? 연결 필요
        mTess.setImage(bitmap);
        OCRresult = mTess.getUTF8Text();
        Log.e("OCRresult", OCRresult);
        Log.e("OCRresult","OCRresult" );

        return OCRresult;
//        Intent intent = new Intent(getApplicationContext(), OCRActivity.class);
//        intent.putExtra("OCRresult",OCRresult);
//        startActivity(intent);
//        finish();
    }
    public void onDestroy() {
        super.onDestroy();
        if (mTess != null)
            mTess.end();
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
    //---------------------------- OCR ---------------------------------
    //-------------------------- Image improving ----------------------------
    private void BitmaptoMat(Bitmap bitmap){
        origin = new Mat();
        Utils.bitmapToMat(bitmap, origin);
        recognizeImage(origin);

    }

    private boolean recognizeImage(Mat origin) {

        // Convert the image to GRAY
        Log.i("tag","recognizeImage");
        // Reset value
        recognizeResult = "";

        Mat originGray = new Mat();
        cvtColor(origin, originGray, COLOR_BGR2GRAY);

        // Process noisy, blur, and threshold to get black-white image
        originGray = processNoisy(originGray);  //노이즈 제거

        recognizeResult = matToString(originGray); //matToString 함수 거침>> OCR로 텍스트 추출 결과 반환 -- recognizeResult = OCR로 변환된 String
        Log.i("tag","Done recognize");

        originGray.release();
        originGray = null;
        Log.i("tag","Result: " + recognizeResult);//recognizeResult = 최종 OCR 결과 > 이걸로 이제 유사도 분석 해서 단어 알맞게 바꿔야함(Edit Distance)

        return true;
    }

    /**
     * Process noisy or blur image with simplest filters
     * @param grayMat
     * @returni
     */
    private Mat processNoisy(Mat grayMat) {
        Mat element1 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        Mat element2 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        dilate(grayMat, grayMat, element1);
        erode(grayMat, grayMat, element2);
        threshold(grayMat, grayMat, thresholdMin, thresholdMax, THRESH_BINARY);

        return grayMat;
    }


    /**
     * Convert mat to bitmap
     *
     * @param mat
     * @return
     */

    public static Bitmap toBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    private String matToString(Mat source) {
        int newWidth = source.width()/2;
        resize(source, source, new Size(newWidth, (source.height() * newWidth) / source.width()));
        String result = processImage(toBitmap(source));

        return result;

    }

    //-------------------------- Image improving ----------------------------


}