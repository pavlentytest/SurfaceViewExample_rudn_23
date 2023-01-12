package ru.samsung.itschool.mdev.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    // указатель на SurfaceView
    private SurfaceHolder surfaceHolder;
    // время перерисовки
    private long redrawTime;

    public MyThread(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); // сглаживание
    }

    // timestamp
    // Unixtime - 01/01/1970

    public long getTime() {
        return System.nanoTime()*1000; // мс
    }

    @Override
    public void run() {
        Canvas canvas; // null
        while(true) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 500000) {
                continue;
            }
            // блокировка Canvas
            canvas = surfaceHolder.lockCanvas();
            // отрисовка
            drawCircle(canvas);
            // разблокировка и отправка
            surfaceHolder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas canvas) {
        int x = canvas.getWidth()/2;
        int y = canvas.getHeight()/2;
        canvas.drawColor(Color.parseColor("#000000")); // черный
        canvas.drawCircle(x,y, (float)(500*Math.random()), paint);
    }
}
