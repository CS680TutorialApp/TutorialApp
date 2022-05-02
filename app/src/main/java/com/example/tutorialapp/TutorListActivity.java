package com.example.tutorialapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TutorListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private TutorSQLHelper tutorHelper;

    private ListView listView;
    private TextView textView;

    private ArrayList<Tutor> tutorList;
    private final int IPC_ID = 1122;


    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        /*TODO
        the next lines has benn added
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

//        //attach listener
//        listView = (ListView) findViewById(R.id.list2);
        //       listView.setOnItemClickListener(this);


        //Show title on action bar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(true); // app title shown
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);

//        subjectHelper = new SubjectSQLHelper(this);
//
//        //create database
//        try {
//            db = subjectHelper.getWritableDatabase();
//        } catch (SQLException e) {
//            Log.d("SQLiteDemo", "Create database failed");
//        }
//
//        //drop existing table and recreate
//        subjectHelper.dropTable();
//
//        //insert records
//        subjectHelper.addSubject(new Subject("Java", "Mike", "https://www.w3schools.com/java/"));
//        subjectHelper.addSubject(new Subject("Python", "Jason", "https://www.w3schools.com/python/"));
//        subjectHelper.addSubject(new Subject("SQL", "Bob", "https://www.w3schools.com/sql/"));

        tutorHelper = new TutorSQLHelper(this);
        tutorList = tutorHelper.getTutorList();

//        CustomAdapter customAdapter = new CustomAdapter(this, tutorList);
//        listView.setAdapter(customAdapter);


        textView = (TextView) findViewById(R.id.tutors);
        for (Tutor tutor: tutorList) {
            textView.append(tutor.getName() + "\n");
            Log.d("Tutor ", tutor.getName() + tutor.getPhone());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // if subject is selected, then display listview of subjects
            case R.id.subject:
                Intent intent = new Intent(this, SubjectListActivity.class);
                startActivity(intent);
                return true;

            case R.id.tutor:
                //TODO ADD TUTOR LIST
                Intent intent2 = new Intent(this, TutorListActivity.class);
                startActivity(intent2);
                return true;

            case R.id.close:
                //String textToSpeak = "Good Bye! See You Later!";
                //tTos.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(this, "Sign Out has been clicked", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent pass_reset = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(pass_reset);
                    }
                }, 4000);

                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }


    //close database
    @Override
    protected void onPause() {
        super.onPause();
        if(db != null)
            db.close();
    }







}
