package com.example.jason.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by JASON on 10/25/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    Disk[] allDisks = new Disk[5];
    Point[] allPoints = new Point[5];
    boolean hold;
    private Disk one;
    private Point onePoint;

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        hold = false;
        allDisks[0] = new Disk(new Rect(100,100,200,500), Color.rgb(0,255,0));
        allDisks[1] = new Disk(new Rect(100,100,200,500), Color.rgb(255,0,0));
        allDisks[2] = new Disk(new Rect(100,100,200,500), Color.rgb(0,0,255));
        allDisks[3] = new Disk(new Rect(100,100,200,500), Color.rgb(0,255,0));
        allDisks[4] = new Disk(new Rect(100,100,200,500), Color.rgb(0,255,0));
        allPoints[0] = new Point(200,200);
        allPoints[1] = new Point(200,400);
        allPoints[2] = new Point(200,600);
        allPoints[3] = new Point(200,800);
        allPoints[4] = new Point(200,1000);
 //       one = new Disk(new Rect(100,100,200,500), Color.rgb(0,255,0));
 //       onePoint = new Point(200,200);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int originX = 0;
        int originY = 0;
        int finalX = 0;
        int finalY = 0;
        int currentDisk = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                originX = (int)event.getX();            //doesn't work, use flag
                originY = (int)event.getY();
                for(int i = 0; i < 5; i++) {
                    if (originX >= allDisks[currentDisk].getX() - 50 && originX <= allDisks[currentDisk].getX() + 50 && originY >= allDisks[currentDisk].getY() - 200 && originY <= allDisks[currentDisk].getY() + 200) {
                        hold = true;
                        currentDisk = i;
                    }
                }
                if(hold) {
                    allPoints[currentDisk].set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                hold = false;
                finalX = (int)event.getX();
                finalY = (int)event.getY();
                break;
        }


        return true;        //change to other one later
        //return super.onTouchEvent(event);
    }

    public void update(){
        for(int i = 0; i < 5; i++) {
            allDisks[i].update(allPoints[i]);
        }
//        one.update(onePoint);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        for(int i = 0; i < 5; i++) {
            allDisks[i].draw(canvas);
        }
    }
}
