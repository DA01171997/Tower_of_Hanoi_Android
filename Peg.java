package com.example.jason.towers;
import android.graphics.Canvas;

import java.util.Stack;
/**
 * Created by JASON on 10/26/2017.
 */

public class Peg implements GameObject{
    private Stack<Disk>DStack;
    private int left;                               //left x boundary
    private int right;                              //right x boundary
    public Peg(int left, int right){
        this.left = left;
        this.right = right;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < DStack.size(); i++){
            canvas.drawRect(DStack.get(i).getRectDuy(), DStack.get(i).getColor());
        }
    }

    public boolean checkMove(Stack<Disk> from, Stack <Disk> to){
        if(to.isEmpty()){
            return true;
        }
        else if(to.peek().getNum() > from.peek().getNum()){
            return true;
        }
        else return false;
    }
    public Disk GetPopTop(){                        //returns top disk, pops stack
        Disk temp = DStack.peek();
        DStack.pop();
        return temp;
    }
    public void pushStack(Disk d){
        DStack.push(d);
    }
    //size: stack.size()
    //top:  stack.peek()
}
