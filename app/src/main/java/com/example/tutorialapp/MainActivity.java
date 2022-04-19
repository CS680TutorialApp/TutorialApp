package com.example.tutorialapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();

    }
}