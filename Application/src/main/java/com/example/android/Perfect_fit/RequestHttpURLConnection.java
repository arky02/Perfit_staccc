package com.example.android.Perfect_fit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RequestHttpURLConnection {
    StringBuffer reqStr = new StringBuffer();
    String clientId = "v06knzwc61";
    String clientSecret = "2pFHWbm0xkEDtvWJWGVCrChhQvUOzJ7MY58vbDPZ";

    public void request(String imgFileUrl) {
        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = imgFileUrl;
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-pose/v1/estimate"; // 사람 인식
            URL url = null;
            try {
                url = new URL(apiURL);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            HttpURLConnection con = null;
            try { 
                con = (HttpURLConnection)url.openConnection();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            // multipart request
            String boundary = "---" + System.currentTimeMillis() + "---";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            OutputStream outputStream = null;
            try {
                outputStream = con.getOutputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String LINE_FEED = "\r\n";
            // file 추가
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(uploadFile);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while (true) {
                try {
                    if (!((bytesRead = inputStream.read(buffer)) != -1)) break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    outputStream.write(buffer, 0, bytesRead);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                outputStream.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            BufferedReader br = null;
            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(responseCode==200) { // 정상 호출
                try {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {  // 에러 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                try {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            String inputLine = null;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while (true) {
                    try {
                        if (!((inputLine = br.readLine()) != null)) break;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    response.append(inputLine);
                }
                try {
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
