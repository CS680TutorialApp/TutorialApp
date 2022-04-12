package com.example.tutorialapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                Intent myIntent = new Intent(String.valueOf(TutorActivity.class));

                Bundle myData = new Bundle();
                myData.putString("tutorName", tutor);

                myIntent.putExtras(myData);
                startActivity(myIntent, myData);
            }
        });




    }
}