package com.example.qyqfi.racingcar;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class GameView extends AppCompatActivity {
    private ImageButton leftButton, rightButton;
    private ImageView car;
    public int healthPoints = 2;
    public int collisionFlag = 0;

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



        //control main car
        leftButton =  findViewById(R.id.leftButton);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(car.getX() > -50){
                    moveLeft();
                }
                return false;
            }
        });
        rightButton =  findViewById(R.id.rightButton);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(car.getX() < 700){
                    moveRight();
                }
                return false;
            }
        });
        car = findViewById(R.id.car);
       /* car.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {  //TODO swipe to move car between lanes
                x = event.getX();

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    car.setX(x);
                }
                return true;
            }
        });*/



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

        Collision(car, carModelA);
        //Collision(car, carModelB);
        //Collision(car, carModelC);
    }

    public void moveLeft(){
        car.setX((car.getX() - 50));
    }

    public void moveRight(){
        car.setX((car.getX() + 50));
    }

    public boolean Collision(ImageView car, ImageView traffic)
    {
        Rect carRect = new Rect();
        car.getHitRect(carRect);
        Rect trafficRect = new Rect();
        traffic.getHitRect(trafficRect);

        if(carRect.intersect(trafficRect) && collisionFlag == 0) {
            LoseHealth();
            collisionFlag = 1;
        }
        if(!carRect.intersect(trafficRect) && collisionFlag == 1){  //only works for single car
            collisionFlag = 0;
        }


        return carRect.intersect(trafficRect);
    }

    public void LoseHealth(){
        healthPoints--;
        Toast.makeText(getApplicationContext(), "Lives left " + Integer.toString(healthPoints), Toast.LENGTH_SHORT).show();
        if(healthPoints == 0 ) {
           openQuitActivity();
        }
    }

    public void openQuitActivity(){
        Intent intent = new Intent(this, QuitActivity.class);
        startActivity(intent);
    }
}
