package com.example.jahir.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class CanvasView extends View {
    public int height;
    public int width;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    int r,g,b;
    Random rnd = new Random();


    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK );
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void color(){
        r=(int) rnd.nextInt()*256;
        g=(int) rnd.nextInt()*256;
        b=(int) rnd.nextInt()*256;
        mPaint.setColor(Color.rgb(r,g,b) );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //color();
        canvas.drawPath(mPath, mPaint);
    }

    private void startTouch(float x, float y){
        //color();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y){
        //color();
        float dX = Math.abs(x - mX);
        float dY = Math.abs(y - mY);

        if (dX >= TOLERANCE || dY >= TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX) /2, (y +mY) /2);
            mX = x;
            mY = y;
        }
    }

    public void cleanCanvas(){
        mPath.reset();
        invalidate();
        color();
    }

    public void upTouch(){
        //color();
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}
