package com.example.tutorialapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    LoginSQLHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText user_name = (EditText) findViewById(R.id.new_user);
        EditText email = (EditText) findViewById(R.id.new_email);
        EditText password = (EditText) findViewById(R.id.new_password);

        String usernameInput = user_name.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        Button signUp = (Button) findViewById(R.id.signup);

        Db = new LoginSQLHelper(this);
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name.setText("");
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("");
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameInput.equals("") || emailInput.equals("") || passwordInput.equals("")){
                    Toast.makeText(Signup.this, "Fields are empty.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Boolean checkUser = Db.checkUserName(usernameInput);
                    if (checkUser == false){
                        Boolean insert = Db.insertData(usernameInput, passwordInput);
                        if (insert==true){
                            Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Signup.this, "Registration field.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Signup.this, "User already exist. Please sign in.", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });


    }
}