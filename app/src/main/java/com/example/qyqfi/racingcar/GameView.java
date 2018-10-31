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
    private ImageView carModelA;
    private ImageView carModelB;
    private ImageView carModelC;


    //Position
    private  float carModelA_X;
    private  float carModelA_Y;
    private  float carModelB_X;
    private  float carModelB_Y;
    private  float carModelC_X;
    private  float carModelC_Y;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        carModelA = (ImageView)findViewById(R.id.carModelA);
        carModelB = (ImageView)findViewById(R.id.carModelB);
        carModelC = (ImageView)findViewById(R.id.carModelC);
        //get screen size
        WindowManager wm = getWindowManager();
        Display disp= wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //set initial position
        carModelA.setX(-50.0f);
        carModelA.setY(-50.0f);
        carModelB.setX(-300.0f);
        carModelB.setY(-50.0f);
        carModelC.setX(-150.0f);
        carModelC.setY(-50.0f);


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
        //speed control
        carModelA_Y += 10;
        carModelB_Y += 20;
        carModelC_Y += 15;

        if (carModelA.getY() > screenHeight){
            carModelA_X = (float)Math.floor(Math.random() * (screenWidth - carModelA.getWidth()));
            carModelA_Y = -50.0f;
            //carModelA_Y = screenHeight + 100.0f;
        }

        if (carModelB.getY() > screenHeight){
            carModelB_X = (float)Math.floor(Math.random() * (screenWidth - carModelB.getWidth()));
            carModelB_Y = -50.0f;
        }

        if (carModelC.getY() > screenHeight){
            carModelC_X = (float)Math.floor(Math.random() * (screenWidth - carModelC.getWidth()));
            carModelC_Y = -50.0f;

        }

        carModelA.setX(carModelA_X);
        carModelA.setY(carModelA_Y);
        carModelB.setX(carModelB_X);
        carModelB.setY(carModelB_Y);
        carModelC.setX(carModelC_X);
        carModelC.setY(carModelC_Y);
    }

}
