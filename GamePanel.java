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
 * DUUUUUUY
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;


    Disk[] allDisks = new Disk[5];
    Point[] allPoints = new Point[5];
    //Peg stackOne = new Peg(xleft,xright);
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
    int[] yPositions= {(int)(height*0.83),(int)(height*0.76),(int)(height*0.69),(int)(height*0.62),(int)(height*0.55)};

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        hold = false;
        standOne = new Disk(new Rect(100,100,(int)(width/3.1573),(int)(height*0.125)), Color.rgb(0,0,0), 0);
        standTwo = new Disk(new Rect(100,100,(int)(width/3.1573),(int)(height*0.125)), Color.rgb(0,0,0), 0);
        standThree = new Disk(new Rect(100,100,(int)(width/3.1573),(int)(height*0.125)), Color.rgb(0,0,0), 0);
        pointOne = new Point((int)(width*0.1664), (int)(height*0.9));
        pointTwo = new Point((int)(width*0.5),(int)(height*0.9));
        pointThree = new Point((int)(width*0.8353), (int)(height*0.9));
        allDisks[0] = new Disk(new Rect(100,100,(int)(width*0.2745),(int)(height*0.125)), Color.rgb(0,255,0), 5);
        allDisks[1] = new Disk(new Rect(100,100,(int)(width*0.2323),(int)(height*0.125)), Color.rgb(255,0,0), 4);
        allDisks[2] = new Disk(new Rect(100,100,(int)(width*0.19),(int)(height*0.125)), Color.rgb(0,0,255), 3);
        allDisks[3] = new Disk(new Rect(100,100,(int)(width*0.1478),(int)(height*0.125)), Color.rgb(255,255,0), 2);
        allDisks[4] = new Disk(new Rect(100,100,(int)(width*0.1056),(int)(height*0.125)), Color.rgb(0,255,255), 1);
        allPoints[0] = new Point((int)(width*0.1664),(int)(height*0.83));
        allPoints[1] = new Point((int)(width*0.1664),(int)(height*0.76));          //WTF
        allPoints[2] = new Point((int)(width*0.1664),(int)(height*0.69));
        allPoints[3] = new Point((int)(width*0.1664),(int)(height*0.62));
        allPoints[4] = new Point((int)(width*0.1664),(int)(height*0.55));
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
                finalY = (int)event.getY();             //Do snap to position here
            //  allPoints[currentDisk].set((int) event.getX(), (int) event.getY())
                originX = 0;
                originY = 0;
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
