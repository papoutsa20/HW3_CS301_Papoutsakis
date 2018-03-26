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
    private int xSpeed; // speed of the ball in x direction
    private int ySpeed; // speed of the ball in y direction
    private int yCordPaddle;
    private Paint color = new Paint(); // color of ball and paddle
    private int score = 0;

    /*
    Constructor that sets the default color to white
     */

    public PongAnimator() {
        this.color.setColor(Color.WHITE);
        this.yCordPaddle = 400;
    }


    @Override
    /*
    Time interval in milliseconds between each "tick"
    @return int that represents the time between "ticks"
     */
    public int interval() {
        return 10;
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


   /*
   Set the paddle size
    @return void
    */


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
        canvas.drawRect(50, this.yCordPaddle, 50 + this.padddleWidth, this.yCordPaddle + this.paddleHeight, this.color);

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
            this.xSpeed = (int) (Math.random() * 25) + 5;
            this.xCount = x / this.xSpeed;
            this.ySpeed = (int) (Math.random() * 25) + 5;


        }

        // now that we have started! let's play:)
        else {

            //updated positions to draw!
            int newX = (this.xCount * this.xSpeed);
            int newY = (this.yCount * this.ySpeed);

           // if the ball is onscreen
            if (newX > 0) {

                // if the ball hits the paddle or the far right wall
                if (newX >= canvas.getWidth() || (newX >= 50 && newX <= 50 + this.padddleWidth)
                        && (newY >= this.yCordPaddle - this.ballWidthHeight &&
                        newY <= this.yCordPaddle + this.paddleHeight)) {
                    this.xReverse = !this.xReverse;
                    if(newX <= 50 + this.padddleWidth)
                    {

                        this.score++;

                    }

                }

                // if the ball hits the upper or lower wall
                if (newY > canvas.getHeight() || newY < 0) {
                    this.yReverse = !this.yReverse;
                    //this.ySpeed*=1.1;

                }

               // draw the ball
                canvas.drawRect(newX, newY, newX + this.ballWidthHeight, newY + this.ballWidthHeight, this.color);

                Paint score = new Paint();
                score.setTextSize(100);
                score.setTextAlign(Paint.Align.CENTER);
                score.setColor(this.color.getColor());
                canvas.drawText( this.score + "",canvas.getWidth()/2,canvas.getHeight()/2,score);


            }
            // if the ball passes the left most wall
            else if (newX < 0) {
                this.outOfBounds = true;
                this.score = 0;
            }


        }


    }



    @Override
    public void onTouch(MotionEvent event) {
        this.yCordPaddle = (int)event.getY();

    }


}
