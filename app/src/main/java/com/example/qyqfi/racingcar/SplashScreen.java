package com.example.qyqfi.racingcar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class SplashScreen extends AppCompatActivity {
    private ImageButton playButton;
    private ImageButton highScoreButton;
    private MediaPlayer mediaPlayer;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

         /*mediaPlayer = MediaPlayer.create(this, R.raw.intro_loop);  //change to vrooom sound
        mediaPlayer.setLooping(true);
        mediaPlayer.start();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menuIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(menuIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);





    }
}