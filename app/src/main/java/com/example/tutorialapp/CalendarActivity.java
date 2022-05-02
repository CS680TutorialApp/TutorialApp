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


    private Intent myIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        /*TODO
        The next lines has been added
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change actionBar color
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
        tutorSQLHelper = new TutorSQLHelper(this);
        tutorObject = tutorSQLHelper.getTutor(tutor);
        String tutorEmail = tutorObject.getEmail();
        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        calendar = (CalendarView) findViewById(R.id.calendar);
        date_view = (TextView) findViewById(R.id.date_view);
        button = (Button) findViewById(R.id.button6);
        notifyButton = (Button) findViewById(R.id.notify);
        clearButton = (Button) findViewById(R.id.clear);

        // notification set up
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
        //from expanded notification screen
        //open email when notification clicked
        Intent notifyIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        notifyIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {tutorEmail, tutorEmail});
        notifyIntent.putExtra(Intent.EXTRA_TEXT, "Schedule Confirmation");
        notifyIntent.putExtra(Intent.EXTRA_SUBJECT, "Schedule Confirmation");

        Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
        selectorIntent.setData(Uri.parse("mailto:"));

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{tutorEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Schedule confirmation");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Schedule confirmation");
        emailIntent.setSelector( selectorIntent );

        //create pending intent to wrap intent so that it will fire when notification selected.
        //The PendingIntent can only be used once.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, emailIntent,PendingIntent.FLAG_MUTABLE);
        //    PendingIntent.FLAG_ONE_SHOT);

//        PendingIntent updatedPendingIntent = PendingIntent.getActivity(
//                this,
//                NOTIFICATION_REQUEST_CODE,
//                updatedIntent,
//                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // setting the mutability flag
//        )

        final Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.droid)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // In this Listener have one method
            // and in this method we will
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
                        = dayOfMonth + "-"
                        + (month + 1) + "-" + year;

                // set this date in TextView for Display
                date_view.setText(Date);
            }
        });

        //TODO Add notification here to BUTTON
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
