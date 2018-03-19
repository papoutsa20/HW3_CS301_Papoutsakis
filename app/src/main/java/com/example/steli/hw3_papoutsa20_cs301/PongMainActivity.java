package com.example.steli.hw3_papoutsa20_cs301;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class PongMainActivity extends AppCompatActivity implements View.OnClickListener {

    private PongAnimator pongA = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);
        AnimationSurface a = (AnimationSurface)findViewById(R.id.animationSurface);
        this.pongA = new PongAnimator();
        a.setAnimator(this.pongA);

        Button paddleSize = (Button)findViewById(R.id.paddleSize);
        Button newBall = (Button)findViewById(R.id.newBall);
        Button color = (Button) findViewById(R.id.Colors);

        paddleSize.setOnClickListener(this);
        newBall.setOnClickListener(this);
        color.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.paddleSize)
        {
            switch (this.pongA.getPaddleSize())
            {
                case 0:
                    this.pongA.setPaddleHeight(400);
                    this.pongA.setPaddleSize(1);
                    break;
                case 1:
                    this.pongA.setPaddleHeight(200);
                    this.pongA.setPaddleSize(0);
                    break;
            }
        }
        else if(v.getId() == R.id.newBall && this.pongA.getOutOfBounds())
        {
            this.pongA.setStartOver(true);

        }


        else if(v.getId() == R.id.Colors)
        {
            Paint paint = new Paint();
            paint.setARGB(255, (int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));
            this.pongA.setColor(paint);
        }
        else{}
    }
}
