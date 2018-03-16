package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DateFormat;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by steli on 3/16/2018.
 */

public class PongAnimator implements Animator {

   /*
   instance variables
    */
    private Boolean startOver = true; // tells us whether we need to draw a new ball
    private Boolean xReverse = false;
    private Boolean yReverse = false;
    private int xCount = 0;  // counts the number of logical clock ticks in the x direction
    private int yCount = 0;  // counts the number of logical clock ticks in the y direction



    @Override
    public int interval() {return 50;}

    @Override
    public int backgroundColor() {return Color.BLACK;}

    @Override
    public boolean doPause() {return false;}

    @Override
    public boolean doQuit() {return false;}

    @Override
    public void tick(Canvas canvas) {
        // drawing the stationary paddle
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        canvas.drawRect(50,350,75,650,white);

        if(!xReverse) this.xCount++;
        else this.xCount--;
        if(!yReverse) this.yCount++;
        else this.yCount--;
        Log.i("this is the xCount","" + this.xCount);
        Log.i("this is the yCount", "" + this.yCount);
        Log.i("this is the widthheight", canvas.getWidth() + "  " + canvas.getHeight());



        // starting the ball at a random point from the upper wall
        if(this.startOver)
        {
            this.xCount =1;
            this.yCount=1;
            this.xReverse=false;
            this.yReverse=false;
            int x = (int)(Math.random() * canvas.getWidth());
            canvas.drawRect(x,0,x+25,25,white);
            this.startOver = false;
            this.xCount = x/15;

        }

        // now that we have started! let's play:)
        else
        {
            int newX = this.xCount *15;
            int newY = this.yCount *15;

            if(newX > canvas.getWidth())
            {
                this.xReverse = !this.xReverse;
            }

            if(newY > canvas.getHeight() || newY < 0)
            {
                this.yReverse = !this.yReverse;
            }
            if(newX < 0) this.startOver = true;

            canvas.drawRect(newX,newY,newX+25,newY+25,white);

            //if((newX >=50 && newX <=75) && (newY >=350 && newY<=650))




        }


    }

    @Override
    public void onTouch(MotionEvent event) {

    }
}
