package com.example.tutorialapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    // Define the variable of CalendarView type
    // and TextView type;
    private CalendarView calendar;
    private TextView date_view;
    private Button button;
    private Button notifyButton;
    private Button clearButton;
    private String tutor;
    private TutorSQLHelper tutorSQLHelper;
    private Tutor tutorObject;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder notifyDetails = null;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final int SIMPLE_NOTFICATION_ID = 101;
    private String contentTitle = "Email Notification";
    private String contentText = "Get to Email by clicking me";
    private String tickerText = "New Alert - Pull Down Status Bar";

    private TextToSpeech tTos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        /*
        The next lines has been added
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change actionBar color
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        int myColor = Color.parseColor("#2CA7E0");
        ColorDrawable cd = new ColorDrawable(myColor);
        actionBar.setBackgroundDrawable(cd);

        //create a local Intent object to extract data
        Intent myLocalIntent = getIntent();

        //grab the values in intent container
        Bundle myBundle = myLocalIntent.getExtras();
        //extract the individual data parts of the bundle
        tutor = myBundle.getString("tutorName");
        tutorSQLHelper = new TutorSQLHelper(this);
        tutorObject = tutorSQLHelper.getTutor(tutor);
        String tutorEmail = tutorObject.getEmail();

        // Set up calendar
        calendar = (CalendarView) findViewById(R.id.calendar);
        date_view = (TextView) findViewById(R.id.date_view);
        button = (Button) findViewById(R.id.button6);
        notifyButton = (Button) findViewById(R.id.notify);
        clearButton = (Button) findViewById(R.id.clear);

        // set up notification
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Notifications must be assigned to a channel
        NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel description");
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[] { 1000, 1000, 1000, 1000 });
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        mNotificationManager.createNotificationChannel(channel);


        //create implicit intent for action when notification selected
        //send email to tutor when notification clicked
        Intent notifyIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        notifyIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {tutorEmail, tutorEmail});
        notifyIntent.putExtra(Intent.EXTRA_TEXT, "Schedule Confirmation");
        notifyIntent.putExtra(Intent.EXTRA_SUBJECT, "Schedule Confirmation");


        //create pending intent to wrap intent so that it will fire when notification selected.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,PendingIntent.FLAG_MUTABLE);

        final Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.droid)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // get the value of DAYS, MONTH, YEARS
            public void onSelectedDayChange(
                    CalendarView view,
                    int year,
                    int month,
                    int dayOfMonth)
            {
                // Store the value of date with
                // format in String type Variable
                // Add 1 in month because month
                // index is start with 0
                String Date
                        = (month + 1)  + "-"
                        +  dayOfMonth + "-" + year;

                // set this date in TextView for Display
                date_view.setText(Date);
            }
        });

        //Send explicit intent to tutorActivity class with data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), TutorActivity.class);

                Bundle myData = new Bundle();
                myData.putString("tutorName", tutor);

                myIntent.putExtras(myData);
                startActivity(myIntent, myData);
            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //notify() in response to button click.
                mNotificationManager.notify(SIMPLE_NOTFICATION_ID, nb.build());

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                mNotificationManager.cancel(SIMPLE_NOTFICATION_ID);
            }
        });

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

            // display tutor list
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
