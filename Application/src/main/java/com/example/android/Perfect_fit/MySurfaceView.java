package com.example.android.Perfect_fit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    SurfaceHolder surfaceHolder;
    RenderingThread renderingThread;
    private HumanSkeleton humanSkeleton;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        renderingThread = new MySurfaceView.RenderingThread();
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

     class RenderingThread extends Thread {
        File mfile = new File("/storage/emulated/0/Android/data/com.example.android.Perfect_fit/files/pic.jpg");
        Bitmap img;

        public RenderingThread() {
            img = BitmapFactory.decodeFile(mfile.getAbsolutePath());
        }

        @Override
        public void run() {
            Canvas canvas = null;

            while(true && humanSkeleton != null) {
                canvas = surfaceHolder.lockCanvas();
                try{
                    synchronized (surfaceHolder) {
                        int w = img.getWidth();
                        int h = img.getHeight();
                        float x,y;

                        int right = canvas.getWidth();
                        int bottom = h * canvas.getWidth() / w;

                        Rect src = new Rect(0, 0, w, h);
                        Rect dst = new Rect(0, 0, right, bottom);
                        canvas.drawBitmap(img, src, dst, paint);

                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(25);
                        paint.setColor(Color.RED);
                        x = (float)humanSkeleton.getNeck().x * right;
                        y = (float)humanSkeleton.getNeck().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLeftshoulder().x * right;
                        y = (float)humanSkeleton.getLeftshoulder().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRightshoulder().x * right;
                        y = (float)humanSkeleton.getRightshoulder().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLeftelbow().x * right;
                        y = (float)humanSkeleton.getLeftelbow().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRightelbow().x * right;
                        y = (float)humanSkeleton.getRightelbow().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLeftwrist().x * right;
                        y = (float)humanSkeleton.getLeftwrist().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRightwrist().x * right;
                        y = (float)humanSkeleton.getRightwrist().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLefthip().x * right;
                        y = (float)humanSkeleton.getLefthip().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRighthip().x * right;
                        y = (float)humanSkeleton.getRighthip().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLeftknee().x * right;
                        y = (float)humanSkeleton.getLeftknee().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRightknee().x * right;
                        y = (float)humanSkeleton.getRightknee().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getLeftankle().x * right;
                        y = (float)humanSkeleton.getLeftankle().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getRightankle().x * right;
                        y = (float)humanSkeleton.getRightankle().y * bottom;
                        canvas.drawPoint(x, y, paint);
                        x = (float)humanSkeleton.getTop().x * right;
                        y = (float)humanSkeleton.getTop().y * bottom;
                        canvas.drawPoint(x, y, paint);
                    }
                }finally {
                    if(canvas == null)return;
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }
}
