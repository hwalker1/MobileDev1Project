package com.example.qyqfi.racingcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class GameView extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "GameViewActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;

    //control btn
    private ImageButton leftButton, rightButton;
    private ImageView car;

    //Life count & Score
    public int healthPoints = 3;
    public int score = 0;

    //collision flag
    public int collisionFlag = 0;

    //Screen Size
    private int screenWidth;
    private int screenHeight;

    //Images
    private ImageView carModelA;
    private ImageView carModelB;
    private ImageView carModelC;
    private ImageView fuel_life_1;
    private ImageView fuel_life_2;
    private ImageView fuel_life_3;

    //Text
    private TextView scoreText;

    //Position
    private  float carModelA_X;
    private  float carModelA_Y;
    private  float carModelB_X;
    private  float carModelB_Y;
    private  float carModelC_X;
    private  float carModelC_Y;

    //imageView car width
    private int car_width = 128;
    private int car_height = 80;
    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        //accelerometer sensor
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(GameView.this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG,"onCreate: Registered accelerometer listener");

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

       scoreText = (TextView)findViewById(R.id.score_text);

        carModelA = (ImageView)findViewById(R.id.carModelA);
        carModelB = (ImageView)findViewById(R.id.carModelB);
        carModelC = (ImageView)findViewById(R.id.carModelC);
        fuel_life_1 = (ImageView)findViewById(R.id.fuel_life_1);
        fuel_life_2 = (ImageView)findViewById(R.id.fuel_life_2);
        fuel_life_3 = (ImageView)findViewById(R.id.fuel_life_3);

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
        //set initial score
        scoreText.setText(""+score);

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

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Log.d(TAG,"onSensorChanged: X:" + sensorEvent.values[0]+" Y:"+sensorEvent.values[1]+" Z: "+sensorEvent.values[2]);

        float car_X = car.getX();
        float car_Y = car.getY();

        //turning screen controls car's motion
        if(sensorEvent.values[0] < -4 && car_X + car_width + 280 <= screenWidth){
            car.setX(car_X + 50);
        }else if(sensorEvent.values[0] > 4 && car_X >= 0){
            car.setX(car_X - 50);
        }else if(sensorEvent.values[1] < -2 && car_Y - 50 >= 0){
            car.setY(car_Y - 50);
        }else if(sensorEvent.values[1] > 2 && car_Y + car_height + 350 <= screenHeight){
            car.setY(car_Y + 50);
        }

        //Log.d(TAG,"car Y:"+ car_Y + " height: "+ screenHeight);
    }

    //change model cars position
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

        setScore();
        collision(car, carModelA);
        //Collision(car, carModelB);
        //Collision(car, carModelC);
    }

    //repeat add 1 to score every 20 milliseconds
    public void setScore(){
        score++;
        scoreText.setText("" + score/100);
    }
    public void moveLeft(){
        car.setX((car.getX() - 50));
    }

    public void moveRight(){
        car.setX((car.getX() + 50));
    }

    public boolean collision(ImageView car, ImageView traffic)
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
        switch (healthPoints){
            case 0:
                timer.cancel();
                timer = null;
                openQuitActivity();
                break;
            case 1:
                fuel_life_2.setVisibility(View.INVISIBLE);
                fuel_life_3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                fuel_life_3.setVisibility(View.INVISIBLE);
                break;
        }

        if(healthPoints == 0 ) {
           openQuitActivity();
        }
    }

    public void openQuitActivity(){
        String scoreValue = scoreText.getText().toString();
        Intent intent = new Intent(this, QuitActivity.class);
        intent.putExtra("SCORE",scoreValue);
        finish();
        startActivity(intent);
    }
}
