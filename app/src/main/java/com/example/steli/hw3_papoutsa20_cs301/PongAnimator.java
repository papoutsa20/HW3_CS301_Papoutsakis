package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by steli on 3/16/2018.
 */

public class PongAnimator implements Animator {


    /*
           instance variables
            */
    private Boolean startOver = true; // tells us whether we need to draw a new ball
    private Boolean outOfBounds = false;
    private Boolean xReverse = false;
    private Boolean yReverse = false;
    private int xCount = 0;  // counts the number of logical clock ticks in the x direction
    private int yCount = 0;  // counts the number of logical clock ticks in the y direction
    private int padddleWidth = 25;
    private int paddleHeight = 200;
    private int paddleSize = 0;
    private int ballWidthHeight = 25;
    private int speed;
    private Paint color = new Paint();


    @Override
    public int interval() {
        return 30;
    }

    @Override
    public int backgroundColor() {
        return Color.BLACK;
    }

    @Override
    public boolean doPause() {
        return false;
    }

    @Override
    public boolean doQuit() {
        return false;
    }


    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public int getPaddleSize() {
        return paddleSize;
    }

    public void setPaddleSize(int paddleSize) {
        this.paddleSize = paddleSize;
    }

    public void setStartOver(Boolean startOver) {
        this.startOver = startOver;
    }

    public Boolean getOutOfBounds() {
        return outOfBounds;
    }

    public void setColor(Paint color) {
        this.color = color;
    }


    @Override
    public void tick(Canvas canvas) {
        // drawing the stationary paddle
        canvas.drawRect(50, 400, 50 + this.padddleWidth, 400 + this.paddleHeight, this.color);

        if (!xReverse) this.xCount++;
        else this.xCount--;
        if (!yReverse) this.yCount++;
        else this.yCount--;


        //Log.i("this is the xCount","" + this.xCount);
        //Log.i("this is the yCount", "" + this.yCount);
        //Log.i("this is the widthheight", canvas.getWidth() + "  " + canvas.getHeight());


        // starting the ball at a random point from the upper wall
        if (this.startOver) {
            this.color.setColor(Color.WHITE);
            this.xCount = 1;
            this.yCount = 1;
            switch ((int)(Math.random() * 2))
            {
                case 0:
                    this.xReverse = true;
                    break;
                case 1:
                    this.xReverse = false;
                    break;
            }
            this.yReverse = false;
            int x = (int) (Math.random() * canvas.getWidth());
            canvas.drawRect(x, 0, x + this.ballWidthHeight, this.ballWidthHeight, this.color);
            this.startOver = false;
            this.outOfBounds = false;
            this.speed = (int) (Math.random() * 25) + 5;
            this.xCount = x / this.speed;

        }

        // now that we have started! let's play:)
        else {
            int newX = this.xCount * this.speed;
            int newY = this.yCount * this.speed;

            if (newX > 0) {

                if (newX > canvas.getWidth() || (newX >= 50 && newX <= 50 + this.padddleWidth)
                        && (newY >= 400 && newY <= 400 + this.paddleHeight)) {
                    this.xReverse = !this.xReverse;
                }

                if (newY > canvas.getHeight() || newY < 0) {
                    this.yReverse = !this.yReverse;
                }

                canvas.drawRect(newX, newY, newX + this.ballWidthHeight, newY + this.ballWidthHeight, this.color);

            } else if (newX < 0) {
                this.outOfBounds = true;
            }


        }


    }

    @Override
    public void onTouch(MotionEvent event) {

    }


}
