package com.example.steli.hw3_papoutsa20_cs301;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PongMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);
        AnimationSurface a = (AnimationSurface)findViewById(R.id.animationSurface);
        a.setAnimator(new PongAnimator());
    }
}
