package com.example.qyqfi.racingcar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    // Open Connectivity
    public void openDB() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close DB
    public void closeDB() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save
    public boolean add(String name) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);

            long results = db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

            if(results > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Select
    public Cursor retrive() {
        String[] columns = {Constants.ROW_ID, Constants.NAME};

        Cursor c = db.query(Constants.TB_NAME, columns, null, null, null, null, null);

        return c;
    }

    // Update/Edit
    public boolean update(String newName, int id) {
        try {
            int result = db.delete(Constants.TB_NAME, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete/Remove
    public boolean delete(int id) {
        try {
            int result = db.delete(Constants.TB_NAME, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
