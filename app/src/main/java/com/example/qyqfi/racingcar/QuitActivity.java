package com.example.qyqfi.racingcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuitActivity extends AppCompatActivity {

    private TextView score_tv;
    private ImageButton playButton;
    private ImageButton settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit);

        playButton = (ImageButton) findViewById(R.id.playBtn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
        settingButton = (ImageButton) findViewById(R.id.settingBtn);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingActivity();
            }
        });

        //set text to Score TextView
        score_tv = (TextView) findViewById(R.id.score_textView);
        score_tv.setText("Score: "+getIntent().getStringExtra("SCORE"));
    }

    public void openGameActivity(){
        Intent intent = new Intent(this, GameView.class);
        finish();
        startActivity(intent);
    }
    public void openSettingActivity(){
        Intent intent = new Intent(this,  SettingsActivity.class);
        startActivity(intent);
    }
}
