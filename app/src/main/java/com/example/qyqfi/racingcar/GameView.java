package com.example.qyqfi.racingcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class GameView extends AppCompatActivity {
    //Screen Size
    private int screenWidth;
    private  int getScreenHeight;

    //Images
    private ImageView carModel;

    //Position
    private  float carModelX;
    private  float carModelY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
    }
}
