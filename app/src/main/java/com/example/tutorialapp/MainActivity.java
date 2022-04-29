package com.example.tutorialapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private TutorSQLHelper tutorHelper;
    private SubjectSQLHelper subjectHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();

        // Initialize database of tutorial.db and login.db
        //create database
        try {
            db = tutorHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.d("SQLiteDemo", "Create database failed");
        }
        // Create tutor table
        //drop existing table and recreate
        tutorHelper.dropTable();

        //insert records
        tutorHelper.addTutor(new Tutor("Mike", "https://bentley.zoom.us/", "geo:0,0?q=175+forest+street+waltham+ma", "(781) 891-2000", "mike@email.com"));
        tutorHelper.addTutor(new Tutor("Jason", "https://bentley.zoom.us/", "geo:0,0?q=175+forest+street+waltham+ma", "(781) 891-2000", "jason@email.com"));
        tutorHelper.addTutor(new Tutor("Bob", "https://bentley.zoom.us/", "geo:0,0?q=175+forest+street+waltham+ma", "(781) 891-2000", "bob@email.com"));

        // create subject table
        subjectHelper = new SubjectSQLHelper(this);


        //drop existing table and recreate
        subjectHelper.dropTable();

        //insert records
        subjectHelper.addSubject(new Subject("Java", "Mike", "https://www.w3schools.com/java/"));
        subjectHelper.addSubject(new Subject("Python", "Jason", "https://www.w3schools.com/python/"));
        subjectHelper.addSubject(new Subject("SQL", "Bob", "https://www.w3schools.com/sql/"));

    }
}