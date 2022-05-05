package com.example.tutorialapp;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    LoginSQLHelper Db;
    TextView signupError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //set animation object for buttons
        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.bounce);
        EditText user_name = (EditText) findViewById(R.id.new_user);
        EditText email = (EditText) findViewById(R.id.new_email);
        EditText password = (EditText) findViewById(R.id.new_password);
        signupError = findViewById(R.id.signup_error);

        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);



        Button signUp = (Button) findViewById(R.id.signup);
        //assign a new object for the database
        Db = new LoginSQLHelper(this);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //show animation when signup button clicked
                signUp.startAnimation(animation);

                //get all user info to store on database
                String usernameInput = user_name.getText().toString();
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
                // check if fields are empty
                if(usernameInput.equals("") || emailInput.equals("") || passwordInput.equals("")){
                    signupError.setText("Fields are empty.");

                }
                else {
                    //check if the user is already exist
                    Boolean checkUser = Db.checkUserName(usernameInput);
                    if (checkUser == false){
                        //if not exist register the info to database
                        Boolean insert = Db.insertData(usernameInput, passwordInput);
                        if (insert==true){
                            //if exist display error
                            signupError.setText("Registered Successfully");

                            //once register in, pop user for log in
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            signupError.setText("Registration field.");
                        }
                    }
                    else{
                        signupError.setText("User already exist. Please sign in.");
                    }

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