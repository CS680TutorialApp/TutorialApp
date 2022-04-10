package com.example.tutorialapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TutorActivity extends AppCompatActivity implements View.OnClickListener{

    private String tutorName;
    private String zoom;
    private String phone;
    private String email;
    private String address;

    private SQLiteDatabase db;
    private TutorSQLHelper tutorHelper;

    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        //create a local Intent object; we have been called!
        Intent myLocalIntent = getIntent();

        //grab the values in intent container
        Bundle myBundle = myLocalIntent.getExtras();

        //extract the individual data parts of the bundle
        tutorName = myBundle.getString("tutorName");

        //create database
        try {
            db = tutorHelper.getWritableDatabase();
        } catch(SQLException e) {
            Log.d("SQLiteDemo", "Create database failed");
        }

        //drop existing table and recreate
        tutorHelper.dropTable();

        //insert records
        tutorHelper.addTutor(new Tutor("Mike", "https://bentley.zoom.us/", "175 Forest St, Waltham, MA 02452", "(781) 891-2000", "mike@email.com"));
        tutorHelper.addTutor(new Tutor("Jason", "https://bentley.zoom.us/", "175 Forest St, Waltham, MA 02452", "(781) 891-2000", "jason@email.com"));
        tutorHelper.addTutor(new Tutor("Bob", "https://bentley.zoom.us/", "175 Forest St, Waltham, MA 02452", "(781) 891-2000", "bob@email.com"));

        Tutor tutor = tutorHelper.getTutor(tutorName);
        zoom = tutor.getZoomLink();
        phone = tutor.getPhone();
        email = tutor.getEmail();
        address = tutor.getAddress();

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);







    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                //TODO CHECK ON THIS WEB SEARCH
                Uri uri  = Uri.parse(zoom);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            // start web search intent
            case R.id.button3:
                //TODO CHECK ON MAP
                Uri uri2  = Uri.parse("geo:0,0?q=175+forest+street+waltham+ma");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent3);
                break;
            // call number intent
            case R.id.button4:
                //TODO SEND AN EMAIL
                break;
            // google map intent =
            case R.id.button5:
                Uri uri3  = Uri.parse("tel:" + phone);
                Intent intent2 = new Intent(Intent.ACTION_CALL, uri3);
                startActivity(intent2);
                break;

        }
}
