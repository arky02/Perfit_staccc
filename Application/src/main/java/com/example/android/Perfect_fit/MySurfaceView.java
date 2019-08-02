package com.example.android.Perfect_fit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    SurfaceHolder surfaceHolder;
    RenderingThread renderingThread;
    HumanSkeleton humanSkeleton;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        renderingThread = new MySurfaceView.RenderingThread();
    }

    public MySurfaceView(Context context, HumanSkeleton skeleton) {
        this(context);
        this.humanSkeleton = skeleton;
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
            while(true) {
                canvas = surfaceHolder.lockCanvas();
                try{
                    synchronized (surfaceHolder) {
                        Rect src = new Rect(0, 0, img.getWidth(), img.getHeight());
                        Rect dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                        //left, top, right, bottom

                        canvas.drawBitmap(img, src, dst, paint);
                    }
                }finally {
                    if(canvas == null)return;
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                paint.setColor(Color.BLACK);
//                canvas.drawPoint(x좌표, y좌표, paint);
            }
        }
    }
}
