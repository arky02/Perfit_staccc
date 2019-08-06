package com.example.android.Perfect_fit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
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
    RectF rect_Top = new RectF();
    RectF rect_Bottom = new RectF();
    int right, bottom;

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

    public double getDistance() {
        double top, toe;
        top = (double)Head / (double)bottom;
        toe = (double)Bottom / (double)bottom;

        return top - toe;
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
            while(true && humanSkeleton != null) {
                canvas = surfaceHolder.lockCanvas();
                try{
                    synchronized (surfaceHolder) {
                        int w = img.getWidth();
                        int h = img.getHeight();
                        x1 = canvas.getWidth() / 5;
                        x2 = canvas.getWidth() - canvas.getWidth() / 5;

                        right = canvas.getWidth();
                        bottom = h * canvas.getWidth() / w;

                        Rect src = new Rect(0, 0, w, h);
                        Rect dst = new Rect(0, 0, right, bottom);
                        canvas.drawBitmap(img, src, dst, paint);

                        paint.setStrokeWidth(8f);
                        paint.setColor(Color.RED);

                        if(Head == 0 && Bottom == 0) {
                            Head = (float)humanSkeleton.getTop().y * bottom;
                            Bottom = (float)humanSkeleton.getRightankle().y * bottom;
                        }

                        DrawRect(rect_Top, Head);
                        DrawRect(rect_Bottom, Bottom);

                    }
                }finally {
                    if(canvas == null)return;
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void DrawRect(RectF rect, float y) {
        rect.set(x1, y, x2,y+10);
        canvas.drawRect(rect, paint);
    }

    boolean TopCheck = false;
    boolean BottomCheck = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getY() >= Head - 30 && event.getY() <= Head + 30) {
                    TopCheck = true;
                }
                else if(event.getY() >= Bottom - 30 && event.getY() <= Bottom + 30) {
                    BottomCheck = true;
                }
                else {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(TopCheck) {
                    Head = event.getY();
                }
                else if(BottomCheck) {
                    Bottom = event.getY();
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
