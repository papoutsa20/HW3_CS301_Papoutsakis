package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private int ballWidthHeight = 25; // height and width of the square ball
    private int xSpeed; // speed of the ball in x direction
    private int ySpeed; // speed of the ball in y direction
    private int yCordPaddle;
    private int yCordPaddleCpu = 0;
    private Paint color = new Paint(); // color of ball and paddle
    private int score = 0;
    private int cpuScore = 0;
    private boolean offSet = false;

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
        if(!this.offSet && (int)(Math.random() * 1000) == 21) this.offSet = true;

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
            this.score = 0;
            this.cpuScore = 0;

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
            this.yCordPaddleCpu = 0;


        }

        // now that we have started! let's play:)
        else  {

            //updated positions to draw!
            int newX = (this.xCount * this.xSpeed);
            int newY = (this.yCount * this.ySpeed);

           // if the ball is onscreen
            if (newX > 0 && newX < canvas.getWidth()) {
                // if the ball hits the paddle or the far right wall
                if ((newX >= 50 && newX <= 50 + this.padddleWidth)
                        && (newY >= this.yCordPaddle - this.ballWidthHeight &&
                        newY <= this.yCordPaddle + this.paddleHeight)) {
                    this.xReverse = !this.xReverse;
                    this.score++;



                }

                else if((newX >= canvas.getWidth() - 75 && newX <= canvas.getWidth() - 25)
                && (newY >= this.yCordPaddleCpu - this.ballWidthHeight &&
                        newY <= this.yCordPaddleCpu + this.paddleHeight))
                {
                    this.xReverse = !this.xReverse;
                    this.cpuScore++;
                }

                // if the ball hits the upper or lower wall
                else if (newY > canvas.getHeight() || newY < 0) {
                    this.yReverse = !this.yReverse;


                }

               // draw the ball
                canvas.drawRect(newX, newY, newX + this.ballWidthHeight, newY + this.ballWidthHeight, this.color);


                // draws the score on the background
                Paint score = new Paint();
                score.setTextSize(100);
                score.setTextAlign(Paint.Align.CENTER);
                score.setColor(this.color.getColor());
                canvas.drawText( this.score + "",canvas.getWidth()/2 - 200,canvas.getHeight()/2,score);
                canvas.drawText( this.cpuScore + "",canvas.getWidth()/2 + 200,canvas.getHeight()/2,score);


                ComputerPlayer(canvas,newY,newX);



            }
            // if the ball passes the left most wall
            else {
                this.outOfBounds = true;
                this.offSet = false;
                canvas.drawRect(canvas.getWidth() - 50, this.yCordPaddleCpu, canvas.getWidth() - 25,
                        this.yCordPaddleCpu + this.paddleHeight, this.color);
                Paint score = new Paint();
                score.setTextSize(100);
                score.setTextAlign(Paint.Align.CENTER);
                score.setColor(this.color.getColor());
                canvas.drawText("Final " + this.score ,canvas.getWidth()/2 - 200,canvas.getHeight()/2,score);
                canvas.drawText("Final " + this.cpuScore,canvas.getWidth()/2 + 200,canvas.getHeight()/2,score);

            }


        }


    }

    public void ComputerPlayer(Canvas g, int yCord, int xCord)
    {




        if((yCord <= this.paddleHeight/2))
        {
            this.yCordPaddleCpu = 0;
        }


      else if((yCord + this.paddleHeight/2 >= g.getHeight())){

            if(!(this.offSet && xCord > g.getWidth()-300))
                this.yCordPaddleCpu = g.getHeight() - this.paddleHeight;
        }

        else{
            if(this.offSet && xCord > g.getWidth()-300)
            {
                if(this.yCordPaddleCpu > 0) this.yCordPaddleCpu-= 20;

            }
            else {
                this.yCordPaddleCpu = yCord - (this.paddleHeight / 2);
            }
        }







        g.drawRect(g.getWidth() - 50, this.yCordPaddleCpu, g.getWidth() - 25,
                this.yCordPaddleCpu + this.paddleHeight, this.color);


    }

    @Override
    public void onTouch(MotionEvent event) {
        this.yCordPaddle = (int)event.getY();

    }
//Hi Stelly Belly

}
