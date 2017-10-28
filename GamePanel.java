package com.example.jason.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    //Peg stackOne = new Peg(xleft,xright);                         //figure out these coords and add them. left+right boundaries
    //Peg stackTwo = new Peg(xleft,xright);
    //Peg stackThree = new Peg(xleft,xright);
    Disk standOne;
    Disk standTwo;
    Disk standThree;
    Point pointOne;
    Point pointTwo;
    Point pointThree;
    int currentDisk = 0;
    int originX = 0;
    int originY = 0;
    boolean hold;

    int width = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;


    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        hold = false;
        standOne = new Disk(new Rect(100,100,(int)(width/3.1573),(height/8)), Color.rgb(0,0,0), 0);
        standTwo = new Disk(new Rect(100,100,(int)(width/3.1573),(height/8)), Color.rgb(0,0,0), 0);
        standThree = new Disk(new Rect(100,100,(int)(width/3.1573),(height/8)), Color.rgb(0,0,0), 0);
        pointOne = new Point(394, 1300);
        pointTwo = new Point(1184,1300);
        pointThree = new Point(1973, 1300);
        allDisks[0] = new Disk(new Rect(100,100,(int)(width/3.643),(height/8)), Color.rgb(0,255,0), 5);
        allDisks[1] = new Disk(new Rect(100,100,550,(height/8)), Color.rgb(255,0,0), 4);
        allDisks[2] = new Disk(new Rect(100,100,450,(height/8)), Color.rgb(0,0,255), 3);
        allDisks[3] = new Disk(new Rect(100,100,350,(height/8)), Color.rgb(255,255,0), 2);
        allDisks[4] = new Disk(new Rect(100,100,250,(height/8)), Color.rgb(0,255,255), 1);
        allPoints[0] = new Point(394,(int)(height/1.3));
        allPoints[1] = new Point((width/6),(int)(height/1.4));          //WTF
        allPoints[2] = new Point((width/6),(int)(height/1.5));
        allPoints[3] = new Point(394,800);
        allPoints[4] = new Point(394,1000);
        for(int i = 0; i < 5; i++){
            //stackOne.pushStack(allDisks[i]);
        }
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

        int finalX = 0;
        int finalY = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(!hold) {
                    originX = (int) event.getX();
                    originY = (int) event.getY();
                }
                for(int i = 0; i < 5; i++) {
                    if ( allDisks[i].isInDisk(originX, originY)) {
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
                int originX = 0;
                int originY = 0;
                currentDisk = 0;
                break;
        }


        return true;        //change to other one later
        //return super.onTouchEvent(event);
    }

    public void update(){
        for(int i = 0; i < 5; i++) {
            allDisks[i].update(allPoints[i]);
        }
        standOne.update(pointOne);
        standTwo.update(pointTwo);
        standThree.update(pointThree);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        standOne.draw(canvas);
        standTwo.draw(canvas);
        standThree.draw(canvas);
        for(int i = 0; i < 5; i++) {
            allDisks[i].draw(canvas);
        }
    }

}
