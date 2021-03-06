package com.example.tutorialapp;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import static android.Manifest.permission.CALL_PHONE;

import java.util.Locale;

public class TutorActivity extends AppCompatActivity implements View.OnClickListener {

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

    private TextView textView;
    private TextToSpeech tTos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        /*
        the next lines has been added
         */
        //change actionBar color
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

        //create a local Intent object to extract data
        Intent myLocalIntent = getIntent();

        //grab the values in intent container
        Bundle myBundle = myLocalIntent.getExtras();

        //extract the individual data parts of the bundle
        tutorName = myBundle.getString("tutorName");
        // get tutor from database
        tutorHelper = new TutorSQLHelper(this);
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
        button4.setText("Email: " + email);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setText("Phone: " + phone);
        button5.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView5);
        textView.setText(tutor.getName() + "'s Appointment Confirmation");

        tTos = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tTos.setLanguage(Locale.ENGLISH);
                }
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            //Open zoom link
            case R.id.button2:
                Uri uri = Uri.parse(zoom);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            // open map
            case R.id.button3:
                Uri uri2 = Uri.parse(address);
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent3);
                break;

            // send email to tutor
            case R.id.button4:
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Schedule confirmation");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Schedule confirmation");
                emailIntent.setSelector( selectorIntent );

                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

            // call phone
            case R.id.button5:
                Uri uri3 = Uri.parse("tel:" + phone);

                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(uri3);

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
                break;

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
            // show tutor list
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
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
