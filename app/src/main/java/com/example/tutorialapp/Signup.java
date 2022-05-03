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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    LoginSQLHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.bounce);
        EditText user_name = (EditText) findViewById(R.id.new_user);
        EditText email = (EditText) findViewById(R.id.new_email);
        EditText password = (EditText) findViewById(R.id.new_password);

        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);



        Button signUp = (Button) findViewById(R.id.signup);

        Db = new LoginSQLHelper(this);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signUp.startAnimation(animation);
                String usernameInput = user_name.getText().toString();
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
                // check if fields are empty
                if(usernameInput.equals("") || emailInput.equals("") || passwordInput.equals("")){
                    Toast.makeText(Signup.this, "Fields are empty.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Boolean checkUser = Db.checkUserName(usernameInput);
                    if (checkUser == false){
                        Boolean insert = Db.insertData(usernameInput, passwordInput);
                        if (insert==true){
                            Toast.makeText(Signup.this, "Registered Successfully:", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Signup.this, "User already exist. Please sign in."+" Username: "+ usernameInput +"; Email: " +emailInput+ "; Password: "+ passwordInput, Toast.LENGTH_SHORT).show();
                            //once register in, pop user for log in
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Signup.this, "Registration field.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Signup.this, "User already exist. Please sign in."+" Username: "+ usernameInput +"; Email: " +emailInput+ "; Password: "+ passwordInput, Toast.LENGTH_SHORT).show();
                        System.out.println("Username: "+ usernameInput +"Email: " +emailInput+ "Password: "+ passwordInput);
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