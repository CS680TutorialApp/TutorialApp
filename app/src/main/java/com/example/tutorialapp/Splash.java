package com.example.tutorialapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Handler handler;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeScreen = new Intent(Splash.this,MainActivity.class);
                startActivity(homeScreen);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}