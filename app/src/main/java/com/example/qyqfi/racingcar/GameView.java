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
    public int healthPoints = 10;

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

    //Collision Detection
    public boolean carModelA_col = true;
    public boolean carModelB_col = true;
    public boolean carModelC_col = true;

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
        carModelA.setY(screenHeight+50.0f);
        carModelB.setX(-50.0f);
        carModelB.setY(screenHeight+50.0f);
        carModelC.setX(-50.0f);
        carModelC.setY(screenHeight+50.0f);

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
            carModelA_col = true;  //reset collide when it reaches bottom of screen and resets
        }

        if (carModelB.getY() > screenHeight){
            carModelB_X = (float)Math.floor(Math.random() * (screenWidth - carModelB.getWidth()));
            carModelB_Y = -50.0f;
            carModelB_col = true;
        }

        if (carModelC.getY() > screenHeight){
            carModelC_X = (float)Math.floor(Math.random() * (screenWidth - carModelC.getWidth()));
            carModelC_Y = -50.0f;
            carModelC_col = true;
        }

        carModelA.setX(carModelA_X);
        carModelA.setY(carModelA_Y);
        carModelB.setX(carModelB_X);
        carModelB.setY(carModelB_Y);
        carModelC.setX(carModelC_X);
        carModelC.setY(carModelC_Y);

        //if collide with car and its collidable then losehealth
        if(Collision(car, carModelA, carModelA_col)){
            LoseHealth();
            carModelA_col = false;
        }
        if(Collision(car, carModelB, carModelB_col)){
            LoseHealth();
            carModelB_col = false;
        }
        if(Collision(car, carModelC, carModelC_col)){
            LoseHealth();
            carModelC_col = false;
        }
    }

    public void moveLeft(){
        car.setX((car.getX() - 50));
    }

    public void moveRight(){
        car.setX((car.getX() + 50));
    }

    public boolean Collision(ImageView car, ImageView traffic, boolean collidable ) //todo fix multithread problem (still lose health on quit screen)
    {
        Rect carRect = new Rect();
        car.getHitRect(carRect);
        Rect trafficRect = new Rect();
        traffic.getHitRect(trafficRect);

        if(carRect.intersect(trafficRect) && collidable) {
            return true;
        }
        else{
            return false;
        }
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
