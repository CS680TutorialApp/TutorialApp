package com.example.tutorialapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectDetailActivity extends AppCompatActivity {
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private Button button;

    String tutor;
    String subject;
    String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject2);

        /*TODO
        the next lines has been added
         */
        //change actionBar color
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

        //create a local Intent object; we have been called!
        Intent myLocalIntent = getIntent();

        //grab the values in intent container
        Bundle myBundle = myLocalIntent.getExtras();

        //extract the individual data parts of the bundle
        tutor = myBundle.getString("tutorName");
        subject = myBundle.getString("subject");
        ref = myBundle.getString("referenceLink");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        textView2.setText("Subject: " + subject);
        textView3.setText("Tutor: " + tutor);
        textView4.setText("Quick Reference: " + ref);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), CalendarActivity.class);

                Bundle myData = new Bundle();
                myData.putString("tutorName", tutor);

                myIntent.putExtras(myData);
                startActivity(myIntent, myData);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(ref);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
                //TODO ADD TUTOR LIST
                Intent intent2 = new Intent(this, TutorListActivity.class);
                startActivity(intent2);
                return true;

            case R.id.close:
                //                String textToSpeak = "Good Bye! See You Later!";
//                tTos.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
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

}