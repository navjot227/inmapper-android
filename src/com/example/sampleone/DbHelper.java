package com.example.sampleone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inmapper-android.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "mobile_point";
    public static final String COLUMN1 = "posx";
    public static final String COLUMN2 = "posy";
    public static final String COLUMN3 = "posz";
    public static final String COLUMN4 = "azimuth";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN1
            + " INTEGER NOT NULL," + COLUMN2 + " NUMBER NOT NULL," + COLUMN3
            + " NUMBER NOT NULL," + COLUMN4 + " NUMBER NOT NULL);";

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            // Create Database
            db.execSQL(TABLE_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

    }

}