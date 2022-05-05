package com.example.tutorialapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    TextView reset_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.bounce);

        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);


        EditText email = (EditText) findViewById(R.id.email_to_reset);
        Button reset = (Button) findViewById(R.id.reset_pass);
        reset_error  = findViewById(R.id.resetError);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email.setText("");

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.startAnimation(animation);
                if(email.getText().equals("")){
                    reset_error.setText("Field is Empty");
                }


            }
        });
    }
    //setup the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}