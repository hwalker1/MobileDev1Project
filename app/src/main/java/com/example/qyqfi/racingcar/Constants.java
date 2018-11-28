package com.example.qyqfi.racingcar;

public class Constants {
    //Columns
    static final String ROW_ID="id";
    static final String NAME="name";

    //DB Properties
    static final String DB_NAME="hh_DB";
    static final String TB_NAME="hh_TB";
    static final int DB_VERSION=1;

    //Create TB STMT
    static final String CREATE_TB = "Create TABLE hh_TB(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL);";

    // Drop TB STMT
    static final String DROP_TB = "DROP TABLE IF EXISTS" + TB_NAME;
}
