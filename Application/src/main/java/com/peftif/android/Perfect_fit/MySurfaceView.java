package com.peftif.android.Perfect_fit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.peftif.android.Perfect_fit.PoseEstimation.HumanSkeleton;

import java.io.File;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    Canvas canvas = null;
    SurfaceHolder surfaceHolder;
    RenderingThread renderingThread;
    private HumanSkeleton humanSkeleton;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float Head = 0, Bottom = 0;
    float location[][] = new float[12][2];
    int right, bottom, w, h;
    private Bitmap img_toe, img_head, img_dot, resize_img_toe, resize_img_head, left_shoulder, right_shoulder, left_elbow, right_elbow, left_wrist, right_wrist,
            left_hip, right_hip, left_knee, right_knee, resize_img_Lshoulder, resize_img_Rshoulder, resize_img_Lelbow, resize_img_Relbow, resize_img_Lwrist,
            resize_img_Rwrist, resize_img_Lhip, resize_img_Rhip, resize_img_Lknee, resize_img_Rknee;
    private Bitmap[] resize_img_dot = new Bitmap[12];
    boolean isTouched[] = new boolean[]{false, false, false, false, false, false, false, false, false, false};

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        renderingThread = new MySurfaceView.RenderingThread();
        Resources r = context.getResources();
        img_toe = BitmapFactory.decodeResource(r, R.drawable.toebar_fin);
        img_head = BitmapFactory.decodeResource(r, R.drawable.headbar_fin);
        img_dot = BitmapFactory.decodeResource(r, R.drawable.pink_dot);
        left_shoulder = BitmapFactory.decodeResource(r, R.drawable.left_shoulder_box);
        right_shoulder = BitmapFactory.decodeResource(r, R.drawable.right_shoulder_box);
        left_elbow = BitmapFactory.decodeResource(r, R.drawable.left_elbow_box);
        right_elbow = BitmapFactory.decodeResource(r, R.drawable.right_elbow_box);
        left_wrist = BitmapFactory.decodeResource(r, R.drawable.left_wrist_box);
        right_wrist = BitmapFactory.decodeResource(r, R.drawable.right_wrist_box);
        left_hip = BitmapFactory.decodeResource(r, R.drawable.left_hip_box);
        right_hip = BitmapFactory.decodeResource(r, R.drawable.right_hip_box);
        left_knee = BitmapFactory.decodeResource(r, R.drawable.left_knee_box);
        right_knee = BitmapFactory.decodeResource(r, R.drawable.right_knee_box);

        resize_img_Lshoulder = Bitmap.createScaledBitmap(left_shoulder, left_shoulder.getWidth() , left_shoulder.getHeight(), true);
        resize_img_Rshoulder = Bitmap.createScaledBitmap(right_shoulder, right_shoulder.getWidth() , right_shoulder.getHeight(), true);
        resize_img_Lelbow = Bitmap.createScaledBitmap(left_elbow, left_elbow.getWidth() , left_elbow.getHeight(), true);
        resize_img_Relbow = Bitmap.createScaledBitmap(right_elbow, right_elbow.getWidth() , right_elbow.getHeight(), true);
        resize_img_Lwrist = Bitmap.createScaledBitmap(left_wrist, left_wrist.getWidth() , left_wrist.getHeight(), true);
        resize_img_Rwrist = Bitmap.createScaledBitmap(right_wrist, right_wrist.getWidth() , right_wrist.getHeight(), true);
        resize_img_Lhip = Bitmap.createScaledBitmap(left_hip, left_hip.getWidth() , left_hip.getHeight(), true);
        resize_img_Rhip = Bitmap.createScaledBitmap(right_hip, right_hip.getWidth() , right_hip.getHeight(), true);
        resize_img_Lknee = Bitmap.createScaledBitmap(left_knee, left_knee.getWidth() , left_knee.getHeight(), true);
        resize_img_Rknee = Bitmap.createScaledBitmap(right_knee, right_knee.getWidth() , right_knee.getHeight(), true);
        resize_img_toe = Bitmap.createScaledBitmap(img_toe, img_toe.getWidth() , img_toe.getHeight(), true);
        resize_img_head = Bitmap.createScaledBitmap(img_head, img_head.getWidth(), img_head.getHeight() , true);

        // 0 : 왼쪽 발목, 1 : 오른쪽 발목, 2 : 왼쪽 무릎, 3 : 오른쪽 무릎, 4 : 왼쪽 엉덩이, 5 : 오른쪽 엉덩이
        // 6 : 왼쪽 어깨, 7 : 오른쪽 어깨, 8 : 왼쪽 팔꿈치, 9 : 오른쪽 팔꿈치, 10 : 왼쪽 손목, 11 : 오른쪽 손목
        for (int i = 0; i < 12; i++) {
            resize_img_dot[i] = Bitmap.createScaledBitmap(img_dot, img_dot.getWidth(), img_dot.getHeight(), true);
        }
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setHumanSkeleton(HumanSkeleton humanSkeleton) {
        this.humanSkeleton = humanSkeleton;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderingThread.start();
    }

    public double getDistance() {
        double top, toe;
        top = (double)Head / (double)bottom;
        toe = (double)Bottom / (double)bottom;

        return toe - top;
    }

    // 0 : 왼쪽 발목, 1 : 오른쪽 발목, 2 : 왼쪽 무릎, 3 : 오른쪽 무릎, 4 : 왼쪽 엉덩이, 5 : 오른쪽 엉덩이
    // 6 : 왼쪽 어깨, 7 : 오른쪽 어깨, 8 : 왼쪽 팔꿈치, 9 : 오른쪽 팔꿈치, 10 : 왼쪽 손목, 11 : 오른쪽 손목


    public double getLShoulderToElbow() {
        return Math.sqrt((location[6][0]/(double)right - location[8][0]/(double)right) * (location[6][0]/(double)right - location[8][0]/(double)right) +
                (location[6][1]/(double)bottom - location[8][1]/(double)bottom) * (location[6][1]/(double)bottom - location[8][1]/(double)bottom));
    }

    public double getbodyDistance() {
        return Math.sqrt(( (location[4][0]/(double)right + location[5][0]/(double)right) / 2 - (location[6][0]/(double)right + location[7][0]/(double)right) / 2) * ((location[4][0]/(double)right + location[5][0]/(double)right) / 2 - (location[6][0]/(double)right + location[7][0]/(double)right) / 2)
                + ((location[4][1]/(double)bottom + location[5][1]/(double)bottom) / 2 - (location[6][1]/(double)bottom + location[7][1]/(double)bottom) / 2) * ((location[4][0]/(double)right + location[5][0]/(double)right) / 2 - (location[6][0]/(double)right + location[7][0]/(double)right) / 2));
    }//이거 이상함

    public double getRShoulderToElbow() {
        return Math.sqrt((location[7][0]/(double)right - location[9][0]/(double)right) * (location[7][0]/(double)right - location[9][0]/(double)right) + (location[7][1]/(double)bottom - location[9][1]/(double)bottom) * (location[7][1]/(double)bottom - location[9][1]/(double)bottom));
    }

    public double getLAnkleToknee() {
        return Math.sqrt((location[0][0]/(double)right - location[2][0]/(double)right) * (location[0][0]/(double)right - location[2][0]/(double)right) + (location[0][1]/(double)bottom - location[2][1]/(double)bottom) * (location[0][1]/(double)bottom - location[2][1]/(double)bottom));
    }

    public double getRAnkleToknee() {
        return Math.sqrt((location[1][0]/(double)right - location[3][0]/(double)right) * (location[1][0]/(double)right - location[3][0]/(double)right) + (location[1][1]/(double)bottom - location[3][1]/(double)bottom) * (location[1][1]/(double)bottom - location[3][1]/(double)bottom));
    }

    public double getLElbowToWrist() {
        return Math.sqrt((location[8][0]/(double)right - location[10][0]/(double)right) * (location[8][0]/(double)right - location[10][0]/(double)right) + (location[8][1]/(double)bottom - location[10][1]/(double)bottom) * (location[8][1]/(double)bottom - location[10][1]/(double)bottom));
    }

    public double getRElbowToWrist() {
        return Math.sqrt((location[9][0]/(double)right - location[11][0]/(double)right) * (location[9][0]/(double)right - location[11][0]/(double)right) + (location[9][1]/(double)bottom - location[11][1]/(double)bottom) * (location[9][1]/(double)bottom - location[11][1]/(double)bottom));
    }

    public double getLKneeToHip() {
        return Math.sqrt((location[2][0]/(double)right - location[4][0]/(double)right) * (location[2][0]/(double)right - location[4][0]/(double)right) + (location[2][1]/(double)bottom - location[4][1]/(double)bottom) * (location[2][1]/(double)bottom - location[4][1]/(double)bottom));
    }

    public double getRKneeToHip() {
        return Math.sqrt((location[3][0]/(double)right - location[5][0]/(double)right) * (location[3][0]/(double)right - location[5][0]/(double)right) + (location[3][1]/(double)bottom - location[5][1]/(double)bottom) * (location[3][1]/(double)bottom - location[5][1]/(double)bottom));
    }

    public double getShoulderWidth() {
        return Math.sqrt((location[6][0]/(double)right - location[7][0]/(double)right) * (location[6][0]/(double)right - location[7][0]/(double)right) + (location[6][1]/(double)bottom - location[7][1]/(double)bottom) * (location[6][1]/(double)bottom - location[7][1]/(double)bottom));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            renderingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            surfaceHolder.setFixedSize(right, bottom);
        }
    };

     class RenderingThread extends Thread {
        File mfile = new File("/storage/emulated/0/Android/data/com.peftif.android.Perfect_fit/files/pic.jpg");
        Bitmap img;

        public RenderingThread() {
            img = BitmapFactory.decodeFile(mfile.getAbsolutePath());
            Log.e("check img", ""+img);
        }

        @Override
        public void run() {
            while(true && humanSkeleton != null) {
                canvas = surfaceHolder.lockCanvas();
                try{
                    synchronized (surfaceHolder) {
                        w = img.getWidth();
                        h = img.getHeight();

                        right = canvas.getWidth();
                        bottom = h * canvas.getWidth() / w;

                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                        Rect src = new Rect(0, 0, w, h);
                        Rect dst = new Rect(0, 0, right, bottom);
                        canvas.drawBitmap(img, src, dst, paint);

                        paint.setStrokeWidth(8f);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.RED);

                        if(Head == 0 && Bottom == 0) {
                            Head = (float)humanSkeleton.getTop().y * bottom;
                            Bottom = (float)humanSkeleton.getRightankle().y * bottom;
                            location[0][0] = (float) (humanSkeleton.getLeftankle().x * right); //왼쪽 발목
                            location[0][1] = (float) (humanSkeleton.getLeftankle().y * bottom);
                            location[1][0] = (float) (humanSkeleton.getRightankle().x * right); //오른쪽 무릎
                            location[1][1] = (float) (humanSkeleton.getRightankle().y * bottom);
                            location[2][0] = (float) (humanSkeleton.getLeftknee().x * right); //왼쪽 무릎
                            location[2][1] = (float) (humanSkeleton.getLeftknee().y * bottom);
                            location[3][0] = (float) (humanSkeleton.getRightknee().x * right); //오른쪽 무릎
                            location[3][1] = (float) (humanSkeleton.getRightknee().y * bottom);
                            location[4][0] = (float) (humanSkeleton.getLefthip().x * right); //왼쪽 엉덩이
                            location[4][1] = (float) (humanSkeleton.getLefthip().y * bottom);
                            location[5][0] = (float) (humanSkeleton.getRighthip().x * right);//오른쪽 엉덩이
                            location[5][1] = (float) (humanSkeleton.getRighthip().y * bottom);
                            location[6][0] = (float) (humanSkeleton.getLeftshoulder().x * right); //왼쪽 어깨
                            location[6][1] = (float) (humanSkeleton.getLeftshoulder().y * bottom);
                            location[7][0] = (float) (humanSkeleton.getRightshoulder().x * right); //오른쪽 어깨
                            location[7][1] = (float) (humanSkeleton.getRightshoulder().y * bottom);
                            location[8][0] = (float) (humanSkeleton.getLeftelbow().x * right); //왼쪽 팔꿈치
                            location[8][1] = (float) (humanSkeleton.getLeftelbow().y * bottom);
                            location[9][0] = (float) (humanSkeleton.getRightelbow().x * right);//오른쪽 팔꿈치
                            location[9][1] = (float) (humanSkeleton.getRightelbow().y * bottom);
                            location[10][0] = (float) (humanSkeleton.getLeftwrist().x * right); //왼쪽 손목
                            location[10][1] = (float) (humanSkeleton.getLeftwrist().y * bottom);
                            location[11][0] = (float) (humanSkeleton.getRightwrist().x * right);//오른쪽 손목
                            location[11][1] = (float) (humanSkeleton.getRightwrist().y * bottom);
                        }

                        // 0 : 왼쪽 발목, 1 : 오른쪽 발목, 2 : 왼쪽 무릎, 3 : 오른쪽 무릎, 4 : 왼쪽 엉덩이, 5 : 오른쪽 엉덩이
                        // 6 : 왼쪽 어깨, 7 : 오른쪽 어깨, 8 : 왼쪽 팔꿈치, 9 : 오른쪽 팔꿈치, 10 : 왼쪽 손목, 11 : 오른쪽 손목

                        DrawDot(resize_img_dot[2], location[2][0], location[2][1]);
                        DrawDot(resize_img_dot[3], location[3][0], location[3][1]);
                        DrawDot(resize_img_dot[4], location[4][0], location[4][1]);
                        DrawDot(resize_img_dot[5], location[5][0], location[5][1]);
                        DrawDot(resize_img_dot[6], location[6][0], location[6][1]);
                        DrawDot(resize_img_dot[7], location[7][0], location[7][1]);
                        DrawDot(resize_img_dot[8], location[8][0], location[8][1]);
                        DrawDot(resize_img_dot[9], location[9][0], location[9][1]);
                        DrawDot(resize_img_dot[10], location[10][0], location[10][1]);
                        DrawDot(resize_img_dot[11], location[11][0], location[11][1]);

                        if(isTouched[0]) {
                            DrawTextbox(resize_img_Lknee, location[2][0], location[2][1]);
                        }
                        if(isTouched[1]) {
                            DrawTextbox(resize_img_Rknee, location[3][0], location[3][1]);
                        }
                        if(isTouched[2]) {
                            DrawTextbox(resize_img_Lhip, location[4][0], location[4][1]);
                        }
                        if(isTouched[3]) {
                            DrawTextbox(resize_img_Rhip, location[5][0], location[5][1]);
                        }
                        if(isTouched[4]) {
                            DrawTextbox(resize_img_Lshoulder, location[6][0], location[6][1]);
                        }
                        if(isTouched[5]) {
                            DrawTextbox(resize_img_Rshoulder, location[7][0], location[7][1]);
                        }
                        if(isTouched[6]) {
                            DrawTextbox(resize_img_Lelbow, location[8][0], location[8][1]);
                        }
                        if(isTouched[7]) {
                            DrawTextbox(resize_img_Relbow, location[9][0], location[9][1]);
                        }
                        if(isTouched[8]) {
                            DrawTextbox(resize_img_Lwrist, location[10][0], location[10][1]);
                        }
                        if(isTouched[9]) {
                            DrawTextbox(resize_img_Rwrist, location[11][0], location[11][1]);
                        }

                        DrawImg(resize_img_toe, (int)Bottom);
                        DrawImg(resize_img_head, (int)Head);

                    }
                }finally {
                    if(canvas == null)return;
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void DrawImg(Bitmap bitmap, int y) {
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(0, y, right, y + (int)((double)right/ bitmap.getWidth() * bitmap.getHeight()));
        canvas.drawBitmap(bitmap, src, dst, paint);
    }

    public void DrawDot(Bitmap bitmap, float x, float y) {
         float Nx, Ny;
         Nx = x - bitmap.getWidth() / 2;
         Ny = y - bitmap.getHeight();
         canvas.drawBitmap(bitmap, Nx, Ny, paint);
    }

    public void DrawTextbox(Bitmap bitmap, float x, float y) {
         float Nx, Ny;
         Nx = x - bitmap.getWidth() / 2 + 10;
         Ny = y - bitmap.getHeight() - 10;
         canvas.drawBitmap(bitmap, Nx, Ny, paint);
    }

    boolean TopCheck = false;
    boolean BottomCheck = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getY() >= Head - 80 && event.getY() <= Head + 80) {
                    TopCheck = true;
                }
                else if(event.getY() >= Bottom - 80 && event.getY() <= Bottom + 80) {
                    BottomCheck = true;
                }
                else if(event.getX() >= location[2][0] - 20 && event.getX() <= location[2][0] + 20 && event.getY() >= location[2][1] - 20 && event.getY() <= location[2][1] + 20) {
                    isTouched[0] = true;
                }
                else if(event.getX() >= location[3][0] - 20 && event.getX() <= location[3][0] + 20 && event.getY() >= location[3][1] - 20 && event.getY() <= location[3][1] + 20) {
                    isTouched[1] = true;
                }
                else if(event.getX() >= location[4][0] - 20 && event.getX() <= location[4][0] + 20 && event.getY() >= location[4][1] - 20 && event.getY() <= location[4][1] + 20) {
                    isTouched[2] = true;
                }
                else if(event.getX() >= location[5][0] - 20 && event.getX() <= location[5][0] + 20 && event.getY() >= location[5][1] - 20 && event.getY() <= location[5][1] + 20) {
                    isTouched[3] = true;
                }
                else if(event.getX() >= location[6][0] - 20 && event.getX() <= location[6][0] + 20 && event.getY() >= location[6][1] - 20 && event.getY() <= location[6][1] + 20) {
                    isTouched[4] = true;
                }
                else if(event.getX() >= location[7][0] - 20 && event.getX() <= location[7][0] + 20 && event.getY() >= location[7][1] - 20 && event.getY() <= location[7][1] + 20) {
                    isTouched[5] = true;
                }
                else if(event.getX() >= location[8][0] - 20 && event.getX() <= location[8][0] + 20 && event.getY() >= location[8][1] - 20 && event.getY() <= location[8][1] + 20) {
                    isTouched[6] = true;
                }
                else if(event.getX() >= location[9][0] - 20 && event.getX() <= location[9][0] + 20 && event.getY() >= location[9][1] - 20 && event.getY() <= location[9][1] + 20) {
                    isTouched[7] = true;
                }
                else if(event.getX() >= location[10][0] - 20 && event.getX() <= location[10][0] + 20 && event.getY() >= location[10][1] - 20 && event.getY() <= location[10][1] + 20) {
                    isTouched[8] = true;
                }
                else if(event.getX() >= location[11][0] - 20 && event.getX() <= location[11][0] + 20 && event.getY() >= location[11][1] - 20 && event.getY() <= location[11][1] + 20) {
                    isTouched[9] = true;
                }
                else {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(TopCheck) {
                    if(event.getY() >= Head -160 && event.getY() <= Head + 160) {
                        Head = event.getY();
                    }
                }
                else if(BottomCheck) {
                    if(event.getY() >= Bottom - 160 && event.getY() <= Bottom + 160) {
                        Bottom = event.getY();
                    }
                }
                else if(isTouched[0]) {
                    location[2][0] = event.getX();
                    location[2][1] = event.getY();
                }
                else if(isTouched[1]) {
                    location[3][0] = event.getX();
                    location[3][1] = event.getY();
                }
                else if(isTouched[2]) {
                    location[4][0] = event.getX();
                    location[4][1] = event.getY();
                }
                else if(isTouched[3]) {
                    location[5][0] = event.getX();
                    location[5][1] = event.getY();
                }
                else if(isTouched[4]) {
                    location[6][0] = event.getX();
                    location[6][1] = event.getY();
                }
                else if(isTouched[5]) {
                    location[7][0] = event.getX();
                    location[7][1] = event.getY();
                }
                else if(isTouched[6]) {
                    location[8][0] = event.getX();
                    location[8][1] = event.getY();
                }
                else if(isTouched[7]) {
                    location[9][0] = event.getX();
                    location[9][1] = event.getY();
                }
                else if(isTouched[8]) {
                    location[10][0] = event.getX();
                    location[10][1] = event.getY();
                }
                else if(isTouched[9]) {
                    location[11][0] = event.getX();
                    location[11][1] = event.getY();
                }
                else return true;
                break;

            case MotionEvent.ACTION_UP:
                TopCheck = false;
                BottomCheck = false;
                for (int i = 0; i < 10; i++) {
                    isTouched[i] = false;
                }
        }
        return true;
    }
}
