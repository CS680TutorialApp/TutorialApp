package com.example.tutorialapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AfterLogin extends AppCompatActivity {
    TextToSpeech tTos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

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

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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

                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }
    public void onPause(){
        if (tTos != null){
            tTos.stop();
            tTos.shutdown();
        }
        super.onPause();

    }}
