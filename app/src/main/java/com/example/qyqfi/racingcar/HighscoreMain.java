package com.example.qyqfi.racingcar;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class HighscoreMain extends AppCompatActivity {
    ListView lv;
    EditText nameEditText;
    Button saveBtn, retriveBtn;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
    CustomAdapter adapter;
    Boolean forUpdate = true;

    public HighscoreMain() {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new CustomAdapter(this, spacecrafts);

        this.getSpacecrafts();
        //lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog(false);
            }
        });
    }

    public void addScore(Boolean forUpdate, String score) {
        getSpacecrafts();
        update(score);
        getSpacecrafts();
    }

    private void displayDialog(Boolean forUpdate) {
        Dialog d = new Dialog(this);
        d.setTitle("Add Score Data");
        d.setContentView(R.layout.highscore_dialog);

        nameEditText = d.findViewById(R.id.nameEditTxt);
        saveBtn = d.findViewById(R.id.saveBtn);
        retriveBtn = d.findViewById(R.id.retrieveBtn);

        if(!forUpdate) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save(nameEditText.getText().toString());
                }
            });
            retriveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts();
                }
            });
        } else {
            // Save selected text
            nameEditText.setText(adapter.getSelectedItemName());

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(nameEditText.getText().toString());
                }
            });
            retriveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts();
                }
            });
        }

        d.show();
    }

    // Save
    private void save(String name) {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        boolean saved= db.add(name);

        if(saved) {
            nameEditText.setText("");
            getSpacecrafts();
        } else {
            Toast.makeText(this, "Unable to Save", Toast.LENGTH_SHORT).show();
        }
    }

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

    // Delete
    private void delete() {
        // Get ID
        int id = adapter.getSelectedItemID();

        // Delete from DB
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        boolean deleted = db.delete(id);
        db.closeDB();

        if(deleted) {
            getSpacecrafts();
        } else {
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();

        if(title == "NEW") {
            displayDialog(!forUpdate);
        } else if(title == "EDIT") {
            displayDialog(forUpdate);
        } else if (title == "DELETE") {
            delete();
        }

        return super.onContextItemSelected(item);
    }
}
