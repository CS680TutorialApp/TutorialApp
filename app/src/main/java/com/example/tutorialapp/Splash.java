package com.example.tutorialapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Handler handler;



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView bent_logo = findViewById(R.id.splash_logo);
        ImageView welcome = findViewById(R.id.welcome);
        ImageView to = findViewById(R.id.to);

        //Load animation
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        // Start animation
        welcome.startAnimation(slide_down);
        to.startAnimation(slide_down);
        bent_logo.startAnimation(slide_up);
        //hide taskbar on splash activity
        getSupportActionBar().hide();

        //implement handler to set the splash view for a while
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //intent to the login activity after splash done
                Intent homeScreen = new Intent(Splash.this,LoginActivity.class);
                startActivity(homeScreen);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}
