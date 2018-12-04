package com.example.qyqfi.racingcar;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class HighscoreView extends AppCompatActivity {
    DatabaseHelper myDB;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayer = MediaPlayer.create(this, R.raw.intro_loop);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        setContentView(R.layout.database_view);

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));

                Set<String> hs = new HashSet<>();
                hs.addAll(theList);
                theList.clear();
                theList.addAll(hs);

                Collections.sort(theList, new Comparator<String>()
                {
                    @Override
                    public int compare(String s1, String s2)
                    {
                        Integer val1 = Integer.parseInt(s1);
                        Integer val2 = Integer.parseInt(s2);
                        return val1.compareTo(val2);
                    }
                });

                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }


    }

}
