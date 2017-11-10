package com.example.jason.towers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by JASON LIEU and DUY DO on 10/25/2017.
 */

public class Disk implements GameObject {

    private Rect rectangle;
    private int color;
    private int number;


    public Disk(Rect rectangle, int color,int number){
        this.rectangle = rectangle;
        this.color = color;
        this.number = number;
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
        //  currentX = point.x;// currentY = point.y;
    }
    public int getNum(){ return number;}
    public boolean isInDisk(int x, int y){
        return rectangle.contains(x,y);
    }
    public Paint getColor(){
        Paint paint = new Paint();
        paint.setColor(color);
        return paint;
    }
}
