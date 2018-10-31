package com.example.qyqfi.racingcar;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends AppCompatActivity {
    //Screen Size
    private int screenWidth;
    private  int screenHeight;

    //Images
    private ImageView carModel;

    //Position
    private  float carModelX;
    private  float carModelY;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        carModel = (ImageView)findViewById(R.id.carModel);

        //get screen size
        WindowManager wm = getWindowManager();
        Display disp= wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //move to out of screen
        carModel.setX(-80.0f);
        carModel.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0 , 20);

    }

    public void changePos(){
        carModelY -= 10;
        if (carModel.getY() + carModel.getHeight() < 0){
            carModelX = (float)Math.floor(Math.random() * (screenWidth - carModel.getWidth()));
            carModelY = screenHeight + 100.0f;

        }
        carModel.setX(carModelX);
        carModel.setY(carModelY);
    }

}
