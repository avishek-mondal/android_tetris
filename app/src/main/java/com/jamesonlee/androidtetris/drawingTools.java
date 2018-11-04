package com.jamesonlee.androidtetris;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jameson on 07/06/2017.
 */

public class drawingTools{
    Canvas c;
    Paint blockPaint = new Paint();
    Paint borderPaint = new Paint();
    int bgColour;
    int blockSize;
    public void drawBlock(int x, int y) {
        //c.drawPoint(x,y,p);
        c.drawRect(x*blockSize,y*blockSize,(x+1)*blockSize,(y+1)*blockSize,blockPaint);
        c.drawRect(x*blockSize,y*blockSize,(x+1)*blockSize,(y+1)*blockSize,borderPaint);
    }
    public void clearBlock(int x, int y) {
        Paint tempPaint = new Paint();
        tempPaint.setColor(bgColour);
        tempPaint.setStyle(Paint.Style.FILL);
        c.drawRect(x*blockSize,y*blockSize,(x+1)*blockSize,(y+1)*blockSize,tempPaint);
        tempPaint.setStyle(Paint.Style.STROKE);
        c.drawRect(x*blockSize,y*blockSize,(x+1)*blockSize,(y+1)*blockSize,tempPaint);
    }
    public void setPaint(int a, int r, int g, int b) {
        blockPaint.setARGB(a, r, g, b);
    }
    public void setBorder(int a, int r, int g, int b) {
        borderPaint.setARGB(a, r, g, b);
        borderPaint.setStyle(Paint.Style.STROKE);
    }
    public void setFill(int a, int r, int g, int b) {
        bgColour = android.graphics.Color.argb(a, r, g, b);
    }
    public void fill() {
        c.drawColor(bgColour);
    }
    public void setCanvas(Canvas canvas) {
        c = canvas;
    }
    public void setBlockSize(int size) {
        blockSize = size;
    }

}
