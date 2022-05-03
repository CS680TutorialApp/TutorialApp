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

public class SubjectListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SQLiteDatabase db;
    private SubjectSQLHelper subjectHelper;

    private ListView listView;
    private TextView textView;

    private ArrayList<Subject> subjectList;
    private final int IPC_ID = 1122;

    TextToSpeech tTos;


    CustomAdapter customAdapter;

    private TextToSpeech speaker;
    private static final String tag = "Widgets";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_subject);

        tTos = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tTos.setLanguage(Locale.ENGLISH);
                }
            }
        });
        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

        //attach listener
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);


        subjectHelper = new SubjectSQLHelper(this);

        //Initialize Text to Speech engine (context, listener object)
        speaker = new TextToSpeech(this, this::onInit);

        // retrieve subject list
        subjectList = subjectHelper.getSubjectList();

        CustomAdapter customAdapter = new CustomAdapter(this, subjectList);
        listView.setAdapter(customAdapter);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Subject List");
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
            // if tutor is selected, then display list of tutors
            case R.id.tutor:
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
            default:
                return super.onOptionsItemSelected(item);


        }

    }


    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        String subject = subjectList.get(position).getName();
        String tutor = subjectList.get(position).getTutor();
        String ref = subjectList.get(position).getReferenceLink();

        // set up text to speech
        if(speaker.isSpeaking()){
            Log.i(tag, "Speaker Speaking");
            speaker.stop();
            // else start speech
        } else {
            Log.i(tag, "Speaker Not Already Speaking");
            speak(subject + " selected");
        }
        // send intent with data
        Intent myIntent = new Intent(this, SubjectDetailActivity.class);

        Bundle myData = new Bundle();

        myData.putString("tutorName", tutor);
        myData.putString("subject", subject);
        myData.putString("referenceLink", ref);

        myIntent.putExtras(myData);
        startActivity(myIntent, myData);

    }

    //close database
    @Override
    protected void onPause() {
        super.onPause();
        if(db != null)
            db.close();
        /*TODO i added the next line
         */
        if (tTos != null){
            tTos.stop();
            tTos.shutdown();
        }
        super.onPause();

    }


    //speak methods will send text to be spoken
    public void speak(String output){
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
    }

    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // If a language is not be available, the result will indicate it.
            int result = speaker.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                Log.e(tag, "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized
                Log.i(tag, "TTS Initialization successful.");
            }
        } else {
            // Initialization failed.
            Log.e(tag, "Could not initialize TextToSpeech.");
        }
    }

}
