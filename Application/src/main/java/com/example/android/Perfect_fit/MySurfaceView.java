package com.example.android.Perfect_fit;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    Canvas canvas = null;
    SurfaceHolder surfaceHolder;
    RenderingThread renderingThread;
    private HumanSkeleton humanSkeleton;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float x1, x2, Head = 0, Bottom = 0;
    int right, bottom, w, h;
    private Bitmap img_toe, img_head, resize_img_toe, resize_img_head;

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        renderingThread = new MySurfaceView.RenderingThread();
        Resources r = context.getResources();
        img_toe = BitmapFactory.decodeResource(r, R.drawable.toebar);
        img_head = BitmapFactory.decodeResource(r, R.drawable.headbar);
        resize_img_toe = Bitmap.createScaledBitmap(img_toe, img_toe.getWidth() * 5 , img_toe.getHeight() * 4, true);
        resize_img_head = Bitmap.createScaledBitmap(img_head, img_head.getWidth() * 5, img_head.getHeight() * 4, true);
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
        File mfile = new File("/storage/emulated/0/Android/data/com.example.android.Perfect_fit/files/pic.jpg");
        Bitmap img;

        public RenderingThread() {
            img = BitmapFactory.decodeFile(mfile.getAbsolutePath());
        }

        @Override
        public void run() {
            while(true && humanSkeleton != null) {
                canvas = surfaceHolder.lockCanvas();
                try{
                    synchronized (surfaceHolder) {
                        w = img.getWidth();
                        h = img.getHeight();
                        x1 = canvas.getWidth() / 5;
                        x2 = canvas.getWidth() - canvas.getWidth() / 5;

                        right = canvas.getWidth();
                        bottom = h * canvas.getWidth() / w;

                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                        Rect src = new Rect(0, 0, w, h);
                        Rect dst = new Rect(0, 0, right, bottom);
                        canvas.drawBitmap(img, src, dst, paint);

                        paint.setStrokeWidth(8f);
                        paint.setColor(Color.RED);

                        if(Head == 0 && Bottom == 0) {
                            Head = (float)humanSkeleton.getTop().y * bottom;
                            Bottom = (float)humanSkeleton.getRightankle().y * bottom;
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
        Rect src = new Rect(0, 0, w, h);
        Rect dst = new Rect(0, y, right, y + bottom);
        canvas.drawBitmap(bitmap, src, dst, paint);
        Log.e("draw Check", "check");
    }

    boolean TopCheck = false;
    boolean BottomCheck = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getY() >= Head - 60 && event.getY() <= Head + 60) {
                    TopCheck = true;
                }
                else if(event.getY() >= Bottom - 60 && event.getY() <= Bottom + 60) {
                    BottomCheck = true;
                }
                else {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(TopCheck) {
                    if(event.getY() >= Head -30 && event.getY() <= Head + 30) {
                        Head = event.getY();
                    }
                }
                else if(BottomCheck) {
                    if(event.getY() >= Bottom - 150 && event.getY() <= Bottom + 150) {
                        Bottom = event.getY();
                    }
                }
                else return true;

                break;

            case MotionEvent.ACTION_UP:
                TopCheck = false;
                BottomCheck = false;
        }
        return true;
    }
}
