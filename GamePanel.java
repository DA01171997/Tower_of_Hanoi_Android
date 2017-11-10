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
 * Created by JASON LIEU and DUY DO on 10/25/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    int width = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;
    Disk[] allDisks = new Disk[5];
    Point[] allPoints = new Point[5];
    Peg stackOne = new Peg(0,(int)(width*0.3333) - 1);
    Peg stackTwo = new Peg((int)(width*0.3333),(int)(width*0.6666) - 1);
    Peg stackThree = new Peg((int)(width*0.6666),width);
    Disk standOne;
    Disk standTwo;
    Disk standThree;
    Point pointOne;
    Point pointTwo;
    Point pointThree;
    int currentDisk = 0;
    int originX = 0;
    int originY = 0;
    int fromStack = 0;
    int to = 0;
    boolean hold;



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
        allPoints[1] = new Point((int)(width*0.1664),(int)(height*0.76));
        allPoints[2] = new Point((int)(width*0.1664),(int)(height*0.69));
        allPoints[3] = new Point((int)(width*0.1664),(int)(height*0.62));
        allPoints[4] = new Point((int)(width*0.1664),(int)(height*0.55));
        for(int i = 0; i < 5; i++){
            stackOne.pushStack(allDisks[i]);
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
        boolean canMove = false;
        int finalX = 0;
        int finalY = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(!hold) {
                    originX = (int) event.getX();
                    originY = (int) event.getY();
                }
                if( originX > 0 && originX < stackOne.getRight())
                {

                    for(int i = 0; i < 5; i++) {                            //loop through disk array
                        if ( allDisks[i].isInDisk(originX, originY))        //check which disk is being held
                        {
                            currentDisk = i;
                            if(allDisks[currentDisk].getNum() == stackOne.getTop().getNum())
                            {
                                hold = true;
                            }
                        }
                        if(hold)
                        {
                            allPoints[currentDisk].set((int)event.getX(), (int)event.getY());
                        }
                    }

                }
                if( originX > stackTwo.getLeft() && originX < stackTwo.getRight())
                {
                    for(int i = 0; i < 5; i++) {                            //loop through disk array
                        if ( allDisks[i].isInDisk(originX, originY))        //check which disk is being held
                        {
                            currentDisk = i;
                            if(allDisks[currentDisk].getNum() == stackTwo.getTop().getNum())
                            {
                                hold = true;
                            }
                        }
                        if(hold)
                        {
                            allPoints[currentDisk].set((int)event.getX(), (int)event.getY());
                        }
                    }

                }
                if( originX > stackThree.getLeft() && originX < stackThree.getRight())
                {
                    for(int i = 0; i < 5; i++) {                            //loop through disk array
                        if ( allDisks[i].isInDisk(originX, originY))        //check which disk is being held
                        {
                            currentDisk = i;
                            if(allDisks[currentDisk].getNum() == stackThree.getTop().getNum())
                            {
                                hold = true;                                //grab onto disk
                            }
                        }
                        if(hold)
                        {                                                   //update position with finger location
                            allPoints[currentDisk].set((int)event.getX(), (int)event.getY());
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:

                finalX = (int)event.getX();

                if( originX > 0 && originX < stackOne.getRight() && hold)
                {
                    fromStack = 1;
                }
                if( originX > stackTwo.getLeft() && originX < stackTwo.getRight() && hold)
                {
                    fromStack = 2;
                }
                if( originX > stackThree.getLeft() && originX < stackThree.getRight() && hold)
                {
                    fromStack = 3;
                }
                if( finalX > 0 && finalX < stackOne.getRight() && hold)
                {
                    to = 1;
                }
                if( finalX > stackTwo.getLeft() && finalX < stackTwo.getRight() && hold)
                {
                    to = 2;
                }
                if( finalX > stackThree.getLeft() && finalX < stackThree.getRight() && hold)
                {
                    to = 3;
                }
                if( to == 1){
                    if(stackOne.getSize() != 0) {
                        if (allDisks[currentDisk].getNum() < stackOne.getTop().getNum()) {
                            canMove = true;
                        }
                    }
                    else{
                        canMove = true;
                    }

                }
                if( to == 2){
                    if(stackTwo.getSize() != 0) {
                        if (allDisks[currentDisk].getNum() < stackTwo.getTop().getNum()) {
                            canMove = true;
                        }
                    }
                    else{
                        canMove = true;
                    }
                }
                if( to == 3){
                    if(stackThree.getSize() != 0) {
                        if (allDisks[currentDisk].getNum() < stackThree.getTop().getNum()) {
                            canMove = true;
                        }
                    }
                    else{
                        canMove = true;
                    }
                }
                if( to == fromStack){ canMove = true;}
                if(canMove && hold)
                {
                    if(fromStack == 1){stackOne.removeTop();}
                    if(fromStack == 2){stackTwo.removeTop();}
                    if(fromStack == 3){stackThree.removeTop();}
                    if(to == 1){
                        allPoints[currentDisk].set((int) (width*0.1664), yPositions[stackOne.getSize()]);
                        stackOne.pushStack(allDisks[currentDisk]);
                    }
                    if(to == 2){
                        allPoints[currentDisk].set((int)(width*0.5), yPositions[stackTwo.getSize()]);
                        stackTwo.pushStack(allDisks[currentDisk]);
                    }
                    if(to == 3){
                        allPoints[currentDisk].set((int)(width*0.8353), yPositions[stackThree.getSize()]);
                        stackThree.pushStack(allDisks[currentDisk]);
                    }
                }
                else if(!canMove && hold)
                {
                    if(fromStack == 1){allPoints[currentDisk].set((int) (width*0.1664), yPositions[stackOne.getSize()-1]);}
                    if(fromStack == 2){allPoints[currentDisk].set((int)(width*0.5), yPositions[stackTwo.getSize()-1]);}
                    if(fromStack == 3){allPoints[currentDisk].set((int)(width*0.8353), yPositions[stackThree.getSize()-1]);}
                }
                System.out.println(fromStack);

                hold = false;
                originX = 0;
                originY = 0;
                currentDisk = 0;
                to = 0;
                fromStack = 0;
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
