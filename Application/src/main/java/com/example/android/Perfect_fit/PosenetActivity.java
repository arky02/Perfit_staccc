package com.example.android.Perfect_fit;

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

            Intent intent = new Intent(PosenetActivity.this, MainActivity.class);
            intent.putExtra("posenet", o);
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.putExtra("height",getIntent().getStringExtra("height"));
            Log.e("pose_0", o);
            startActivity(intent);
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
}
