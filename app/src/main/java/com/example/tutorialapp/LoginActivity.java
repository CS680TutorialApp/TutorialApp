package com.example.tutorialapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    LoginSQLHelper Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText user_name = (EditText) findViewById(R.id.user_name);
        EditText password = (EditText) findViewById(R.id.pass);
        TextView forgot_pass = (TextView) findViewById(R.id.forgot_pass);
        TextView signup = (TextView) findViewById(R.id.signup);

        Button login = (Button) findViewById(R.id.login);

        //TODO need to create db and table before you execute the query
        /** such as below in the main activity, in the tutorHelper.dropTable() it drops the table and create table
         * //create database
         *         try {
         *             db = tutorHelper.getWritableDatabase();
         *         } catch (SQLException e) {
         *             Log.d("SQLiteDemo", "Create database failed");
         *         }
         *         // Create tutor table
         *         //drop existing table and recreate
         *         tutorHelper.dropTable();
         */
        Db = new LoginSQLHelper(this);


        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass_reset = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(pass_reset);

                Toast.makeText(LoginActivity.this, "Forgot password Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up = new Intent(getApplicationContext(), Signup.class);
                startActivity(sign_up);
                Toast.makeText(LoginActivity.this, "Signup Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputPassword = password.getText().toString();
                String inputUsername = user_name.getText().toString();
                //TODO need to create db and table
                Boolean checkUserPass = Db.checkUsernameAndPassword(inputUsername, inputPassword);
                if(inputUsername.equals("") || inputPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Fields are empty. Please Enter again.", Toast.LENGTH_SHORT).show();


                }
                else{

                    if(checkUserPass == true){
                        Toast.makeText(LoginActivity.this, "Signed is Successfully.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, " Username: "+ inputUsername + "; Password: "+ inputPassword, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, checkUserPass.toString(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this," Username: "+ inputUsername + "; Password: "+ inputPassword, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, checkUserPass.toString(), Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });


    }


}
