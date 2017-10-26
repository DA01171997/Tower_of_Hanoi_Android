package com.example.jason.towers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by JASON on 10/25/2017.
 */

public class Disk implements GameObject {

    private Rect rectangle;
    private int color;

    private int currentX;
    private int currentY;

    public Disk(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point){                 //center of rectangle
        rectangle.set(point.x - rectangle.width()/2,point.y - rectangle.height()/2,point.x + rectangle.width()/2, point.y + rectangle.height()/2);
        currentX = point.x;
        currentY = point.y;
    }

    public int getX(){
        return currentX;
    }
    public int getY(){
        return currentY;
    }
}
