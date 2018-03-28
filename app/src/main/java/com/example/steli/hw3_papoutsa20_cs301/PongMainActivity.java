package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


/**
 * Main activity of the Pong game and program begins
 *
 * Enhancements:
 * Paddle size the toggle between small and big
 * User must press new ball button after the ball goes out of bounds to get a new ball
 * Color button changes the color of paddle and ball and resets to white when the ball goes out of bounds
 *
 * Notes:
 * Game is meant to be played in horizontal orientation
 * Paddle doesn't move(Yet:))
 * Enjoy
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @author Stelios Papoutsakis
 * @version March 21 2018
 *
 *
 */

public class PongMainActivity extends AppCompatActivity implements
        View.OnClickListener,SeekBar.OnSeekBarChangeListener {

    private PongAnimator pongA = null; // instance of Pong Animator


    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        // setting animationSurface's Animator to PongAnimator
        AnimationSurface animationSurface = (AnimationSurface)findViewById(R.id.animationSurface);
        this.pongA = new PongAnimator();
        animationSurface.setAnimator(this.pongA);

        // Finding references to all the buttons
        SeekBar paddleSize = (SeekBar)findViewById(R.id.seekBarPaddleSize);
        SeekBar ballSize = (SeekBar)findViewById(R.id.seekBarBallSize);
        Button newBall = (Button)findViewById(R.id.newBall);
        Button color = (Button) findViewById(R.id.Colors);


        paddleSize.setMax(200);
        ballSize.setMax(45);
        ballSize.setProgress(20);

        //Setting listeners to all the buttons
        newBall.setOnClickListener(this);
        color.setOnClickListener(this);
        paddleSize.setOnSeekBarChangeListener(this);
        ballSize.setOnSeekBarChangeListener(this);



    }




    /*
    onClick method for all the buttons in the Pong game
     */
    @Override
    public void onClick(View v) {


        // if button is new ball and ball is off screen
       if(v.getId() == R.id.newBall && this.pongA.getOutOfBounds())
        {
            this.pongA.setStartOver(true);

        }


        // if button is color
        else if(v.getId() == R.id.Colors)
        {
            // change to a random color
            Paint paint = new Paint();
            paint.setARGB(255, (int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));
            this.pongA.setColor(paint);
        }

        // if none of the above do nothing!!
        else{}
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId() == R.id.seekBarPaddleSize)
        {
            seekBar.setProgress(progress);
            this.pongA.setPaddleHeight(200 + progress);
        }

        else if(seekBar.getId() == R.id.seekBarBallSize)
        {
            seekBar.setProgress(progress);
            this.pongA.setBallWidthHeight(5 + progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
