package com.example.tutorialapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private SubjectSQLHelper subjectHelper;

    private ListView listView;
    private TextView textView;

    private ArrayList<Subject> subjectList;
    private final int IPC_ID = 1122;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        //attach listener
        listView = (ListView) findViewById(R.id.list);


        //Show title on action bar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(true); // app title shown
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);

        subjectHelper = new SubjectSQLHelper(this);

        //create database
        try {
            db = subjectHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.d("SQLiteDemo", "Create database failed");
        }

        //drop existing table and recreate
        subjectHelper.dropTable();

        //insert records
        subjectHelper.addSubject(new Subject("Java", "Mike", "https://www.w3schools.com/java/"));
        subjectHelper.addSubject(new Subject("Python", "Jason", "https://www.w3schools.com/python/"));
        subjectHelper.addSubject(new Subject("SQL", "Bob", "https://www.w3schools.com/sql/"));


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
                listView = findViewById(R.id.list);
                CustomAdapter customAdapter = new CustomAdapter(this, subjectList);
                listView.setAdapter(customAdapter);


        }
            return true;
    }


    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        String subject = subjectList.get(position).getName();
        String tutor = subjectList.get(position).getTutor();
        String ref = subjectList.get(position).getReferenceLink();

        //implicit intent to open link in browser
//        Uri uri = Uri.parse(link);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

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
    }





}
