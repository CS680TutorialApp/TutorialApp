package com.example.tutorialapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class TutorListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private TutorSQLHelper tutorHelper;

    private ListView listView;
    private TextView textView;

    private ArrayList<Tutor> tutorList;
    private final int IPC_ID = 1122;


    CustomAdapter customAdapter;
    private TextToSpeech tTos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        /*
        the next lines has benn added
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

        // get tutor list from database
        tutorHelper = new TutorSQLHelper(this);
        tutorList = tutorHelper.getTutorList();

        textView = (TextView) findViewById(R.id.tutors);
        for (Tutor tutor: tutorList) {
            textView.append(tutor.getName() + ": " + tutor.getEmail() + "\n");
            Log.d("Tutor ", tutor.getName() + tutor.getPhone());
        }

        tTos = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tTos.setLanguage(Locale.ENGLISH);
                }
            }
        });
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
                //show TUTOR LIST
                Intent intent2 = new Intent(this, TutorListActivity.class);
                startActivity(intent2);
                return true;

            case R.id.close:
                String textToSpeak = "Good Bye! See You Later!";
                tTos.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
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
