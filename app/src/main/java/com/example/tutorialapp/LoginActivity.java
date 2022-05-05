package com.example.tutorialapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    LoginSQLHelper Db;
    EditText user_name;
    EditText password;
    TextView loginError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //set up animation object
        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.bounce);

        loginError = findViewById(R.id.error);
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.pass);
        TextView forgot_pass = (TextView) findViewById(R.id.forgot_pass);
        TextView signup = (TextView) findViewById(R.id.signup);

        Button login = (Button) findViewById(R.id.login);

        Db = new LoginSQLHelper(this);


        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginError.setText("");
                user_name.setText("");
                user_name.setHint("User name");
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
                password.setHint("Password");
            }
        });
        // if forget password, send intent to resetPassword.class
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass_reset = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(pass_reset);

            }
        });

        // if sign up, send intent to signup.class
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up = new Intent(getApplicationContext(), Signup.class);
                startActivity(sign_up);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //start animation when login button clicked
                login.startAnimation(animation);
                //get the user password
                String inputPassword = password.getText().toString();
                //get the user username
                String inputUsername = user_name.getText().toString();
                Boolean checkUserPass = Db.checkUsernameAndPassword(inputUsername, inputPassword);
                // check if fields are empty
                if(inputUsername.equals("") || inputPassword.equals("")){

                    loginError.setText("Fields are empty. Please Enter again.");


                }
                else{
                    // if username and password are correct, send to subjectListActivity.class
                    if(checkUserPass == true){
                        Intent intent = new Intent(getApplicationContext(), SubjectListActivity.class);
                        startActivity(intent);

                    }else{
                        //display error text
                        loginError.setText("Invalid Credentials");
                    }


                }


            }
        });


    }


}
