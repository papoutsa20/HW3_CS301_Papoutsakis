package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


/**
 * An implementation of the animator interface that animates a single player pong game
 *
 * @author Stelios Papoutsakis
 * @version March 21 2018
 */

public class PongAnimator implements Animator {


    /*
      instance variables
            */
    private Boolean startOver = true; // tells us whether we need to draw a new ball
    private Boolean outOfBounds = false; // tells us if the ball is on screen
    private Boolean xReverse = false; // tells us the direction the ball is traveling in the x direction
    private Boolean yReverse = false; // tells us the direction the ball is traveling in the x direction
    private int xCount = 0;  // counts the number of logical clock ticks in the x direction
    private int yCount = 0;  // counts the number of logical clock ticks in the y direction
    private int padddleWidth = 25; // user paddle width
    private int paddleHeight = 200; // user paddle height, defaults to small size
    private int paddleSize = 0; // indicates if the paddle is in small(0) or large (1) mode, defaults to small
    private int ballWidthHeight = 25; // height and width of the square ball
    private int speed; // speed of the ball
    private Paint color = new Paint(); // color of ball and paddle


    @Override
    /*
    Time interval in milliseconds between each "tick"
    @return int that represents the time between "ticks"
     */
    public int interval() {
        return 30;
    }

    /*
    Background color of program
    @return int of background color
     */
    @Override
    public int backgroundColor() {
        return Color.BLACK;
    }

    //Don't care about this
    @Override
    public boolean doPause() {
        return false;
    }

    //Don't care about this
    @Override
    public boolean doQuit() {
        return false;
    }


    /*
    Set the paddle height
    @return void
     */
    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    /*
    @return 0 or 1 representing the paddle size
     */
    public int getPaddleSize() {
        return paddleSize;
    }

   /*
   Set the paddle size
    @return void
    */
    public void setPaddleSize(int paddleSize) {
        this.paddleSize = paddleSize;
    }

    /*
    change the value of the startOver boolean
    @return void
    */
    public void setStartOver(Boolean startOver) {
        this.startOver = startOver;
    }


    /*
     tells us if the ball is onscreen
     @return Boolean representing if the ball is on screen
     */
    public Boolean getOutOfBounds() {
        return outOfBounds;
    }


    /*
    change color of the paddle and ball
    @return void
    */
    public void setColor(Paint color) {
        this.color = color;
    }



    /**
     External Citation
     Date: 20 March 20158
     Problem: Didn't know how to start writing the tick method
     Resource:
     TestAnimator Starter code class
     Solution: I was able to see the example and implement the logic used there into my own program
     for example, that test code gave me the idea to have boolean reverse values. I didn't
     have to do any additional research online

     */


    /*
   tick method is called to update the screen given a certain interval
   @return void
    */
    @Override
    public void tick(Canvas canvas) {
        // drawing the stationary paddle
        canvas.drawRect(50, 400, 50 + this.padddleWidth, 400 + this.paddleHeight, this.color);

        // increments the count if not reversed, otherwise decrements
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

            // deciding which direction the ball will shoot out of
            switch ((int) (Math.random() * 2)) {
                case 0:
                    this.xReverse = true;
                    break;
                case 1:
                    this.xReverse = false;
                    break;
            }
            this.yReverse = false;

            // deciding the speed of the ball
            int x = (int) (Math.random() * canvas.getWidth());
            canvas.drawRect(x, 0, x + this.ballWidthHeight, this.ballWidthHeight, this.color);
            this.startOver = false;
            this.outOfBounds = false;
            this.speed = (int) (Math.random() * 25) + 5;
            this.xCount = x / this.speed;

        }

        // now that we have started! let's play:)
        else {

            //updated positions to draw!
            int newX = this.xCount * this.speed;
            int newY = this.yCount * this.speed;

           // if the ball is onscreen
            if (newX > 0) {

                // if the ball hits the paddle or the far right wall
                if (newX >= canvas.getWidth() || (newX >= 50 && newX <= 50 + this.padddleWidth)
                        && (newY >= 400 - this.ballWidthHeight && newY <= 400 + this.paddleHeight)) {
                    this.xReverse = !this.xReverse;
                }

                // if the ball hits the upper or lower wall
                if (newY > canvas.getHeight() || newY < 0) {
                    this.yReverse = !this.yReverse;
                }

               // draw the ball
                canvas.drawRect(newX, newY, newX + this.ballWidthHeight, newY + this.ballWidthHeight, this.color);

            }
            // if the ball passes the left most wall
            else if (newX < 0) {
                this.outOfBounds = true;
            }


        }


    }


    // don't care about this yet!!
    @Override
    public void onTouch(MotionEvent event) {

    }


}
