package com.example.qyqfi.racingcar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuitActivity extends AppCompatActivity {

    private TextView score_tv;
    private HighscoreMain hs;
    private ImageButton playButton;
    private ImageButton highScoreButton;
    //
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
    CustomAdapter adapter = new CustomAdapter(this, spacecrafts);
    EditText nameEditText;
    DBHelper dbH = new DBHelper(this);
    //
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

        highScoreButton = (ImageButton) findViewById(R.id.showScoreBtn);
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHighScoreActivity();
            }
        });

        //set text to Score TextView
        score_tv = (TextView) findViewById(R.id.score_textView);
        score_tv.setText("Score: "+getIntent().getStringExtra("SCORE"));

        //save score to db
        update("99");

    }

    public void openGameActivity(){
        Intent intent = new Intent(this, GameView.class);
        finish();
        startActivity(intent);
    }

    public void openHighScoreActivity() {
        Intent intent = new Intent(this, HighscoreMain.class);
        startActivity(intent);
    }


    //
    // Retrive of spacecrafts
    public void getSpacecrafts() {
        spacecrafts.clear();
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        Cursor c = db.retrive();
        Spacecraft spacecraft = null;

        while(c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);

            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);

            spacecrafts.add(spacecraft);
        }

        db.closeDB();
    }

    // Update or Edit
    private void update(String newName) {
        // Get ID of Spacecraft
        int id = adapter.getSelectedItemID();

        // Update in DB
        DBAdapter db = new DBAdapter(this);
        db.closeDB();
        boolean updated = db.update(newName, id);
        db.closeDB();

        if(updated) {
            nameEditText.setText(newName);
            getSpacecrafts();
        } else {
            Toast.makeText(this, "Unable to Update", Toast.LENGTH_SHORT).show();
        }
    }
    //
}
