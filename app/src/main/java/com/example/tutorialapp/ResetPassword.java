package com.example.tutorialapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText email = (EditText) findViewById(R.id.email_to_reset);
        Button reset = (Button) findViewById(R.id.reset_pass);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email.setText("");

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}