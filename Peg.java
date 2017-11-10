package com.example.jason.towers;
import android.graphics.Canvas;

import java.util.Vector;
/**
 * Created by JASON LIEU and DUY DO on 10/26/2017.
 */

public class Peg implements GameObject{
    private Vector<Disk>DStack = new Vector<>();
    private int left;
    private int right;
    private int size;
    public Peg(int left, int right){
        this.left = left;
        this.right = right;
        size = 0;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {}

    public void pushStack(Disk d){
        DStack.add(d);
        size++;
    }
    public void removeTop()
    {
        DStack.remove(size-1);
        size--;
    }
    public int getSize(){
        return size;
    }
    public Disk getTop(){
        return DStack.get(size-1);
    }
    public int getLeft() { return left;}
    public int getRight() { return right;}
    public boolean checkWin(){
        if(getSize() == 5){
            return true;
        }
        return false;}
}
