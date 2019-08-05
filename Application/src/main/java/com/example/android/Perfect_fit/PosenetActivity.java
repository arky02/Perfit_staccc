package com.example.android.Perfect_fit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PosenetActivity extends AppCompatActivity {
    public static Context context;
    HumanSkeleton data;
    double height, LShoulderToElbow, RShoulderToElbow, LElbowToWrist, RElbowToWrist, shoulderWidth, LAnkleToknee, RAnkleToknee, LKneeToHip, RKneeToHip, bodyDistance;
    double origin_Height, origin_LShoulderToElbow, origin_RShoulderToElbow, origin_LElbowToWrist, origin_RElbowToWrist, origin_shoulderWidth, origin_LAnkleToknee, origin_RAnkleToknee, origin_LKneeToHip, origin_RKneeToHip, origin_bodyDistance;
    double origin_leg, origin_arm;
    ProgressBar progress;

    private class PoseEstimationTask extends AsyncTask<File, Double, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FF1684"), PorterDuff.Mode.MULTIPLY);
            progress.setIndeterminate(true);
        }

        @Override
        protected String doInBackground(File... files) {
            File file =  new File(PosenetActivity.this.getExternalFilesDir(null), "pic_resize.jpg");

            resize(files[0], file);

            String clientId = "v06knzwc61";
            String clientSecret = "2pFHWbm0xkEDtvWJWGVCrChhQvUOzJ7MY58vbDPZ";
            String paramName = "image"; // 파라미터명은 image로 지정
            StringBuffer response = new StringBuffer();

            // 1. File을 열어서 Binary  객체로 만들기
            try {
                String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-pose/v1/estimate"; // 사람 인식
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);
                // multipart request
                String boundary = "---" + System.currentTimeMillis() + "---";
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                OutputStream outputStream = con.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                String LINE_FEED = "\r\n";
                // file 추가
                String fileName = file.getName();
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();
                writer.append(LINE_FEED).flush();
                writer.append("--" + boundary + "--").append(LINE_FEED);
                writer.close();

                BufferedReader br = null;
                int responseCode = con.getResponseCode();
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    Log.e("Pose Estimation", "error!!!!!!! responseCode= " + responseCode);
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                if (br != null) {
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();

                    return response.toString();
                } else {
                    Log.e("Pose Estimation", "error !!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            progress.setIndeterminate(false);

            origin_Height = Double.parseDouble(getIntent().getStringExtra("height"));
            try {
                data = new HumanSkeleton(o);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(data.isOk()) {
                LShoulderToElbow = getDistance(data.getLeftshoulder(), data.getLeftelbow());
                RShoulderToElbow = getDistance(data.getRightshoulder(), data.getRightelbow());
                LElbowToWrist = getDistance(data.getLeftelbow(), data.getLeftwrist());
                RElbowToWrist = getDistance(data.getRightelbow(), data.getRightwrist());
                shoulderWidth = getDistance(data.getLeftshoulder(), data.getRightshoulder());
                LAnkleToknee = getDistance(data.getLeftankle(), data.getLeftknee());
                RAnkleToknee = getDistance(data.getRightankle(), data.getRightknee());
                LKneeToHip = getDistance(data.getLeftknee(), data.getLefthip());
                RKneeToHip = getDistance(data.getRightknee(), data.getRighthip());
                bodyDistance = getDistance(getCenter(data.getLefthip(), data.getRighthip()), getCenter(data.getLeftshoulder(), data.getRightshoulder()));

                //키 : 발목 중간부터 Top 까지
                height = getDistance(getCenter(data.getLeftankle(), data.getRightankle()), data.getTop());

                //진짜 길이 구하기
                origin_LShoulderToElbow = getOrigin(LShoulderToElbow);
                origin_bodyDistance = getOrigin(bodyDistance);
                origin_RShoulderToElbow = getOrigin(RShoulderToElbow);
                origin_LAnkleToknee = getOrigin(LAnkleToknee);
                origin_RAnkleToknee = getOrigin(RAnkleToknee);
                origin_LElbowToWrist = getOrigin(LElbowToWrist);
                origin_RElbowToWrist = getOrigin(RElbowToWrist);
                origin_LKneeToHip = getOrigin(LKneeToHip);
                origin_RKneeToHip = getOrigin(RKneeToHip);
                origin_shoulderWidth = getOrigin(shoulderWidth);

                Intent intent = new Intent(PosenetActivity.this, ModelAdjustActivity.class);

                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("height",getIntent().getStringExtra("height"));
                intent.putExtra("skeleton", data);

                startActivity(intent);
            }
            else {
                Toast.makeText(PosenetActivity.this, "다시 한번 찍어주세요", Toast.LENGTH_SHORT).show();
                finish();
            }

            // TODO: Spinner 내리기
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posenet);

        progress = findViewById(R.id.progress);

        Intent intent = getIntent();
        String path = intent.getStringExtra("img");

        PoseEstimationTask task = new PoseEstimationTask();
        task.execute(new File(path));

        // 1. 불러온 인텐트 값을 이용해서 파일을 바이너리로 읽는다.
        // 2. Http Connection  을 열어서  Naver 로 결과를 받아온다.
        // 3. 받아온 결과를 출력한다.

    }

    private void resize(File inFile, File outFile){
        try {
            Bitmap b = BitmapFactory.decodeFile(inFile.getAbsolutePath());
            Bitmap out = Bitmap.createScaledBitmap(b, b.getWidth()/4, b.getHeight()/4, false);

            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(outFile);
                out.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();
                b.recycle();
                out.recycle();
            } catch (Exception e) {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getDistance (HumanSkeleton.Point a, HumanSkeleton.Point b) {
        return Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
    }

    public HumanSkeleton.Point getCenter(HumanSkeleton.Point a, HumanSkeleton.Point b) {
        HumanSkeleton.Point point = new HumanSkeleton.Point(0, 0);
        point.x = (a.x + b.x)/2;
        point.y = (a.y + b.y)/2;
        return point;
    }

    public double getOrigin(double X) {
        return (origin_Height * X)/height;
    }

    public double getAve(double a, double b) {
        return (a + b)/2;
    }

}
